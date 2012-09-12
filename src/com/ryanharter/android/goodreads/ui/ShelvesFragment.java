package com.ryanharter.android.goodreads.ui;

import java.util.List;

import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.UserShelf;
import com.ryanharter.android.goodreads.R;
import com.ryanharter.android.goodreads.adapters.ShelvesAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentTransaction;

public class ShelvesFragment extends ListFragment {
	private static final String TAG = "ShelvesFragment";
	
	private List<UserShelf> mShelves;
	private String mUserId;
	
	public ShelvesFragment() {
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null) {
			mUserId = savedInstanceState.getString("userId");
		}
		
		Log.d(TAG, "Creating new ShelvesFragment for user: " + mUserId);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		if (mShelves == null) {
			new GetShelvesTask().execute();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("userId", mUserId);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_list_with_spinner, container, false);
	}
	
	@Override
	public void onListItemClick (ListView l, View v, int position, long id) {
		UserShelf shelf = mShelves.get(position);
		
		ShelfFragment fragment = new ShelfFragment();
		fragment.setUserId(mUserId);
		fragment.setShelfName(shelf.getName());
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left,
		                       android.R.anim.slide_out_right, android.R.anim.slide_in_left);
		ft.replace(R.id.main_container, fragment);
		ft.addToBackStack(null);
		ft.commit();
	}
	
	public void setUserId(String userId) {
		mUserId = userId;
	}
	
	public String getUserId() {
		return mUserId;
	}
	
	public class GetShelvesTask extends AsyncTask<Void, Void, List<UserShelf>> {
		
		protected List<UserShelf> doInBackground(Void... nothing) {
			List<UserShelf> shelves = null;
			try {
				shelves = GoodreadsService.getShelvesForUser(mUserId);
			} catch (Exception e) {
				Log.e(TAG, "Failed to get user shelves.", e);
			}
			return shelves;
		}
		
		protected void onPostExecute(List<UserShelf> shelves) {
			mShelves = shelves;
			
			ShelvesAdapter adapter = new ShelvesAdapter(getActivity(), mShelves);
			getListView().setAdapter(adapter);
		}
	}
}
