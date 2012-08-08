package com.ryanharter.android.goodreads.ui;

import com.ryanharter.android.goodreads.R;
import com.ryanharter.android.goodreads.ui.fragments.NavigationFragment;
import com.ryanharter.lib.ui.HiddenFragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class BaseNavActivity extends HiddenFragmentActivity
{
	private final static String TAG = "BaseNavActivity";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHiddenFragment(new NavigationFragment());
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
}
