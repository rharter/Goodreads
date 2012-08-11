package com.ryanharter.android.goodreads.ui;

import com.goodreads.api.v1.*;
import com.ryanharter.android.goodreads.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

public class ViewUserActivity extends BaseNavActivity
{
	private final static String TAG = "ViewUserActivity";
	private final static int LOGIN_CODE = 1;
	
	private String mUserId;
	private User mUser;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_user);
		
		new UserInfoTask().execute();
	}
	
	public void setUserId(String userId) {
		this.mUserId = userId;
	}
	
	public String getUserId() {
		return mUserId;
	}
	
	private class UserInfoTask extends AsyncTask<String, Void, User> {
		
		@Override
		protected User doInBackground(String... userIds) {
			try {
				mUser = GoodreadsService.getAuthorizedUser();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.d(TAG, "Got user: " + mUser.toString());
			return mUser;
		}
		
		@Override
		protected void onPostExecute(User user) {
			TextView name = (TextView) findViewById(R.id.name);
			Log.d(TAG, "Setting username: " + mUser.getName());
			name.setText(mUser.getName());
			
			TextView about = (TextView) findViewById(R.id.about);
			about.setText(mUser.getAbout());
			
			TextView favoriteBooks = (TextView) findViewById(R.id.favorite_books);
			favoriteBooks.setText(mUser.getFavoriteBooks());
		}
	}
}
