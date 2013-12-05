package com.example.phonoviewer.fragments;

import com.example.phonoviewer.adapters.ChaptersPagerAdapter;

import android.support.v4.view.PagerAdapter;

public class ChaptersPagerFragment extends PagerFragment {

	@Override
	protected PagerAdapter buildAdapter() {
		return (new ChaptersPagerAdapter(getActivity(), getChildFragmentManager()));
	}

}
