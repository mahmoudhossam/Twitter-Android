package info.mahmoudhossam.twitter;

import java.util.List;

import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.os.AsyncTask;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockListFragment;

public class UserTweets extends SherlockListFragment {
	private Twitter twitter;
	private Paging paging;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeVariables();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		refresh();
	}

	private void initializeVariables() {
		twitter = TwitterBackend.getTwitterInstance();
		paging = new Paging(1, 20);
	}
	
	private void refresh() {
		new RetrieveMentions().execute(paging);
	}
	
	class RetrieveMentions extends AsyncTask<Paging, Integer, List<twitter4j.Status>> {

		@Override
		protected List<twitter4j.Status> doInBackground(Paging... arg0) {
			try {
				return twitter.getUserTimeline(arg0[0]);
			} catch (TwitterException e) {
				e.printStackTrace();
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
