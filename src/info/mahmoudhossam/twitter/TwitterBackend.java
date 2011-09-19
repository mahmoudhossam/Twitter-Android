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
	
	private CommonsHttpOAuthConsumer consumer;
	private CommonsHttpOAuthProvider provider;
	private static Twitter twitter;
	private static final String consumerKey = "q6WPy4of06Jin1X1YwRqaw";
	private static final String consumerSecret = "pbGmIHByoSbsSmw4gSSWPU8KvT43r3F1DW9E0ztRw";

	public void OAuthInit() {
		consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
		provider = new CommonsHttpOAuthProvider(
				"https://api.twitter.com/oauth/request_token",
				"https://api.twitter.com/oauth/access_token",
				"https://api.twitter.com/oauth/authorize");
	}

	public String getAuthorizationURL() throws OAuthMessageSignerException,
			OAuthNotAuthorizedException, OAuthExpectationFailedException,
			OAuthCommunicationException {
		return provider.retrieveRequestToken(consumer, "http://oauth.gmodules.com/gadgets/oauthcallback");
	}

	public void getAccessToken(String verifier)
			throws OAuthMessageSignerException, OAuthNotAuthorizedException,
			OAuthExpectationFailedException, OAuthCommunicationException {
		provider.retrieveAccessToken(consumer, verifier);
	}

	public void TwitterInit(){
		AccessToken token = new AccessToken(consumer.getToken(), consumer.getTokenSecret());
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
		twitter.setOAuthAccessToken(token);
	}
	
	public static Twitter getTwitterInstance(){
		return twitter;
	}

}
