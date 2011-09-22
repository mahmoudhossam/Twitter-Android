package info.mahmoudhossam.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PostStatus extends Activity {

	private Twitter twitter;
	private EditText text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post);
		twitter = TwitterBackend.getTwitterInstance();
		text = (EditText) findViewById(R.id.editText1);
	}
	
	public void onTweet(View view) throws TwitterException{
		String tweet = text.getText().toString();
		twitter.updateStatus(tweet);
		Toast.makeText(getApplicationContext(), "Posted", Toast.LENGTH_SHORT).show();
	}

}
