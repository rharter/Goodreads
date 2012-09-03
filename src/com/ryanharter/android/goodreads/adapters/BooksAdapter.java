package com.ryanharter.android.goodreads.adapters;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import com.goodreads.api.v1.Review;
import com.goodreads.api.v1.Author;
import com.ryanharter.android.goodreads.R;

import com.google.android.imageloader.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.ImageView;
import android.util.Log;

public class BooksAdapter extends ArrayAdapter<Review> {
	public static final String TAG = "BooksAdapter";
	
	private Context mContext;
	private ImageLoader mImageLoader;
	private SimpleDateFormat mDateFormatter;
	
	public BooksAdapter(Context context, List<Review> reviews) {
		super(context, -1);
		mContext = context;
		mImageLoader = new ImageLoader();
		mDateFormatter = new SimpleDateFormat("L d");
		addAll(reviews);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		BookView bookView = null;
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_book, parent, false);
			
			bookView = new BookView();
			bookView.image = (ImageView) view.findViewById(R.id.image);
			bookView.title = (TextView) view.findViewById(R.id.title);
			bookView.author = (TextView) view.findViewById(R.id.author);
			bookView.pubYear = (TextView) view.findViewById(R.id.pub_year);
			bookView.readAt = (TextView) view.findViewById(R.id.read_at);
			bookView.rating = (RatingBar) view.findViewById(R.id.rating);
			
			view.setTag(bookView);
		} else {
			bookView = (BookView) view.getTag();
		}
		
		Review review = getItem(position);
		
		bookView.title.setText(review.getBook().getTitle());
		bookView.pubYear.setText(String.valueOf(review.getBook().getYearPublished()));
		
		// Sets the read date if it exists.
		if (review.getReadAt() != null && !review.getReadAt().isEmpty()) {
			bookView.readAt.setVisibility(View.VISIBLE);
			
			SimpleDateFormat sdf = new SimpleDateFormat("E L d k:m:s Z y");
		
			try {
				Date readAt = sdf.parse(review.getReadAt());
				bookView.readAt.setText("Read " + mDateFormatter.format(review.getReadAt()));
			} catch (ParseException e) {
				Log.e(TAG, "Failed to parse date: " + review.getReadAt());
			}
		} else {
			bookView.readAt.setVisibility(View.GONE);
		}
		
		// Sets the rating (stars)
		bookView.rating.setRating(review.getRating());
		
		// Set the author to a name or list
		String author = "by ";
		List<Author> authors = review.getBook().getAuthors();
		if (authors.size() == 1) {
			author += authors.get(0).getName();
		} else {
			for (int i = 0; i < authors.size(); i++) {
				if (i > 0) {
					author += ", ";
				}
				
				author += authors.get(i).getName();
			}
		}
		bookView.author.setText(author);
		
		// Loads the image
		mImageLoader.bind(this, bookView.image, review.getBook().getImageUrl());
		
		return view;
	}
	
	protected static class BookView {
		protected ImageView image;
		protected TextView title;
		protected TextView author;
		protected TextView pubYear;
		protected RatingBar rating;
		protected TextView readAt;
	}
}
