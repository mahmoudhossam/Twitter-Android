package info.mahmoudhossam.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterBackend {

	private static Twitter twitter;
	private static final String CALLBACK_URL = "http://oauth.gmodules.com/gadgets/oauthcallback";
	private AccessToken token;
	private RequestToken requestToken;

	public TwitterBackend(String consumerKey, String consumerSecret) {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
	}

	public String getAuthorizationURL() throws TwitterException {
		requestToken = twitter.getOAuthRequestToken(CALLBACK_URL);
		return requestToken.getAuthorizationURL();
	}

	public AccessToken getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken, String tokenSecret) {
		token = new AccessToken(accessToken, tokenSecret);
	}

	public void setAccessToken(String verifier) throws TwitterException {
		token = twitter.getOAuthAccessToken(requestToken, verifier);
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

}
