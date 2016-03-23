package info.mahmoudhossam.twitter.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import info.mahmoudhossam.twitter.R;
import info.mahmoudhossam.twitter.Activities.Tweet;
import info.mahmoudhossam.twitter.TweetAdapter;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;

public class Mentions extends TweetFragment {

    private List<Status> mentions;

	@Override
	public void refresh() {
		new RetrieveTweets().execute(paging);
	}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Status status = mentions.get(position);
        Intent intent = new Intent(getActivity(), Tweet.class);
        intent.putExtra("text", status.getText());
        intent.putExtra("username", status.getUser().getScreenName());
        intent.putExtra("owner", status.getUser().getName());
        intent.putExtra("time", status.getCreatedAt().toString());
        startActivity(intent);
    }

    private class RetrieveTweets extends AsyncTask<Paging, Integer, List<twitter4j.Status>> {

		@Override
		protected List<twitter4j.Status> doInBackground(Paging... arg0) {
			try {
				mentions = twitter.getMentionsTimeline(arg0[0]);
                return mentions;
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
