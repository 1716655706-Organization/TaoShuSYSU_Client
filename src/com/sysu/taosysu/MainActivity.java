package com.sysu.taosysu;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.sysu.taosysu.ui.fragment.BookListFragment;
import com.sysu.taosysu.ui.fragment.NavigationDrawerFragment;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	private NavigationDrawerFragment mNavigationDrawerFragment;

	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		mNavigationDrawerFragment
				.setUp((DrawerLayout) this.findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, new BookListFragment()).commit();
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
