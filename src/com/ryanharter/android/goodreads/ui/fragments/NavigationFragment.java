package com.ryanharter.android.goodreads.ui.fragments;

import com.ryanharter.android.goodreads.R;
import com.ryanharter.android.goodreads.adapters.NavigationAdapter;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class NavigationFragment extends ListFragment {
	
	private final static String UPDATES = "Updates";
	private final static String SHELVES = "Shelves";
	private final static String FRIENDS = "Friends";
	private final static String RECOMMENDATIONS = "Recommendations";
	private final static String DISCUSSIONS = "Discussions";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setListAdapter(getAdapter());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		return inflater.inflate(R.layout.navigation_fragment, container, false);
	}
	
	private ListAdapter getAdapter() {
		NavigationAdapter ret = new NavigationAdapter(getActivity());
		
		ret.add("Updates", R.drawable.ic_menu_updates);
		ret.add("Shelves", R.drawable.ic_menu_mybooks);
		ret.add("Friends", R.drawable.ic_menu_friends);
		ret.add("Recommendations", R.drawable.ic_menu_recommendations);
		ret.add("Discussions", R.drawable.ic_menu_discussions);
		
		return ret;
	}
}
