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
	private WordsGrid mWordsGrid = new WordsGrid(6, 7);
	private ArrayList<Integer> selectedItems;
	public ArrayList<Integer> getSelectedItems(){return this.selectedItems;}

	
	
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

	public int getNbColumns(){
		return mWordsGrid.getXCount();
	}
	
	public int getNbRows(){
		return mWordsGrid.getYCount();
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
			textView.setText(Integer.toString(position)+"N");
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
	
	private boolean isDiagonaleAllowed(){
		return false;
	}

	private boolean areElementsAdjascent(int a, int b) {
		int rowA = a / mWordsGrid.getYCount();
		int columnA = a - (rowA * mWordsGrid.getYCount());
		int rowB = b / mWordsGrid.getYCount();
		int columnB = b - (rowB*mWordsGrid.getYCount());

		if(isDiagonaleAllowed()){
			return ((Math.abs(rowA - rowB)<2) && (Math.abs(columnA - columnB)<2));
		}
		else{
			return (((Math.abs(rowA - rowB)) + (Math.abs(columnA - columnB))) < 2);
		}
	}

	public void startNewSelection(){
		if(selectedItems==null)
			selectedItems = new ArrayList<Integer>();
		else selectedItems.clear();
	}

}
