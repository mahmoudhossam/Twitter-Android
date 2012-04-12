package info.mahmoudhossam.twitter;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.markupartist.android.widget.ActionBar;

public class Tweets extends ListActivity{

	private Twitter twitter;
	private ActionBar actionbar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweets);
		initializeVariables();
		new RetrieveTweets().execute(twitter);
	}

	private void initializeVariables() {
		actionbar = (ActionBar) findViewById(R.id.actionBar1);
		twitter = TwitterBackend.getTwitterInstance();
	}

	class myAdapter extends BaseAdapter {

		private List<Status> list;
		private int layout;

		public myAdapter(Context context, int resource, List<Status> tweets) {
			list = tweets;
			layout = resource;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = getLayoutInflater();
				convertView = inflater.inflate(layout, null);
			}
			TextView text = (TextView) convertView.findViewById(R.id.tweet);
			TextView name = (TextView) convertView.findViewById(R.id.name);
			TextView time = (TextView) convertView.findViewById(R.id.timestamp);
			Status current = list.get(position);
			text.setText(current.getText());
			name.setText(current.getUser().getName());
			time.setText("" + current.getCreatedAt().getHours() + ":"
					+ current.getCreatedAt().getMinutes());

			return convertView;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return getItem(position).hashCode();
		}

		@Override
		public boolean isEmpty() {
			return getCount() == 0;
		}
		
	}

	class RetrieveTweets extends AsyncTask<Twitter, Integer, List<Status>> {

		@Override
		protected void onPreExecute() {
			actionbar.setProgressBarVisibility(ActionBar.VISIBLE);
		}

		@Override
		protected List<twitter4j.Status> doInBackground(Twitter... params) {
			try {
				return params[0].getHomeTimeline();
			} catch (TwitterException e) {
				Log.e("Twitter", e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<twitter4j.Status> result) {
			ListView lv = getListView();
			lv.setAdapter(new myAdapter(getApplicationContext(),
					R.layout.tweet, result));
			actionbar.setProgressBarVisibility(ActionBar.INVISIBLE);
		}

	}

}
