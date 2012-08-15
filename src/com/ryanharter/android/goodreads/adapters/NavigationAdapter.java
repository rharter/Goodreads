package com.ryanharter.android.goodreads.adapters;

import java.util.*;
import com.ryanharter.android.goodreads.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationAdapter extends ArrayAdapter<NavigationAdapter.NavigationItem> {
	
	private Context context;
	
	/**
	 * Replaces the normal constructors and builds a new 
	 * NavigationAdapter.
	 */
	public NavigationAdapter(Context context) {	
		super(context, 0);
		this.context = context;
	}
	
	/**
	 * Adds a new navigation item by name and image id.
	 */
	public void add(int id, String name, int imageResID) {
		add(new NavigationItem(id, name, imageResID));
	}
	
	/**
	 * Creates
	 */
	@Override
	public View getView (int position, View convertView, ViewGroup parent) {
		View v = convertView;
		NavigationView navView = null;
		
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.row_navigation, parent, false);
			
			// Set up the holder
			navView = new NavigationView();
			navView.image = (ImageView) v.findViewById(R.id.image);
			navView.name = (TextView) v.findViewById(R.id.name);
			
			// Store the view holder
			v.setTag(navView);
		} else {
			navView = (NavigationView) v.getTag();
		}
		
		NavigationItem item = getItem(position);
		navView.image.setImageResource(item.imageId);
		navView.name.setText(item.name);
		
		return v;
	}
	
	protected static class NavigationView {
		protected ImageView image;
		protected TextView name;
	}
	
	/**
	 * Represents a navigation item
	 */
	public class NavigationItem {
		public int id;
		public int imageId;
		public String name;
		
		/**
		 * Creates a new Navigation item using the supplied
		 * name and image, identified by resource id.
		 */
		public NavigationItem(int id, String name, int imageResID) {
			this.id = id;
			this.imageId = imageResID;
			this.name = name;
		}
	}
}
