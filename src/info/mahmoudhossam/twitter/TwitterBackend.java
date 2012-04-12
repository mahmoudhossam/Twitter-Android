package info.mahmoudhossam.twitter;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterBackend {

	private static Twitter twitter;
	private static final String CONSUMER_KEY = Login.consumerKey;
	private static final String CONSUMER_SECRET = Login.consumerSecret;
	private static final CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(
			CONSUMER_KEY, CONSUMER_SECRET);
	private static final CommonsHttpOAuthProvider provider = new CommonsHttpOAuthProvider(
			"https://api.twitter.com/oauth/request_token",
			"https://api.twitter.com/oauth/access_token",
			"https://api.twitter.com/oauth/authorize");;
	private AccessToken token;

	public String getAuthorizationURL() throws OAuthMessageSignerException,
			OAuthNotAuthorizedException, OAuthExpectationFailedException,
			OAuthCommunicationException {
		return provider.retrieveRequestToken(consumer,
				"http://oauth.gmodules.com/gadgets/oauthcallback");
	}

	public AccessToken getAccessToken() {
		return token;
	}

	public void setAccessToken(String verifier)
			throws OAuthMessageSignerException, OAuthNotAuthorizedException,
			OAuthExpectationFailedException, OAuthCommunicationException {
		provider.retrieveAccessToken(consumer, verifier);
	}

	public void twitterInit() {
		token = new AccessToken(consumer.getToken(), consumer.getTokenSecret());
		twitterInit(token);
	}
	
	public void twitterInit(AccessToken token){
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		twitter.setOAuthAccessToken(token);
	}

	public static Twitter getTwitterInstance() {
		return twitter;
	}

}
