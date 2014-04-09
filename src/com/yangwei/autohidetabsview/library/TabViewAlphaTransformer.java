package com.yangwei.autohidetabsview.library;

public class TabViewAlphaTransformer extends AbsTabViewTransformer {

	@Override
	public void updateTabView(float percentage) {
		if (getTabView() != null) {
			getTabView().setAlpha(1-percentage);
		}

	}

}
