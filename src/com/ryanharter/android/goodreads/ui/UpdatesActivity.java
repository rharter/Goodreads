package com.ryanharter.android.goodreads.ui;

import com.ryanharter.android.goodreads.R;
import com.goodreads.api.v1.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UpdatesActivity extends BaseNavActivity
{
	private final static String TAG = "UpdatesActivity";
	private final static int LOGIN_CODE = 1;
	
	private String mUserId;
	
	private ListView listview;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updates);
		
		SharedPreferences sharedPreferences = getSharedPreferences("com.ryanharter.android.goodreads", MODE_PRIVATE);
		String token = sharedPreferences.getString("token", "");
		String tokenSecret = sharedPreferences.getString("tokenSecret", "");
		setUserId(sharedPreferences.getString("userId", ""));

		GoodreadsService.init(token, tokenSecret);

		// If the user hasn't authenticated, load login view
		if (token == "" || token == null) {
			Intent loginIntent = new Intent(this, LoginActivity.class);
			startActivityForResult(loginIntent, LOGIN_CODE);
		} else {
			GoodreadsService.setTokenWithSecret(token, tokenSecret);
		}
		
		listview = (ListView) findViewById(R.id.list);
		
		String[] updates = { "Update 1", "Update 2", "Update 3", "Update 4" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, updates);
		
		listview.setAdapter(adapter);
	}
	
	public void setUserId(String userId) {
		this.mUserId = userId;
	}
	
	public String getUserId() {
		return mUserId;
	}
}
