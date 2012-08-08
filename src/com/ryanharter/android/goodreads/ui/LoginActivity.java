package com.ryanharter.android.goodreads.ui;

import com.goodreads.api.v1.GoodreadsService;
import com.goodreads.api.v1.User;
import com.ryanharter.android.goodreads.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

public class LoginActivity extends Activity
{
	private final static String TAG = "LoginActivity";
	private final static String API_KEY = "3RATirCK1psi4YYZpoB96A";
	private final static String API_SECRET = "WI2y6ZCYZxIanY6uzqNNuT9J88qqcGDXA4Y7WnKHiA";
	private final static String CALLBACK = "oauth://goodreads";
	private final static CommonsHttpOAuthConsumer sConsumer = new CommonsHttpOAuthConsumer(API_KEY, API_SECRET);
	
	private final static OAuthProvider sProvider = new DefaultOAuthProvider(
			"http://www.goodreads.com/oauth/request_token",
			"http://www.goodreads.com/oauth/access_token", 
			"http://www.goodreads.com/oauth/authorize");
	
	private WebView webview;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

		webview = (WebView) findViewById(R.id.webview);

		// Attach the WebViewClient to intercept the callback url
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				// Check for our custom callback protocol
				Log.d(TAG, "Overriding url: " + url);
				if (url.startsWith("oauth"))
				{
					SharedPreferences sharedPreferences = getSharedPreferences("com.ryanharter.android.goodreads", MODE_PRIVATE);
					String requestToken = sharedPreferences.getString("RequestToken", "");
					String requestTokenSecret = sharedPreferences.getString("RequestTokenSecret", "");
					
					// authorization complete
					Uri uri = Uri.parse(url);
					String oauthToken = uri.getQueryParameter(OAuth.OAUTH_TOKEN);
					
					// Populate the token and token secret in the consumer
					// Warning: Crazy shit can happen here
					try {
						sConsumer.setTokenWithSecret(requestToken, requestTokenSecret);
						sProvider.retrieveAccessToken(sConsumer, oauthToken);
					} catch (OAuthMessageSignerException e) {
						
					} catch (OAuthNotAuthorizedException e) {
						
					} catch (OAuthExpectationFailedException e) {
						
					} catch (OAuthCommunicationException e) {
						
					}
					
					String token = sConsumer.getToken();
					String tokenSecret = sConsumer.getTokenSecret();
					String userId = "";
					Log.d(TAG, "Setting token[" + token + "]");
					Log.d(TAG, "Setting secret[" + tokenSecret + "]");
					GoodreadsService.setTokenWithSecret(token, tokenSecret);
					
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString("token", token);
					editor.putString("tokenSecret", tokenSecret);
					editor.commit();
					
					// Get the authorized user
					new GetUserInfoTask(token, tokenSecret).execute();
					return true;
				}
				
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		
		new GetAuthUrlTask().execute();
    }

	private class GetUserInfoTask extends AsyncTask<Void, Void, String> {
		
		private String mToken;
		private String mSecret;
		
		public GetUserInfoTask(String token, String secret) {
			super();
			mToken = token;
			mSecret = secret;
		}
		
		protected String doInBackground(Void... something) {
			User authorizedUser = null;
			try {
				authorizedUser = GoodreadsService.getAuthorizedUser();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String userId = authorizedUser.getId();
			Log.d(TAG, "userId: " + userId);
			return userId;
		}
		
		protected void onPostExecute(String userId) {
			// Send the tokens back to the calling 
			Intent tokens = new Intent();
			tokens.putExtra("com.ryanharter.android.goodreads.token", mToken);
			tokens.putExtra("com.ryanharter.android.goodreads.tokenSecret", mSecret);
			tokens.putExtra("com.ryanharter.android.goodreads.userId", userId);
			setResult(RESULT_OK, tokens);
			
			LoginActivity.this.finish();
		}
	}

	private class GetAuthUrlTask extends AsyncTask<Void, Void, String> {
		
		protected String doInBackground(Void... something) {
			// Set up the service and get request token
			String authUrl = "";
			try {
				authUrl = sProvider.retrieveRequestToken(sConsumer, CALLBACK);
			} catch (OAuthMessageSignerException e) {
				Log.e(TAG, "OAuthMessageSignerException", e);
			} catch (OAuthNotAuthorizedException e) {
				Log.e(TAG, "OAuthNotAuthorizedException", e);
			} catch (OAuthExpectationFailedException e) {
				Log.e(TAG, "OAuthExpectationFailedException", e);
			} catch (OAuthCommunicationException e) {
				Log.e(TAG, "OAuthCommunicationException", e);
			}
			Log.d(TAG, "Got authUrl: " + authUrl);
			
			SharedPreferences sharedPreferences = getSharedPreferences("com.ryanharter.android.goodreads", MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("RequestToken", sConsumer.getToken());
			editor.putString("RequestTokenSecret", sConsumer.getTokenSecret());
			editor.commit();
			
			return authUrl;
		}
		
		protected void onPostExecute(String authUrl) {
			webview.loadUrl(authUrl);
		}
	}
}
