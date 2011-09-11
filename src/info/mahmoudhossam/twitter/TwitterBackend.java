package info.mahmoudhossam.twitter;

import java.util.List;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterBackend {
	private static Twitter twitter;
	private OAuthService service;
	
	public Twitter getTwitterInstance(String consumerKey,
			String consumerSecret, Token accessToken){
		if(twitter == null){
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true);
			cb.setOAuthConsumerKey(consumerKey);
			cb.setOAuthConsumerSecret(consumerSecret);
			cb.setOAuthAccessToken(accessToken.getToken());
			cb.setOAuthAccessTokenSecret(accessToken.getSecret());
			twitter = new TwitterFactory(cb.build()).getInstance();
		}
		return twitter;
	}
	
	public String oauthConnect(String consumerKey, String consumerSecret){
    	service = new ServiceBuilder()
    	.provider(TwitterApi.class).apiKey(consumerKey)
    	.apiSecret(consumerSecret)
    	.callback("http://oauth.gmodules.com/gadgets/oauthcallback").build();
    	return service.getAuthorizationUrl(service.getRequestToken());
    }
	
	public Token getAccessToken(String verifier){
		return service.getAccessToken(service.getRequestToken(), new Verifier(verifier));
	}
	
	public List<Status> getTimeline() throws TwitterException{
		return twitter.getHomeTimeline();
		
	}
	
	public void updateStatus(String status) throws TwitterException{
		twitter.updateStatus(status);
	}
	

}
