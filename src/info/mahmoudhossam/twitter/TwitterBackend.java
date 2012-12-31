package info.mahmoudhossam.twitter;

import android.os.AsyncTask;
import android.util.Log;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.util.concurrent.ExecutionException;

public class TwitterBackend {

	private static Twitter twitter;
	private static final String CALLBACK_URL = "http://oauth.gmodules.com/gadgets/oauthcallback";
	private AccessToken token;
	private RequestToken requestToken;

	public TwitterBackend(String consumerKey, String consumerSecret) {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
	}

	public String getAuthorizationURL() {
		try {
			return new AuthURLGetter().execute().get();
		} catch (InterruptedException e) {
			Log.e("twitter", e.getMessage());
		} catch (ExecutionException e) {
			Log.e("twitter", e.getMessage());
		}
		return null;
	}

	public AccessToken getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken, String tokenSecret) {
		token = new AccessToken(accessToken, tokenSecret);
	}

	public void setAccessToken(String verifier) {
		try {
			token = new AccessTokenGetter().execute(verifier).get();
		} catch (InterruptedException e) {
			Log.e("Twitter", e.getMessage());
		} catch (ExecutionException e) {
			Log.e("Twitter", e.getMessage());
		}
	}

	public void twitterInit(AccessToken accessToken) {
		token = accessToken;
		twitterInit();
	}

	public void twitterInit() {
		twitter.setOAuthAccessToken(token);
	}

	public static Twitter getTwitterInstance() {
		return twitter;
	}

	class AuthURLGetter extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			try {
				requestToken = twitter.getOAuthRequestToken(CALLBACK_URL);
				return requestToken.getAuthorizationURL();
			} catch (TwitterException e) {
				Log.e("Twitter", e.getMessage());
				return null;
			}
		}
	}

	class AccessTokenGetter extends AsyncTask<String, Integer, AccessToken> {

		@Override
		protected AccessToken doInBackground(String... arg0) {
			try {
				return twitter.getOAuthAccessToken(requestToken, arg0[0]);
			} catch (TwitterException e) {
				Log.e("Twitter", e.getMessage());
				return null;
			}
		}
	}

}
