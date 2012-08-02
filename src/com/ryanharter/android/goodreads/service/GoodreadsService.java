package com.ryanharter.android.goodreads.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class GoodreadsService
{
	private final static String API_KEY = "3RATirCK1psi4YYZpoB96A";
	private final static String API_SECRET = "WI2y6ZCYZxIanY6uzqNNuT9J88qqcGDXA4Y7WnKHiA";
	
	private static CommonsHttpOAuthConsumer sConsumer = 
			new CommonsHttpOAuthConsumer(API_KEY, API_SECRET);
	
	private static boolean sAuthenticated = false;
	
	/**
	 * Gets the id of the user who authorized OAuth
	 * 
	 * Get a xml file with the Goodreads user_id for the user 
	 * who authorized access using OAuth. http://www.goodreads.com/api#auth.user
	 */
	public static String getAuthorizedUser() throws Exception {
		HttpGet get = new HttpGet("http://www.goodreads.com/api/auth_user");
		sConsumer.sign(get);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(get);
		
		return "response.getEntity().getContent()";
	}
	
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
