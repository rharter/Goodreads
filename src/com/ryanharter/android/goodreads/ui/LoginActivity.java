package com.ryanharter.android.goodreads.ui;

import org.scribe.model.Token;
import org.scribe.model.Verifier;

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

public class LoginActivity extends Activity
{
	private final static String TAG = "LoginActivity";
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
				if (url.startsWith("oauth"))
				{
					Log.d(TAG, "Overriding url: " + url);
					SharedPreferences sharedPreferences = getSharedPreferences("com.ryanharter.android.goodreads", MODE_PRIVATE);
					String token = sharedPreferences.getString("RequestToken", "");
					String tokenSecret = sharedPreferences.getString("RequestTokenSecret", "");
					
					// authorization complete
					Uri uri = Uri.parse(url);
					String oauthToken = uri.getQueryParameter("oauth_token");
					
					Token requestToken = new Token(token, tokenSecret);
					new GetAccessTokenTask(oauthToken, requestToken).execute();
					return true;
				}
				
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		
		new GetAuthUrlTask().execute();
    }

	private class GetAccessTokenTask extends AsyncTask<Void, Void, Token> {
		
		private String mOauthToken;
		private Token mRequestToken;

		public GetAccessTokenTask(String oauthToken, Token requestToken) {
			super();
			mOauthToken = oauthToken;
			mRequestToken = requestToken;
		}
		
		protected Token doInBackground(Void... something) {
			return GoodreadsService.getAccessToken(mOauthToken, mRequestToken);
		}
		
		protected void onPostExecute(Token accessToken) {
			Log.d(TAG, "Setting token[" + accessToken.getToken() + "]");
			Log.d(TAG, "Setting secret[" + accessToken.getSecret() + "]");
			GoodreadsService.setAccessToken(accessToken);
			
			SharedPreferences sharedPreferences = getSharedPreferences("com.ryanharter.android.goodreads", MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("accessToken", accessToken.getToken());
			editor.putString("accessTokenSecret", accessToken.getSecret());
			editor.commit();
			
			// Get the authorized user
			new GetUserInfoTask(accessToken).execute();
		}
	}
	


	private class GetUserInfoTask extends AsyncTask<Void, Void, String> {
		
		private Token mAccessToken;
		
		public GetUserInfoTask(Token accessToken) {
			super();
			mAccessToken = accessToken;
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
			SharedPreferences sharedPreferences = getSharedPreferences("com.ryanharter.android.goodreads", MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("userId", userId);
			editor.commit();
			
			// Send the tokens back to the calling 
			Intent tokens = new Intent();
			tokens.putExtra("com.ryanharter.android.goodreads.token", mAccessToken.getToken());
			tokens.putExtra("com.ryanharter.android.goodreads.tokenSecret", mAccessToken.getSecret());
			tokens.putExtra("com.ryanharter.android.goodreads.userId", userId);
			setResult(RESULT_OK, tokens);
			
			LoginActivity.this.finish();
		}
	}

	private class GetAuthUrlTask extends AsyncTask<Void, Void, String> {
		
		protected String doInBackground(Void... something) {
			// Set up the service and get request token
			Token requestToken = GoodreadsService.getRequestToken();
			
			SharedPreferences sharedPreferences = getSharedPreferences("com.ryanharter.android.goodreads", MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("RequestToken", requestToken.getToken());
			editor.putString("RequestTokenSecret", requestToken.getSecret());
			editor.commit();
			
			String authUrl = GoodreadsService.getAuthorizationUrl(requestToken);
			Log.d(TAG, "Auth url: " + authUrl);
			
			return authUrl;
		}
		
		protected void onPostExecute(String authUrl) {
			webview.loadUrl(authUrl);
		}
	}
}
