package info.mahmoudhossam.twitter;

import java.util.List;

import twitter4j.Paging;
import twitter4j.TwitterException;
import android.os.AsyncTask;

public class Mentions extends TweetFragment {

	@Override
	public void refresh() {
		new RetrieveTweets().execute(paging);
	}
	
	class RetrieveTweets extends AsyncTask<Paging, Integer, List<twitter4j.Status>> {

		@Override
		protected List<twitter4j.Status> doInBackground(Paging... arg0) {
			try {
				return twitter.getMentions(arg0[0]);
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
