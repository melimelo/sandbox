package com.example.phonoviewer.fragments;

import com.example.phonoviewer.adapters.ReadersPagerAdapter;

import android.support.v4.view.PagerAdapter;

public class ReadersPagerFragment extends PagerFragment {

	@Override
	protected PagerAdapter buildAdapter() {
		return (new ReadersPagerAdapter(getActivity(), getChildFragmentManager()));
	}
}
