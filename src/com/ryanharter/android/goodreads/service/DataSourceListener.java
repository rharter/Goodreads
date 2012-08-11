package com.ryanharter.android.goodreads.service;

public interface DataSourceListener {
	
	/**
	 * Called when a web service call has been completed.
	 */
	public void onDataReceived(Object data);
}