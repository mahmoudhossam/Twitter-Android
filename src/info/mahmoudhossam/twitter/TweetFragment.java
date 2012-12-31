package info.mahmoudhossam.twitter;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockListFragment;
import twitter4j.Paging;
import twitter4j.Twitter;

public abstract class TweetFragment extends SherlockListFragment {

	protected Twitter twitter;
	protected Paging paging;

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

	public abstract void refresh();
}
