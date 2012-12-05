package info.mahmoudhossam.twitter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity implements
		ActionBar.TabListener {

	private ActionBar actionbar;
	private Fragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar();
		setContentView(R.layout.main);
	}

	public void initActionBar() {
		actionbar = getSupportActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setDisplayShowTitleEnabled(false);
		addTabs();
	}

	public void addTabs() {
		ActionBar.Tab mentions = actionbar.newTab().setText(R.string.mentions)
				.setTabListener(this);
		ActionBar.Tab home = actionbar.newTab().setText(R.string.home)
				.setTabListener(this);
		ActionBar.Tab user = actionbar.newTab().setText(R.string.my_profile)
				.setTabListener(this);
		actionbar.addTab(home);
		actionbar.addTab(mentions);
		actionbar.addTab(user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.refresh) {
			Toast.makeText(getApplicationContext(), "Refresh clicked",
					Toast.LENGTH_SHORT).show();
			return true;
		} else if (itemId == R.id.compose) {
			Toast.makeText(getApplicationContext(), "New tweet!",
					Toast.LENGTH_SHORT).show();
			return true;
		} else
			return false;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (fragment == null) {
			fragment = Fragment.instantiate(this, HomeTweets.class.getName());
			ft.add(R.id.RelativeLayout, fragment, "home");
		} else {
			if (tab.getPosition() == 0) {
				fragment = Fragment.instantiate(this,
						HomeTweets.class.getName());
			} else if (tab.getPosition() == 1) {
				fragment = Fragment.instantiate(this, Mentions.class.getName());
			} else {
				fragment = Fragment.instantiate(this, UserTweets.class.getName());
			}
			ft.replace(R.id.RelativeLayout, fragment);

		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (fragment != null) {
			ft.detach(fragment);
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

}