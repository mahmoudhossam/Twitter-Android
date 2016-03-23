package info.mahmoudhossam.twitter.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import info.mahmoudhossam.twitter.R;
import info.mahmoudhossam.twitter.Activities.Tweet;
import info.mahmoudhossam.twitter.TweetAdapter;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;

public class HomeTweets extends TweetFragment {

    private List<Status> home;

    @Override
	public void refresh() {
		new RetrieveTweets().execute(paging);
	}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Status status = home.get(position);
        Intent intent = new Intent(getActivity(), Tweet.class);
        intent.putExtra("text", status.getText());
        intent.putExtra("username", status.getUser().getScreenName());
        intent.putExtra("owner", status.getUser().getName());
        intent.putExtra("time", status.getCreatedAt().toString());
        startActivity(intent);
    }

	private class RetrieveTweets extends AsyncTask<Paging, Integer, List<Status>> {

		@Override
		protected List<twitter4j.Status> doInBackground(Paging... params) {
			try {
				home = twitter.getHomeTimeline(params[0]);
                return home;
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
