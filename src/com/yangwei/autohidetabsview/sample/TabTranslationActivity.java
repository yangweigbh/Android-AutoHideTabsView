package com.yangwei.autohidetabsview.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.yangwei.autohidetabsview.R;
import com.yangwei.autohidetabsview.library.AutoHideTabsView;
import com.yangwei.autohidetabsview.library.TabViewTranslationTransformer;

public class TabTranslationActivity extends FragmentActivity {

	private AutoHideTabsView mView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_translation);
		
		mView = (AutoHideTabsView) findViewById(R.id.mainLayout);
		mView.setAdapter(new TestFragmentAdapter(getSupportFragmentManager()));
		mView.setTransformer(new TabViewTranslationTransformer());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab_translation, menu);
		return true;
	}

}
