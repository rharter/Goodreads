package com.ryanharter.android.goodreads.service;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class GoodreadsService
{
	private final static String API_KEY = "3RATirCK1psi4YYZpoB96A";
	private final static String API_SECRET = "WI2y6ZCYZxIanY6uzqNNuT9J88qqcGDXA4Y7WnKHiA";
	
	private static CommonsHttpOAuthConsumer sConsumer = 
			new CommonsHttpOAuthConsumer(API_KEY, API_SECRET);
	
	private static boolean sAuthenticated = false;
	
	private static void setAuthenticated(boolean authenticated) {
		GoodreadsService.sAuthenticated = authenticated;
	}
	
	public static boolean isAuthenticated() {
		return sAuthenticated;
	}
	
	public static void setTokenWithSecret(String token, String secret) {
		sConsumer.setTokenWithSecret(token, secret);
		setAuthenticated(true);
	}
	
	public static void clearAuthentication() {
		setAuthenticated(false);
	}
}