package info.mahmoudhossam.twitter;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import android.app.ListActivity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Tweets extends ListActivity {

	private List<twitter4j.Status> tweets;
	private Twitter twitter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		getListView().setTextFilterEnabled(true);
		
		RetrieveTweets rt = new RetrieveTweets();
		rt.execute(twitter);
		ListView lv = getListView();
		lv.setAdapter(new myAdapter(this, R.layout.tweets, tweets));
	}

	class myAdapter extends BaseAdapter {

		private List<twitter4j.Status> list;

		public myAdapter(Context context, int resource, List<Status> tweets) {
			list = tweets;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.tweets, null);
			}
			TextView text = (TextView) convertView.findViewById(R.id.tweet);
			TextView name = (TextView) convertView.findViewById(R.id.name);
			TextView time = (TextView) convertView.findViewById(R.id.timestamp);
			ImageView avatar = (ImageView) convertView
					.findViewById(R.id.avatar);
			for (Status s : list) {
				text.setText(s.getText());
				name.setText(s.getUser().getName());
				time.setText(s.getCreatedAt().toString());
				avatar.setImageURI(Uri.parse(s.getUser().getProfileBackgroundImageUrl()));
			}

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
			return list.get(position).getId();
		}
	}

	class RetrieveTweets extends
			AsyncTask<Twitter, Integer, List<Status>> {

		@Override
		protected void onPreExecute() {
			setProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected List<twitter4j.Status> doInBackground(
				Twitter... params) {
			try {
				return params[0].getHomeTimeline();
			} catch (TwitterException e) {
				Log.e("Twitter", e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<twitter4j.Status> result) {
			tweets = result;
			setProgressBarIndeterminateVisibility(false);
		}

	}

}
