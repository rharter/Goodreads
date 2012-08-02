package com.ryanharter.android.goodreads.ui;

import com.ryanharter.android.goodreads.R;
import com.ryanharter.android.goodreads.service.*;

import com.darvds.ribbonmenu.RibbonMenuView;
import com.darvds.ribbonmenu.iRibbonMenuCallback;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.util.Log;

public class ShelfActivity extends Activity implements iRibbonMenuCallback
{
	private final static String TAG = "ShelfActivity";
	private final static int LOGIN_CODE = 1;
	
	private RibbonMenuView rbmView;
	
	private String mUserId;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		SharedPreferences sharedPreferences = getSharedPreferences("com.ryanharter.android.goodreads", MODE_PRIVATE);
		String token = sharedPreferences.getString("token", "");
		String tokenSecret = sharedPreferences.getString("tokenSecret", "");
		setUserId(sharedPreferences.getString("userId", ""));

		rbmView = (RibbonMenuView) findViewById(R.id.ribbonMenuView);
		rbmView.setMenuClickCallback(this);
		rbmView.setMenuItems(R.menu.ribbon_menu);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
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
				rbmView.toggleMenu();
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
	}

	@Override
	public void RibbonMenuItemClick(int itemId) {
			
			
			
			
	}
	
	public void setUserId(String userId) {
		this.mUserId = userId;
	}
	
	public String getUserId() {
		return mUserId;
	}
}
