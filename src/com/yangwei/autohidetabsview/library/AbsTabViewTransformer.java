package com.yangwei.autohidetabsview.library;

import com.viewpagerindicator.TabPageIndicator;

public abstract class AbsTabViewTransformer implements ITransformer{
	float offset;
	float total_offset;
	TabPageIndicator tabView;
	
	public float getOffset() {
		return offset;
	}
	public void setOffset(float offset) {
		this.offset = offset;
	}
	public float getTotal_offset() {
		return total_offset;
	}
	public void setTotal_offset(float total_offset) {
		this.total_offset = total_offset;
	}
	public TabPageIndicator getTabView() {
		return tabView;
	}
	public void setTabView(TabPageIndicator tabView) {
		this.tabView = tabView;
	} 
}
