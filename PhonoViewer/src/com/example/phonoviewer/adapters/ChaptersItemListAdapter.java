package com.example.phonoviewer.adapters;

import java.util.ArrayList;


import com.example.phonoviewer.R;
import com.example.phonoviewer.fragments.ChapterCardFragment;
import com.example.phonoviewer.views.CardView;
import com.example.phonoviewer.views.ListItemView;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressWarnings("rawtypes")
public class ChaptersItemListAdapter extends ArrayAdapter {
	Context ctxt = null;
	ArrayList<String> content = null;

	public ChaptersItemListAdapter(Context ctxt, FragmentManager mgr) {
		//ABEsuper(ctxt, R.layout.simple_list_item_1);
		super(ctxt, R.layout.list_view);
		init(content);
		this.ctxt = ctxt;
	}

	private void init(ArrayList<String> content) {
		this.content = new ArrayList<String>();
		for(int i =0; i<10; i++)
			this.content.add("chapter"+i);
	}

	@Override
	public int getCount() {
		return content.size();
	}

	@Override
	public Fragment getItem(int position) {
		return (ChapterCardFragment.newInstance(position));
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//return new CardView(ctxt);
		//return new ListItemView(ctxt);
		 // 1. Create inflater 
        LayoutInflater inflater = (LayoutInflater) this.ctxt
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.label);
        TextView valueView = (TextView) rowView.findViewById(R.id.description);

        // 4. Set the text for textView 
        labelView.setText(content.get(position)+ " title");//.getTitle());
        valueView.setText(content.get(position)+" desc");//.getDescription());

        // 5. retrn rowView
        return rowView;
	}
	
}