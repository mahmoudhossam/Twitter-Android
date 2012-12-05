package info.mahmoudhossam.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.Status;
import android.app.Activity;
import android.os.AsyncTask;
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

	public void onTweet(View view) {
		String tweet = text.getText().toString();
		new StatusPoster().execute(tweet);

		finish();
	}

	class StatusPoster extends AsyncTask<String, Integer, Status> {

		@Override
		protected twitter4j.Status doInBackground(String... params) {
			try {
				return twitter.updateStatus(params[0]);
			} catch (TwitterException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(twitter4j.Status result) {
			super.onPostExecute(result);
			if (result != null) {
				Toast.makeText(getApplicationContext(), "Posted",
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(getApplicationContext(),
						"Error, could not connect to twitter",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

}
