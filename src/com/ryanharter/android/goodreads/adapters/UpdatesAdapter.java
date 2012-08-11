// package com.ryanharter.android.goodreads.adapters;
// 
// import com.goodreads.api.v1.Update;
// import com.ryanharter.android.goodreads.R;
// 
// import android.content.Context;
// import android.view.LayoutInflater;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.ArrayAdapter;
// import android.widget.ImageView;
// import android.widget.TextView;
// 
// public class UpdatesAdapter extends ArrayAdapter<Update> {
// 	
// 	private Context context;
// 	
// 	/**
// 	 * Replaces the normal constructors and builds a new 
// 	 * NavigationAdapter.
// 	 */
// 	public UpdatesAdapter(Context context) {	
// 		super(context, 0);
// 		this.context = context;
// 	}
// 	
// 	/**
// 	 * Creates
// 	 */
// 	@Override
// 	public View getView (int position, View convertView, ViewGroup parent) {
// 		View v = convertView;
// 		NavigationView navView = null;
// 		
// 		if (v == null) {
// 			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
// 			v = inflater.inflate(R.layout.row_navigation, parent, false);
// 			
// 			// Set up the holder
// 			navView = new NavigationView();
// 			navView.image = (ImageView) v.findViewById(R.id.image);
// 			navView.name = (TextView) v.findViewById(R.id.name);
// 			
// 			// Store the view holder
// 			v.setTag(navView);
// 		} else {
// 			navView = (NavigationView) v.getTag();
// 		}
// 		
// 		NavigationItem item = getItem(position);
// 		navView.image.setImageResource(item.imageId);
// 		navView.name.setText(item.name);
// 		
// 		return v;
// 	}
// }
