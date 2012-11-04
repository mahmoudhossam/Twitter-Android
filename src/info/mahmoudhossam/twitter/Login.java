package info.mahmoudhossam.twitter;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Login extends Activity {

	private static final int OAUTH_REQUEST = 1;
	private static final String PREFS = "prefs";
	private TwitterBackend backend;
	private SharedPreferences prefs;
	static String consumerKey;
	static String consumerSecret;
	private AccessToken token;
	
	private void initializeVariables() {
		consumerKey = getResources().getString(R.string.consumer_key);
		consumerSecret = getResources().getString(R.string.consumer_secret);
		backend = new TwitterBackend(consumerKey, consumerSecret);
		prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeVariables();
		if (tokenExists()) {
			getToken();
			backend.twitterInit(token);
			startActivity(new Intent("mahmoud.post"));
			finish();
		} else {
			login();
		}

	}

	private void login() {
		Intent intent = new Intent("mahmoud.browser");
		String url = null;
		try {
			url = backend.getAuthorizationURL();
		} catch (TwitterException e) {
			Log.e("twitter", e.getMessage());
		}
		intent.putExtra("url", url);
		startActivityForResult(intent, OAUTH_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == OAUTH_REQUEST && resultCode == RESULT_OK) {
			Uri url = Uri.parse(data.getExtras().getString("url"));
			String verifier = url.getQueryParameter("oauth_verifier");
			Log.i("Verifier", verifier);
			try {
				backend.setAccessToken(verifier);
			} catch (TwitterException e) {
				Log.e("twitter", e.getMessage());
			}
			backend.twitterInit();
			saveToken();
			startActivity(new Intent("mahmoud.post"));
			finish();
		} else if (resultCode == RESULT_CANCELED) {
			Toast.makeText(this,
					"Cannot connect to twitter, app not authorized",
					Toast.LENGTH_SHORT).show();
		}
	}

	private boolean tokenExists() {
		return prefs.contains("oauth_token") && prefs.contains("oauth_secret");
	}

	private void saveToken() {
		token = backend.getAccessToken();
		if (token != null) {
			Editor editor = prefs.edit();
			editor.putString("oauth_token", token.getToken());
			editor.putString("oauth_secret", token.getTokenSecret());
			editor.commit();
		}
	}

	private void getToken() {
		String accessToken = prefs.getString("oauth_token", null);
		String secret = prefs.getString("oauth_secret", null);
		if (accessToken != null && secret != null) {
			token = new AccessToken(accessToken, secret);
		}
		
	}

}