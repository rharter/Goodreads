package com.ryanharter.android.goodreads.ui;

import java.util.List;

import com.goodreads.api.v1.Update;
import com.goodreads.api.v1.GoodreadsService;
import com.ryanharter.android.goodreads.R;
import com.ryanharter.android.goodreads.adapters.UpdatesAdapter;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;

public class UpdatesFragment extends ListFragment {
	private static final String TAG = "UpdatesFragment";
	
	private List<Update> mUpdates;
	private UpdatesAdapter mAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_with_spinner, container, false);
		
		Resources resources = getActivity().getResources();
		
		// Style the list a little bit
		ListView list = (ListView) view.findViewById(android.R.id.list);
		list.setDivider(resources.getDrawable(R.drawable.bg_tiles));
		list.setDividerHeight(8);
		
		new GetUpdatesTask().execute();
		
		return view;
	}
	
	public class GetUpdatesTask extends AsyncTask<Void, Void, List<Update>> {
		
		protected List<Update> doInBackground(Void... nothing) {
			List<Update> updates = null;
			try {
				updates = GoodreadsService.getFriendsUpdates();
			} catch (Exception e) {
				Log.e(TAG, "Failed to get friends updates.", e);
			}
			return updates;
		}
		
		protected void onPostExecute(List<Update> updates) {
			mUpdates = updates;
			
			mAdapter = new UpdatesAdapter(getActivity(), mUpdates);
			setListAdapter(mAdapter);
		}
	}
}
