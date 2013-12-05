package com.example.phonoviewer.fragments;

import com.example.phonoviewer.adapters.ChaptersItemListAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class ChaptersItemListFragment extends ListFragment {
	private static final String KEY_POSITION = "position";
	private static final String KEY_TYPE = "type";

	public enum Type {
		All, Reading
	};

	public static ChaptersItemListFragment newInstance(int position, Type type) {
		ChaptersItemListFragment fragment = new ChaptersItemListFragment();
		Bundle args = new Bundle();
		args.putInt(KEY_POSITION, position);
		args.putInt(KEY_TYPE, type.ordinal());
		fragment.setArguments(args);
		return (fragment);
	}

	public static String getTitle(Context ctxt, int position) {
		return Type.values()[position].name();
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * View result=inflater.inflate(R.layout.editor, container, false);
		 * EditText editor=(EditText)result.findViewById(R.id.editor); int
		 * position=getArguments().getInt(KEY_POSITION, -1);
		 * editor.setHint(getTitle(getActivity(), position)); return(result);
		 */
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// Setup
		ListAdapter adapter = new ChaptersItemListAdapter(getActivity(),getActivity().getSupportFragmentManager());
        setListAdapter(adapter);
        //list_V.setOnItemClickListener(this);
        
	}

}