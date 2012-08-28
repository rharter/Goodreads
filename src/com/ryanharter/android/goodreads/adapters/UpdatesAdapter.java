package com.ryanharter.android.goodreads.adapters;

import java.util.List;

import com.goodreads.api.v1.Update;

import com.ryanharter.android.goodreads.R;

import com.google.android.imageloader.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

public class UpdatesAdapter extends ArrayAdapter<Update> {
	private static final String TAG = "UpdatesAdapter";
	
 	private Context mContext;
	
	/**
	 * Replaces the normal constructors and builds a new 
	 * NavigationAdapter.
 	 */
 	public UpdatesAdapter(Context context, List<Update> updates) {	
 		super(context, 0);
 		mContext = context;
 		addAll(updates);
 	}
 	
 	/**
 	 * Creates a view to display a Goodreads Update.
 	 */
 	@Override
 	public View getView (int position, View convertView, ViewGroup parent) {
 		View v = convertView;
 		UpdateViewHolder holder = null;
 		
 		Log.d(TAG, "Getting view for position: " + position);
 		
 		if (v == null) {
 			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 			v = inflater.inflate(R.layout.row_update, parent, false);
 			
 			// Set up the holder
 			holder = new UpdateViewHolder();
 			holder.image = (ImageView) v.findViewById(R.id.image);
 			holder.action = (TextView) v.findViewById(R.id.action);
 			
 			// Store the view holder
 			v.setTag(holder);
 		} else {
 			holder = (UpdateViewHolder) v.getTag();
 		}
 		
 		Update update = getItem(position);
 		holder.action.setText(update.getActionText());
 		
 		ImageLoader imageLoader = new ImageLoader();
 		Log.d(TAG, "Loading image for url: " + update.getImageUrl());
 		imageLoader.bind(this, holder.image, update.getImageUrl());
 		
 		return v;
 	}
 	
 	private static class UpdateViewHolder {
 		public ImageView image;
 		public TextView action;
 	}
 }