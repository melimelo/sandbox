package com.melimelo.wordninja.adapters;

import com.melimelo.wordninja.model.WordsGrid;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class WordsGridAdapter extends BaseAdapter {

	private Context mContext;
	private WordsGrid mWordsGrid = new WordsGrid(3, 3);

	public WordsGridAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public int getCount() {
		Log.d("GetCount",
				Integer.toString(mWordsGrid.getXCount()
						* mWordsGrid.getYCount()));
		return mWordsGrid.getXCount() * mWordsGrid.getYCount();
	}

	@Override
	public Object getItem(int position) {
		return mWordsGrid.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return position / mWordsGrid.getXCount();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView;
		if (convertView == null) {
			textView = new TextView(mContext);
			textView.setLayoutParams(new GridView.LayoutParams(85, 85));
			textView.setPadding(5, 5, 5, 5);
			Log.d("getView",
					"text created:positon" + Integer.toString(position));
			textView.setText(Integer.toString(position));
			textView.setBackgroundColor( mContext.getResources().getColor(android.R.color.black));

		} else {
			textView = (TextView) convertView;
		}
		return textView;
	}

}
