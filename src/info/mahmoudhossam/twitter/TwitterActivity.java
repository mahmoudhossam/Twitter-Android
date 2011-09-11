package info.mahmoudhossam.twitter;

import org.scribe.model.Token;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TwitterActivity extends Activity {

	private static int OAUTH_REQUEST = 1;
	private static String consumerKey = "WofP1nxarEBwp2gl2oRsOg";
	private static String consumerSecret = "zVApqpi9CzPV4zL7ytefMLpvFu2MpWd9HHryIotw";
	private Twitter twitter;
	private Token accessToken;
	private TwitterBackend backend;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button loginButton = (Button) findViewById(R.id.button1);
		registerForContextMenu(loginButton);
		backend = new TwitterBackend();
	}

	public void onLogin(View view) {
		Intent intent = new Intent("mahmoud.browser");
		intent.putExtra("url",
				backend.oauthConnect(consumerKey, consumerSecret));
		startActivityForResult(intent, OAUTH_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == OAUTH_REQUEST && resultCode == RESULT_OK) {
			String url = data.getExtras().getString("url");
			String verifier = getVerifier(url);
			Log.i("Verifier", verifier);
			accessToken = backend.getAccessToken(verifier);
			// persistAccessToken(accessToken);
			twitter = backend.getTwitterInstance(consumerKey, consumerSecret, accessToken);
		} else if (resultCode == RESULT_CANCELED) {
			Toast.makeText(this,
					"Cannot connect to twitter, app not authorized",
					Toast.LENGTH_SHORT).show();
		}
	}

	// public void persistAccessToken(AccessToken token){
	// SharedPreferences prefs = getSharedPreferences("token", 0);
	// Editor editor = prefs.edit();
	// editor.putString("AccessToken", token.getToken());
	// editor.putString("AccessSecret", token.getTokenSecret());
	// if(editor.commit()){
	// Log.i("Twitter", "Access token saved");
	// }
	// }

	private String getVerifier(String url) {
		String[] parts = url.split("=");
		return parts[parts.length - 1];
	}

}