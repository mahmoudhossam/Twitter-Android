package info.mahmoudhossam.twitter;

import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;
import android.os.AsyncTask;
import android.util.Log;

public class HomeTweets extends TweetFragment {

	@Override
	public void refresh() {
		new RetrieveTweets().execute(paging);
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
