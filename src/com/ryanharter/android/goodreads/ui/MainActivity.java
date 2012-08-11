package com.ryanharter.android.goodreads.ui;

import com.goodreads.api.v1.GoodreadsService;
import com.ryanharter.android.goodreads.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;

public class MainActivity extends BaseNavActivity
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
		setContentView(R.layout.activity_main);
		
		SharedPreferences sharedPreferences = getSharedPreferences("com.ryanharter.android.goodreads", MODE_PRIVATE);
		String token = sharedPreferences.getString("accessToken", "");
		String tokenSecret = sharedPreferences.getString("accessTokenSecret", "");
		setUserId(sharedPreferences.getString("userId", ""));

		GoodreadsService.init("3RATirCK1psi4YYZpoB96A", "WI2y6ZCYZxIanY6uzqNNuT9J88qqcGDXA4Y7WnKHiA");

		// If the user hasn't authenticated, load login view
		if (token == "" || token == null) {
			Intent loginIntent = new Intent(this, LoginActivity.class);
			startActivityForResult(loginIntent, LOGIN_CODE);
		} else {
			GoodreadsService.setAccessToken(token, tokenSecret);
		}
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.main_container, new UpdatesFragment());
		ft.commit();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			String token = data.getStringExtra("com.ryanharter.android.goodreads.token");
			String tokenSecret = data.getStringExtra("com.ryanharter.android.goodreads.tokenSecret");
			setUserId(data.getStringExtra("com.ryanharter.android.goodreads.userId"));
			
			if (token != null && token != "") {
				GoodreadsService.setAccessToken(token, tokenSecret);
			}
		}
	}
	
	public void setUserId(String userId) {
		this.mUserId = userId;
	}
	
	public String getUserId() {
		return mUserId;
	}
}
