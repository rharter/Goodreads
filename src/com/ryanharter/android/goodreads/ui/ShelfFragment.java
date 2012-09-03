package com.ryanharter.android.goodreads.ui;

import java.util.List;

import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.Review;
import com.goodreads.api.v1.Reviews;
import com.ryanharter.android.goodreads.R;
import com.ryanharter.android.goodreads.adapters.BooksAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ShelfFragment extends ListFragment {
	private static final String TAG = "ShelfFragment";
	
	private String mShelfName;
	private String mUserId;
	
	private List<Review> mReviews;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null) {
			mShelfName = savedInstanceState.getString("shelfName");
			mUserId = savedInstanceState.getString("userId");
			
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		new GetReviewsTask().execute();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("shelfName", mShelfName);
		outState.putString("userId", mUserId);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_shelves, container, false);
	}
	
	public void setShelfName(String name) {
		mShelfName = name;
	}
	
	public String getShelfName() {
		return mShelfName;
	}
	
	public void setUserId(String userId) {
		mUserId = userId;
	}
	
	public String getUserId() {
		return mUserId;
	}
	
	public class GetReviewsTask extends AsyncTask<Void, Void, List<Review>> {
		
		protected List<Review> doInBackground(Void... nothing) {
			List<Review> reviews = null;
			try {
				Reviews revs = GoodreadsService.getBooksOnShelf(mShelfName, mUserId);
				reviews = revs.getReviews();
			} catch (Exception e) {
				Log.e(TAG, "Failed to get user shelves.", e);
			}
			return reviews;
		}
		
		protected void onPostExecute(List<Review> reviews) {
			mReviews = reviews;
			
			BooksAdapter adapter = new BooksAdapter(getActivity(), mReviews);
			getListView().setAdapter(adapter);
		}
	}
}