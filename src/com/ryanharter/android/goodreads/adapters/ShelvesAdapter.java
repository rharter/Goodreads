package com.ryanharter.android.goodreads.adapters;

import java.util.List;

import com.goodreads.api.v1.UserShelf;
import com.ryanharter.android.goodreads.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ShelvesAdapter extends ArrayAdapter<UserShelf> {
	
	private Context mContext;
	
	public ShelvesAdapter(Context context, List<UserShelf> shelves) {
		super(context, -1);
		mContext = context;
		addAll(shelves);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ShelfView shelfView = null;
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_shelf, parent, false);
			
			shelfView = new ShelfView();
			shelfView.name = (TextView) view.findViewById(R.id.name);
			shelfView.count = (TextView) view.findViewById(R.id.count);
			
			view.setTag(shelfView);
		} else {
			shelfView = (ShelfView) view.getTag();
		}
		
		UserShelf shelf = getItem(position);
		shelfView.name.setText(shelf.getName());
		
		shelfView.count.setText(Integer.toString(shelf.getBookCount()));
		
		return view;
	}
	
	protected static class ShelfView {
		protected TextView name;
		protected TextView count;
	}
}
