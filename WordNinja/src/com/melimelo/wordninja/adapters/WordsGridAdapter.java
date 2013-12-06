package com.melimelo.wordninja.adapters;

import java.util.ArrayList;

import com.melimelo.wordninja.model.WordsGrid;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;


public class WordsGridAdapter extends BaseAdapter {

	public enum PointAction{
		AddNewPoint,
		RemoveLast,
		DoNothing
	}

	private Context mContext;
	private WordsGrid mWordsGrid = new WordsGrid(3, 3);
	private ArrayList<Integer> selectedItems;

	
	
	public WordsGridAdapter(Context context) {
		this.mContext = context;
		for(int i =0;i<mWordsGrid.getXCount()*mWordsGrid.getYCount();i++)
			for(int j =0;j<mWordsGrid.getXCount()*mWordsGrid.getYCount();j++)
				areElementsAdjascent(i, j);
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
			textView.setLayoutParams(new GridView.LayoutParams(35, 35));
			textView.setGravity(Gravity.CENTER);
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

	public PointAction checkSelectedItem(int selectedItemIndex) {
		if((selectedItems.size()>0) && 
				(!areElementsAdjascent(selectedItemIndex,selectedItems.get(selectedItems.size()-1))))
		{
			Log.d("", Integer.toString(selectedItemIndex) + "/" + 
					Integer.toString(selectedItems.get(selectedItems.size()-1)) + " not adjascent.");
			return PointAction.DoNothing;
		}
		else if (selectedItems.contains(selectedItemIndex)) {
			if ((selectedItems.get(selectedItems.size() - 1)
					.equals(selectedItemIndex))) {
				Log.d("", "Same point - do nothing");
				return PointAction.DoNothing;
			} else if ((selectedItems.size() > 1)
					&& (selectedItems.get(selectedItems.size() - 2)
							.equals(selectedItemIndex))) {
				Log.d("", "Go back to old point");
				selectedItems.remove(selectedItems.size() - 1);
				return PointAction.RemoveLast;
			} else{//Boucle
				Log.d("", "New point");
				selectedItems.add(selectedItemIndex);
				return PointAction.AddNewPoint;
			}
		}
		else{
			Log.d("", "New point");
			selectedItems.add(selectedItemIndex);
			return PointAction.AddNewPoint;
		}
	}
	
	private boolean areElementsAdjascent(int a, int b) {
		int rowA = a / mWordsGrid.getXCount();
		int columnA = a - (rowA * mWordsGrid.getXCount());
		int rowB = b / mWordsGrid.getXCount();
		int columnB = b - (rowB*mWordsGrid.getXCount());
		Log.d(null,Integer.toString(a)+":"+Integer.toString(rowA)+Integer.toString(columnA)+"/"+
				Integer.toString(b)+":"+Integer.toString(rowB)+Integer.toString(columnB)+"/"+
				(((Math.abs(rowA - rowB)<2) && (Math.abs(columnA - columnB)<2))?"adjascent":"not adjascent"));
		return ((Math.abs(rowA - rowB)<2) && (Math.abs(columnA - columnB)<2));
	}

	public void startNewSelection(){
		if(selectedItems==null)
			selectedItems = new ArrayList<Integer>();
		else selectedItems.clear();
	}

}
