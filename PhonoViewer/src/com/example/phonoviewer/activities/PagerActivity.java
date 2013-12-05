package com.example.phonoviewer.activities;

import com.example.phonoviewer.R;
import com.example.phonoviewer.R.menu;
import com.example.phonoviewer.fragments.ChaptersPagerFragment;
import com.example.phonoviewer.fragments.ReadersPagerFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class PagerActivity extends FragmentActivity {

	Fragment readersPagerFragment = null;
	Fragment chaptersPagerFragment = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		chaptersPagerFragment = new ChaptersPagerFragment();
		if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
			getSupportFragmentManager().beginTransaction()
					.add(android.R.id.content, chaptersPagerFragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Fragment newFragment = null;
		Log.d("onOptionsItemSelected",this.toString());
		if (item.getTitle().toString().equals("Readers")) {
			if (readersPagerFragment == null) {
				readersPagerFragment = Fragment.instantiate(this,
						ReadersPagerFragment.class.getName());
			}
			newFragment = readersPagerFragment;
		} else if (item.getTitle().toString().equals("Chapters")) {
			newFragment = chaptersPagerFragment;
		}
		if(newFragment!=null)
		{

			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			transaction.replace(android.R.id.content, newFragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}
		return true;
	}
}