package com.ryanharter.android.goodreads.ui;

import com.goodreads.api.v1.Update;
import com.ryanharter.android.goodreads.R;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UpdatesFragment extends ListFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_updates, container, false);
		
		ListView listview = (ListView) view.findViewById(android.R.id.list);
		
		String[] updates = { "Update 1", "Update 2", "Update 3", "Update 4" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, updates);
		
		setListAdapter(adapter);
		
		return view;
	}
}
