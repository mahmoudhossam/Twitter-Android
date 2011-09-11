package info.mahmoudhossam.twitter;

import java.util.ArrayList;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Tweets extends ListActivity {

	List<Status> tweets;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		getListView().setTextFilterEnabled(true);
		tweets = new ArrayList<Status>();
		setListAdapter(new myAdapter(this, R.layout.tweets, tweets));
	}

	class myAdapter extends ArrayAdapter<List<Status>> {

		private List<Status> list;
		
		public myAdapter(Context context, int resource, List<Status> objects) {
			super(context, resource);
			list = objects;
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
			for(Status s : list){
				text.setText(s.getText());
				name.setText(s.getUser().getName());
				time.setText(s.getCreatedAt().toString());
				avatar.setImageURI(Uri.parse(s.getUser().getProfileImageURL().toString()));
			}

			return convertView;
		}
	}

	class RetrieveTweets extends AsyncTask<Twitter, Integer, List<Status>> {

		@Override
		protected void onPreExecute() {
			setProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected List<twitter4j.Status> doInBackground(Twitter... params) {
			try {
				return params[0].getHomeTimeline();
			} catch (TwitterException e) {
				Log.e("TwitterApp", e.getErrorMessage());
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<twitter4j.Status> result) {
			tweets = result;
			setProgressBarIndeterminateVisibility(false);
		}

	}

}
