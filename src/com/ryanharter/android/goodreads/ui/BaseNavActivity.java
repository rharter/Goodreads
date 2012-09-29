package com.ryanharter.android.goodreads.ui;

import com.ryanharter.android.goodreads.R;
import com.ryanharter.android.goodreads.adapters.NavigationAdapter.NavigationItem;
import com.ryanharter.android.goodreads.adapters.NavigationAdapter;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.AdapterView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.AdapterView.OnItemClickListener;


public abstract class BaseNavActivity extends SlidingFragmentActivity
{
	private final static String TAG = "BaseNavActivity";
	
	protected String mUserId;
	protected int mFragmentId = UPDATES;
	private NavigationAdapter mNavAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.navigation_fragment);
		
		getSlidingMenu().setBehindOffsetRes(R.dimen.side_pane_offset);
		
		ListView navigationList = (ListView) findViewById(R.id.navigation_list);
		createAdapter();
		navigationList.setAdapter(mNavAdapter);
		navigationList.setOnItemClickListener(mNavListListener);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		ImageButton navButton = (ImageButton) findViewById(R.id.action_bar_home);
		navButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggle();
			}

		});
	}
	
	public void setUserId(String userId) {
		this.mUserId = userId;
	}
	
	public String getUserId() {
		return mUserId;
	}
	
	public void setCurrentFragment(int fragmentId) {
		mFragmentId = fragmentId;
	}
	
	public int getCurrentFragment() {
		return mFragmentId;
	}
	
	/**
	 * Sets the title for the action bar. This defaults to the 
	 * Goodreads logo.
	 *
	 * @param title The title to set.
	 */
	protected void setTitle(String title) {
		ImageView logo = (ImageView) findViewById(R.id.action_bar_logo);
		logo.setVisibility(View.GONE);
		
		TextView titleView = (TextView) findViewById(R.id.action_bar_title);
		titleView.setText(title);
		titleView.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Generates the list adapter for the navigation menu
	 */
	private void createAdapter() {
		mNavAdapter = new NavigationAdapter(this);
		
		mNavAdapter.add(UPDATES, getString(R.string.updates), R.drawable.ic_menu_updates);
		mNavAdapter.add(SHELVES, getString(R.string.shelves), R.drawable.ic_menu_mybooks);
		mNavAdapter.add(FRIENDS, getString(R.string.friends), R.drawable.ic_menu_friends);
		mNavAdapter.add(RECOMMENDATIONS, getString(R.string.recommendations), 
				R.drawable.ic_menu_recommendations);
		mNavAdapter.add(DISCUSSIONS, getString(R.string.discussions), 
				R.drawable.ic_menu_discussions);
		
	}
	
	public final static int UPDATES = 0;
	public final static int SHELVES = 1;
	public final static int FRIENDS = 2;
	public final static int RECOMMENDATIONS = 3;
	public final static int DISCUSSIONS = 4;
	
	private OnItemClickListener mNavListListener =  new OnItemClickListener() {
		
		public void onItemClick(AdapterView parent, View view, int position, long id) {
		
			NavigationItem item = (NavigationItem) mNavAdapter.getItem(position);
			
			// Just close the pane if the user selects the same fragment
			if (item.id == getCurrentFragment()) {
				toggle();
				return;
			}
			
			Fragment fragment;
			switch (item.id) {
				case SHELVES:
					setCurrentFragment(SHELVES);
					
					String userId = getUserId();
					fragment = new ShelvesFragment();
					((ShelvesFragment) fragment).setUserId(userId);
					break;
				default:
					setCurrentFragment(UPDATES);
					
					fragment = new UpdatesFragment();
			}
			
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.main_container, fragment);
			ft.commit();
			toggle();
		}
	};
}
