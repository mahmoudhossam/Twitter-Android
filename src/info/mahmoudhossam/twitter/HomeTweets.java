package info.mahmoudhossam.twitter;

import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockListFragment;

public class HomeTweets extends SherlockListFragment {

	private Twitter twitter;
	private Paging paging;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeVariables();
		new RetrieveTweets().execute(paging);
	}

	private void initializeVariables() {
		twitter = TwitterBackend.getTwitterInstance();
		paging = new Paging(1, 20);
	}

	class RetrieveTweets extends AsyncTask<Paging, Integer, List<Status>> {

		@Override
		protected List<twitter4j.Status> doInBackground(Paging... params) {
			try {
				return twitter.getHomeTimeline(params[0]);
			} catch (TwitterException e) {
				Log.e("Twitter", e.getMessage());
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<twitter4j.Status> result) {
			if (result != null) {
				setListAdapter(new TweetAdapter(getActivity()
						.getApplicationContext(), R.layout.tweet, result));
			}
		}

	}

}
