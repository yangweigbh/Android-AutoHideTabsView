package com.yangwei.autohidetabsview.library;

public class TabViewTranslationTransformer extends AbsTabViewTransformer {

	@Override
	public void updateTabView(float percentage) {
		if (getTabView() != null) {
			getTabView().setTranslationY(-getTotal_offset() * percentage);
		}
		
	}

}
