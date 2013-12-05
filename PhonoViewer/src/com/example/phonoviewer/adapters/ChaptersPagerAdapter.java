package com.example.phonoviewer.adapters;

import com.example.phonoviewer.fragments.ChaptersItemListFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ChaptersPagerAdapter extends FragmentPagerAdapter {
  Context ctxt=null;

  public ChaptersPagerAdapter(Context ctxt, FragmentManager mgr) {
    super(mgr);
    this.ctxt=ctxt;
  }

  @Override
  public int getCount() {
    return(2);
  }

  @Override
  public Fragment getItem(int position) {
    return(ChaptersItemListFragment.newInstance(position,ChaptersItemListFragment.Type.values()[position]));
  }

  @Override
  public String getPageTitle(int position) {
    return(ChaptersItemListFragment.getTitle(ctxt, position));
  }
}