package info.mahmoudhossam.twitter;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Login extends Activity {

	private static final int OAUTH_REQUEST = 1;

	private TwitterBackend backend;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button loginButton = (Button) findViewById(R.id.button1);
		registerForContextMenu(loginButton);
		backend = new TwitterBackend();
		backend.OAuthInit();

	}

	public void onLogin(View view) throws OAuthMessageSignerException,
			OAuthNotAuthorizedException, OAuthExpectationFailedException,
			OAuthCommunicationException {
		Intent intent = new Intent("mahmoud.browser");
		Log.i("URL", backend.getAuthorizationURL());
		intent.putExtra("url", backend.getAuthorizationURL());
		startActivityForResult(intent, OAUTH_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == OAUTH_REQUEST && resultCode == RESULT_OK) {
			Uri url = Uri.parse(data.getExtras().getString("url"));
			String verifier = url.getQueryParameter("oauth_verifier");
			Log.i("Verifier", verifier);
			try {
				backend.getAccessToken(verifier);
			} catch (OAuthMessageSignerException e) {
				Log.e("Twitter", e.getMessage());
			} catch (OAuthNotAuthorizedException e) {
				Log.e("Twitter", e.getMessage());
			} catch (OAuthExpectationFailedException e) {
				Log.e("Twitter", e.getMessage());
			} catch (OAuthCommunicationException e) {
				Log.e("Twitter", e.getMessage());
			}
			backend.TwitterInit();
			startActivity(new Intent("mahmoud.post"));
		} else if (resultCode == RESULT_CANCELED) {
			Toast.makeText(this,
					"Cannot connect to twitter, app not authorized",
					Toast.LENGTH_SHORT).show();
		}
	}

}