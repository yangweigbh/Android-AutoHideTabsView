package com.yangwei.autohidetabsview.sample;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class TestListFragment extends ListFragment {
	
	public TestListFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				Cheeses.sCheeseStrings));
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
