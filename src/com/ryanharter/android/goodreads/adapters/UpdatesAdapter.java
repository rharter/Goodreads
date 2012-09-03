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
import android.text.Html;
import android.util.Log;

public class UpdatesAdapter extends ArrayAdapter<Update> {
	private static final String TAG = "UpdatesAdapter";
	
 	private Context mContext;
 	private ImageLoader mImageLoader;
	
	/**
	 * Replaces the normal constructors and builds a new 
	 * NavigationAdapter.
 	 */
 	public UpdatesAdapter(Context context, List<Update> updates) {	
 		super(context, 0);
 		mContext = context;
 		mImageLoader = new ImageLoader();
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
 			holder.profileImage = (ImageView) v.findViewById(R.id.profile_image);
 			holder.action = (TextView) v.findViewById(R.id.action);
 			holder.name = (TextView) v.findViewById(R.id.name);
 			
 			// Store the view holder
 			v.setTag(holder);
 		} else {
 			holder = (UpdateViewHolder) v.getTag();
 		}
 		
 		Update update = getItem(position);
 		holder.name.setText(update.getActor().getName());
 		holder.action.setText(Html.fromHtml(update.getActionText()));
 		
 		mImageLoader.bind(this, holder.profileImage, update.getActor().getImageUrl());
 		
 		String imageUrl = update.getImageUrl();
 		
 		if (imageUrl != null) {
 			int smallTag = -1;
 			char largeTag = 'l';
 			if (imageUrl.contains("/books/")) {
		 		smallTag = imageUrl.lastIndexOf("s/");
		 	} else if (imageUrl.contains("/authors/")) {
		 		smallTag = imageUrl.lastIndexOf("p2/") + 1;
		 		largeTag = '5';
		 	}
		 		
		 	if (smallTag > 0) {
		 		char[] chars = imageUrl.toCharArray();
		 		chars[smallTag] = largeTag;
		 		
		 		imageUrl = new String(chars);
		 	}
	 	} else {
	 		imageUrl = update.getImageUrl();
	 	}
 		
 		Log.d(TAG, "Loading image: " + imageUrl);
 		
 		mImageLoader.bind(holder.image, imageUrl, null);
 		
 		return v;
 	}
 	
 	private static class UpdateViewHolder {
 		public ImageView image;
 		public ImageView profileImage;
 		public TextView action;
 		public TextView name;
 	}
 }
