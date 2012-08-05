package com.ryanharter.android.goodreads.ui.fragments;

import com.ryanharter.android.goodreads.R;
import com.ryanharter.android.goodreads.adapters.NavigationAdapter.NavigationItem;
import com.ryanharter.android.goodreads.adapters.NavigationAdapter;
import com.ryanharter.android.goodreads.ui.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

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
		
		ret.add(UPDATES, R.drawable.ic_menu_updates);
		ret.add(SHELVES, R.drawable.ic_menu_mybooks);
		ret.add(FRIENDS, R.drawable.ic_menu_friends);
		ret.add(RECOMMENDATIONS, R.drawable.ic_menu_recommendations);
		ret.add(DISCUSSIONS, R.drawable.ic_menu_discussions);
		
		return ret;
	}
	
	@Override
	public void onListItemClick (ListView l, View v, int position, long id) {
		NavigationItem item = (NavigationItem) getListAdapter().getItem(position);
		if (item.name == SHELVES) {
			Intent intent = new Intent(getActivity(), ViewUserActivity.class);
			getActivity().startActivity(intent);
		}
	}
}
