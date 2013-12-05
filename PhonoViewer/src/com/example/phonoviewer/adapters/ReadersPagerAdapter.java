package com.example.phonoviewer.adapters;

import com.example.phonoviewer.fragments.ReaderItemFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ReadersPagerAdapter extends FragmentPagerAdapter {
  Context ctxt=null;

  public ReadersPagerAdapter(Context ctxt, FragmentManager mgr) {
    super(mgr);
    this.ctxt=ctxt;
  }

  @Override
  public int getCount() {
    return(10);
  }

  @Override
  public Fragment getItem(int position) {
    return(ReaderItemFragment.newInstance(position));
  }

  @Override
  public String getPageTitle(int position) {
    return(ReaderItemFragment.getTitle(ctxt, position));
  }
}