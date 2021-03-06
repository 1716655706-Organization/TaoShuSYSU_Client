package com.sysu.taosysu;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.sysu.taosysu.ui.fragment.BookListFragment;
import com.sysu.taosysu.ui.fragment.NavigationDrawerFragment;
import com.sysu.taosysu.ui.fragment.PersonalInfoFragment;
import com.sysu.taosysu.ui.fragment.PublishBookFragment;
import com.sysu.taosysu.ui.fragment.QuitDialogFragment;
import com.sysu.taosysu.ui.fragment.SearchFragment;

public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	private NavigationDrawerFragment mNavigationDrawerFragment;

	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FragmentManager fm = getFragmentManager();
		mNavigationDrawerFragment = (NavigationDrawerFragment) fm
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		Fragment mFragment = null;

		switch (position) {
		case NavigationDrawerFragment.POSITION_PERSONAL_INFO:
			mFragment = new PersonalInfoFragment();
			break;
		case NavigationDrawerFragment.POSITION_HOME:
			mFragment = new BookListFragment();
			break;
		case NavigationDrawerFragment.POSITION_PUBLISH:
			mFragment = new PublishBookFragment();
			break;
		case NavigationDrawerFragment.POSITION_SEARCH:
			mFragment = new SearchFragment();
			break;
		case NavigationDrawerFragment.POSITION_QUIT:
			mFragment = new QuitDialogFragment();
			break;
		default:
			break;
		}

		FragmentManager fm = getFragmentManager();
		if ((mFragment != null) && (!(mFragment instanceof QuitDialogFragment))) {
			fm.beginTransaction().replace(R.id.container, mFragment)
					.addToBackStack(null).commit();
		} else if (mFragment instanceof QuitDialogFragment) {
			fm.beginTransaction().add(mFragment, null).commit();
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_about_us) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
