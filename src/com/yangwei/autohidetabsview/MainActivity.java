package com.yangwei.autohidetabsview;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	private AutoHideTabsView mView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new AutoHideTabsView(this);
		setContentView(R.layout.activity_main);
		
		mView = (AutoHideTabsView) findViewById(R.id.mainLayout);
		mView.setAdapter(new TestFragmentAdapter(getSupportFragmentManager()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
