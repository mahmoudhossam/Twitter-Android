package info.mahmoudhossam.twitter;

import java.util.List;

import twitter4j.Status;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TweetAdapter extends BaseAdapter {

	private List<Status> list;
	private int layout;
	private Context ctx;

	public TweetAdapter(Context context, int resource, List<Status> tweets) {
		list = tweets;
		layout = resource;
		ctx = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layout, null);
		}
		TextView text = (TextView) convertView.findViewById(R.id.tweet);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		Status current = list.get(position);
		text.setText(current.getText());
		name.setText(current.getUser().getName());

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
