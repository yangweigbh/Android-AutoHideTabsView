package com.yangwei.autohidetabsview.library;

import com.viewpagerindicator.TabPageIndicator;

public interface ITransformer {
	void updateTabView(float percentage);
	
	float getOffset();
	
	void setOffset(float f);
	
	float getTotal_offset();
	
	void setTotal_offset(float total_offset);
	
	TabPageIndicator getTabView();
	
	void setTabView(TabPageIndicator tabView);
	
}
