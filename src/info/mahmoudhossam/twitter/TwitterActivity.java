package info.mahmoudhossam.twitter;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TwitterActivity extends Activity {
	private static String apiKey = "WofP1nxarEBwp2gl2oRsOg";
	private static String apiSecret = "zVApqpi9CzPV4zL7ytefMLpvFu2MpWd9HHryIotw";
	private OAuthService service;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
	}

	public void oauthConnect(){
    	service = new ServiceBuilder()
    	.provider(TwitterApi.class).apiKey(apiKey)
    	.apiSecret(apiSecret).build();
    }
	
	public Token getToken(){
		return service.getRequestToken();
	}
	
	public String getVerifier(){
		oauthConnect();
		return service.getAuthorizationUrl(getToken());
	}
	
	public void onLogin(View view){
		Intent intent = new Intent("mahmoud.view");
		intent.putExtra("url", getVerifier());
		startActivity(intent);
	}
}