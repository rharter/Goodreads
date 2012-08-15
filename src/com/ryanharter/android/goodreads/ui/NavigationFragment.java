package com.ryanharter.android.goodreads.ui;

import com.ryanharter.android.goodreads.R;
import com.ryanharter.android.goodreads.adapters.NavigationAdapter.NavigationItem;
import com.ryanharter.android.goodreads.adapters.NavigationAdapter;
import com.ryanharter.android.goodreads.ui.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.v4.app.FragmentTransaction;

public class NavigationFragment extends ListFragment {
	
	public final static int UPDATES = 0;
	public final static int SHELVES = 1;
	public final static int FRIENDS = 2;
	public final static int RECOMMENDATIONS = 3;
	public final static int DISCUSSIONS = 4;
	
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
		
		ret.add(UPDATES, getActivity().getString(R.string.updates), R.drawable.ic_menu_updates);
		ret.add(SHELVES, getActivity().getString(R.string.shelves), R.drawable.ic_menu_mybooks);
		ret.add(FRIENDS, getActivity().getString(R.string.friends), R.drawable.ic_menu_friends);
		ret.add(RECOMMENDATIONS, getActivity().getString(R.string.recommendations), 
				R.drawable.ic_menu_recommendations);
		ret.add(DISCUSSIONS, getActivity().getString(R.string.discussions), 
				R.drawable.ic_menu_discussions);
		
		return ret;
	}
	
	@Override
	public void onListItemClick (ListView l, View v, int position, long id) {
		NavigationItem item = (NavigationItem) getListAdapter().getItem(position);
		MainActivity activity = (MainActivity) getActivity();
		
		// Just close the pane if the user selects the same fragment
		if (item.id == activity.getCurrentFragment()) {
			activity.toggle();
			return;
		}
		
		Fragment fragment;
		switch (item.id) {
			case SHELVES:
				activity.setCurrentFragment(SHELVES);
				
				String userId = activity.getUserId();
				fragment = new ShelvesFragment();
				((ShelvesFragment) fragment).setUserId(userId);
				break;
			default:
				activity.setCurrentFragment(UPDATES);
				
				fragment = new UpdatesFragment();
		}
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.main_container, fragment);
		ft.addToBackStack(null);
		ft.commit();
		((BaseNavActivity) getActivity()).toggle();
	}
}
