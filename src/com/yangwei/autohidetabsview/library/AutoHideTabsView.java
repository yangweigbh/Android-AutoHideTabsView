/*Copyright [2014] Yang Wei

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.yangwei.autohidetabsview.library;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.viewpagerindicator.TabPageIndicator;
import com.yangwei.autohidetabsview.BuildConfig;
import com.yangwei.autohidetabsview.R;

public class AutoHideTabsView extends RelativeLayout {

	private static final String TAG = "AutoHideTabsView";
	private TabPageIndicator mTabContainer;
	private ViewPager mViewPager;
	private TabContainerState mState;
	private float mLastMotionY;
	private float mLastMotionX;
	private int mTabContainerHeight;
	private onTabsStateChangeListener mListener;
	private float mTabExpandOffset = 3;
	private ITransformer mTransformer;
	
	public static int EXPAND = TabContainerState.EXPAND.ordinal();
	public static int COLLAPSE = TabContainerState.COLLAPSE.ordinal();
	
	enum TabContainerState {
		EXPAND, COLLAPSE, SCROLLING
	}

	public AutoHideTabsView(Context context) {
		this(context, null, 0);
	}
	
	public AutoHideTabsView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public AutoHideTabsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        mState = TabContainerState.EXPAND;
        mTabContainer = new TabPageIndicator(context, attrs);
		mViewPager = new ViewPager(context, attrs);
		mViewPager.setId(R.id.pager);
		mTabContainer.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				Log.d(TAG, "onPageSelected: " + mTabContainer.getTranslationY());
				if (mTabContainer.getTranslationY() < 0) {
					mTabContainer.setTranslationY(0);
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		
		setViewPager(mViewPager);
		setTabContainer(mTabContainer);
		if (mTransformer != null) {
			mTransformer.setTabView(mTabContainer);
		}
    }
	
	void setTabContainer(TabPageIndicator pageIndicator) {
		mTabContainer = pageIndicator;
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(ALIGN_PARENT_TOP, TRUE);
		addView(mTabContainer, params);
	}
	
	void setViewPager(ViewPager viewPager) {
		mViewPager = viewPager;
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		params.addRule(ALIGN_PARENT_TOP, TRUE);
		Log.d(TAG, "setViewPager called");
		addView(mViewPager, params);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		mTabContainerHeight = mTabContainer.getMeasuredHeight();
		mViewPager.setTranslationY(mTabContainerHeight);
		if (mTransformer != null) {
			mTransformer.setTotal_offset((int)getMaxiumTabCollapseOffset());
			mTransformer.updateTabView(0);
		}
		invalidate();
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionY =  ev.getY();
			mLastMotionX =  ev.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			float diff = ev.getY() - mLastMotionY;
			float oppositeDiff = ev.getX() - mLastMotionX;
			float absDiff = Math.abs(diff);
			if ((absDiff > Math.abs(oppositeDiff))) {
				if (diff > 0) {
					onPullDown(diff);
				} else if (diff < 0){
					onPushUp(diff);
				}
			}
			mLastMotionX = ev.getX();
			mLastMotionY = ev.getY();
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			if (Math.abs(mTransformer.getOffset()) < mTabContainerHeight/2) {
				setState(TabContainerState.EXPAND);
			} else if (Math.abs(mTransformer.getOffset()) > mTabContainerHeight/2) {
				setState(TabContainerState.COLLAPSE);
			}
			break;
		default:
			break;
		}
		
		return super.onInterceptTouchEvent(ev);
	}
	
	private void setState(TabContainerState expand) {
		TabContainerState oldState = mState;
		mState = expand;
		if (oldState != mState && mListener != null) {
			mListener.onTabsStateChange(mState.ordinal());
		}
		switch (expand) {
		case EXPAND:
			mTransformer.setOffset(0);
			mTransformer.updateTabView(0);
			break;
		case COLLAPSE:
			mTransformer.setOffset(-getMaxiumTabCollapseOffset());
			mTransformer.updateTabView(1);
			break;
		default:
			break;
		}
		mViewPager.invalidate();
	}

	private void onPushUp(float diff) {
		if (mViewPager.getTranslationY() > 0) {
			mViewPager.setTranslationY(Math.max(0, mViewPager.getTranslationY() + diff));
			return;
		}
		if (mState != TabContainerState.COLLAPSE) {
			mTransformer.setOffset((float)Math.max(-getMaxiumTabCollapseOffset(), mTransformer.getOffset() + diff));
			float percentage = -(mTransformer.getOffset())/(float)getMaxiumTabCollapseOffset();
			if (BuildConfig.DEBUG) {
				Log.d(TAG, ">>>percentage: " + percentage);
			}
			mTransformer.updateTabView(percentage);
			if (mTransformer.getOffset() < 0
					&& mTransformer.getOffset() > -getMaxiumTabCollapseOffset()) {
				mState = TabContainerState.SCROLLING;
			} else if (mTransformer.getOffset() <= -getMaxiumTabCollapseOffset()) {
				TabContainerState oldState = mState;
				mState = TabContainerState.COLLAPSE;
				if (oldState != mState && mListener != null) {
					mListener.onTabsStateChange(mState.ordinal());
				}
			}
		}
	}

	private void onPullDown(float diff) {
		if (mState != TabContainerState.EXPAND) {
			mTransformer.setOffset((float)Math.min(0, mTransformer.getOffset() + diff));
			float percentage = -(mTransformer.getOffset())/(float)getMaxiumTabCollapseOffset();
			if (BuildConfig.DEBUG) {
				Log.d(TAG, ">>>percentage: " + percentage);
			}
			mTransformer.updateTabView(percentage);
			if (mTransformer.getOffset() > -getMaxiumTabCollapseOffset()
					&& mTransformer.getOffset() < 0) {
				mState = TabContainerState.SCROLLING;
			} else if (mTransformer.getOffset() >= 0) {
				mState = TabContainerState.EXPAND;
				if (mListener != null) {
					mListener.onTabsStateChange(mState.ordinal());
				}
			}
		}
		
		mViewPager.setTranslationY(Math.min(mTabContainerHeight, mViewPager.getTranslationY() + diff));
		
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean result = super.dispatchTouchEvent(ev);
		//make sure the children not disable the parent's interceptTouch
		requestDisallowInterceptTouchEvent(false);
		return result;
	}
	
	void setOnTabsStateChangeListener(onTabsStateChangeListener listener) {
		mListener = listener;
	}
	
	public interface onTabsStateChangeListener {
		
		void onTabsStateChange(int newValue);
		
	}

	public void setAdapter(PagerAdapter adapter) {
		if (mViewPager != null) {
			mViewPager.setAdapter(adapter);
		}
		mTabContainer.setViewPager(mViewPager);
	}
	
	float getMaxiumTabCollapseOffset() {
		return mTabContainerHeight - mTabExpandOffset;
	}

	public ITransformer getTransformer() {
		return mTransformer;
	}

	public void setTransformer(ITransformer transformer) {
		this.mTransformer = transformer;
		if (mTabContainer != null) {
			mTransformer.setTabView(mTabContainer);
		}
	}
}
