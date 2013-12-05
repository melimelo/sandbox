package com.example.phonoviewer.views;


import com.example.phonoviewer.R;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ListItemView extends FrameLayout {

	public ListItemView(Context context) {
		super(context);
		android.widget.AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, android.R.attr.listPreferredItemHeight);
		setLayoutParams(params);
		
		
		LinearLayout llayout = new LinearLayout(context);
		
		LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		final int marginTopBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		final int marginLeftRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());
		params2.setMargins(marginLeftRight, marginTopBottom, marginLeftRight, marginTopBottom);
		llayout.setBackgroundResource(R.drawable.card_background);
		llayout.setLayoutParams(params2);
		
		TextView tv = new TextView(context);
		params2 = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(params2);
		tv.setTextColor(getResources().getColor(android.R.color.primary_text_light));
		tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		tv.setText("test");
		llayout.addView(tv);
		
			
	}

}
