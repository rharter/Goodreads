package com.ryanharter.android.goodreads.ui;

import com.goodreads.api.v1.GoodreadsService;
import com.ryanharter.android.goodreads.R;
import com.ryanharter.android.goodreads.ui.fragments.NavigationFragment;
import com.ryanharter.lib.ui.HiddenFragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ImageButton;

public class ShelfActivity extends HiddenFragmentActivity
{
	private final static String TAG = "ShelfActivity";
	private final static int LOGIN_CODE = 1;
	
	private String mUserId;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHiddenFragment(new NavigationFragment());
		setContentView(R.layout.activity_updates);
		
		SharedPreferences sharedPreferences = getSharedPreferences("com.ryanharter.android.goodreads", MODE_PRIVATE);
		String token = sharedPreferences.getString("token", "");
		String tokenSecret = sharedPreferences.getString("tokenSecret", "");
		setUserId(sharedPreferences.getString("userId", ""));

		ImageButton navButton = (ImageButton) findViewById(R.id.action_bar_home);
		navButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "Clicked...");
				toggle();
			}

		});
		
		// If the user hasn't authenticated, load login view
		Log.d(TAG, "Authorized with token[" + token + "] and secret[" + tokenSecret + "]");
		if (token == "" || token == null) {
			Intent loginIntent = new Intent(this, LoginActivity.class);
			startActivityForResult(loginIntent, LOGIN_CODE);
		} else {
			GoodreadsService.setTokenWithSecret(token, tokenSecret);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
			{
			case android.R.id.home:
				toggle();
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
	}
	
	public void setUserId(String userId) {
		this.mUserId = userId;
	}
	
	public String getUserId() {
		return mUserId;
	}
}
