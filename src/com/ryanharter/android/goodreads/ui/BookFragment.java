package com.ryanharter.android.goodreads.ui;

import java.util.List;

import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.Review;
import com.ryanharter.android.goodreads.R;

import com.google.android.imageloader.ImageLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;

public class BookFragment extends Fragment {
	private static final String TAG = "BookFragment";
	
	private Context mContext;
	private Review mReview;
	private String mUserId;
	
	private final ImageLoader mImageLoader = new ImageLoader();
	
	public BookFragment(Context context, Review review) {
		mContext = context;
		mReview = review;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null) {
			mReview = (Review) savedInstanceState.getSerializable("review");
			mUserId = savedInstanceState.getString("userId");
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("review", mReview);
		outState.putString("userId", mUserId);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_book, container, false);
		
		// Setup the title bar
		ImageView cover = (ImageView) v.findViewById(R.id.cover_image);
		TextView title = (TextView) v.findViewById(R.id.title);
		TextView author = (TextView) v.findViewById(R.id.author);
		
		title.setText(mReview.getBook().getTitle());
		author.setText(mReview.getBook().getAuthors().get(0).getName());
		
		// Loads the image
		mImageLoader.bind(cover, mReview.getBook().getImageUrl(), null);
		
		// Setup the Bookshelves bar
		TextView shelves = (TextView) v.findViewById(R.id.shelves);
		
		String sh = "";
		for (String shelf : mReview.getShelves()) {
			if (!sh.isEmpty()) {
				sh += "\n";
			}
			
			sh += shelf;
		}
		shelves.setText(sh);
		
		return v;
	}
	
	public void setReview(Review review) {
		mReview = review;
	}
	
	public Review getReview() {
		return mReview;
	}
	
	public void setUserId(String userId) {
		mUserId = userId;
	}
	
	public String getUserId() {
		return mUserId;
	}
}
