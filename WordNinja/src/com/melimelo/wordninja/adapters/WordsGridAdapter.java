package com.melimelo.wordninja.adapters;

import java.util.ArrayList;

import com.melimelo.wordninja.model.WordsGrid;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;
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

	private int nbRows = 6;
	private int nbColumns = 7;
	private Context mContext;
	private WordsGrid mWordsGrid;
	
	private ArrayList<Integer> selectedItems;
	public ArrayList<Integer> getSelectedItems(){return this.selectedItems;}

	
	
	public WordsGridAdapter(Context context) {
		this.mContext = context;
		mWordsGrid = new WordsGrid(nbColumns, nbRows);
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

	public void initLayout(ViewGroup parent, int i, int j) {
		
		int itemWidth = ((GridView) parent).getWidth()/(i*2+1);
		int itemHeight = itemWidth; //((GridView) parent).getHeight()/(j*2+1);
			
		((GridView) parent).setNumColumns(i);
		((GridView) parent).setColumnWidth(itemWidth);
		((GridView) parent).setVerticalSpacing(itemWidth);
		((GridView) parent).setHorizontalSpacing(itemHeight);
		
		
		parent.setPadding(itemWidth, itemHeight, itemWidth, itemHeight);
		
		/*Log.d("InitLayout",Integer.toString(((GridView) parent).getNumColumns())+"/"+
				Integer.toString(((GridView) parent).getColumnWidth())+"/"+
				Integer.toString(((GridView) parent).getVerticalSpacing())+"/"+
				Integer.toString(((GridView) parent).getHorizontalSpacing())
				);*/
		
		
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		initLayout(parent,nbColumns,nbRows);
		int itemWidth = parent.getWidth() / (nbColumns*2+1);
		int itemHeight = itemWidth;
		if(itemWidth == 0){itemWidth = ((GridView) parent).getColumnWidth()/(nbColumns*2+1);}
		TextView textView;
		if (convertView == null) {
			textView = new TextView(mContext);
			textView.setLayoutParams(new GridView.LayoutParams(itemWidth, itemHeight));
			Log.d("getView", Integer.toString(position)+"/"+Integer.toString(itemWidth));
			textView.setGravity(Gravity.CENTER);
			
			//textView.setPadding(5, 5, 5, 5);
			/*Log.d("getView",
					"text created:positon" + Integer.toString(position));*/
			textView.setText(Integer.toString(position));
			textView.setBackgroundColor( mContext.getResources().getColor(android.R.color.tertiary_text_light));
			Log.d("LineHeight1",Integer.toString(position)+"/"+
					Float.toString(textView.getTextSize())+"/"+
					Integer.toString(textView.getLineHeight())+"/"+
					Integer.toString(textView.getMeasuredHeight())+"/"+
					Integer.toString(itemHeight));
			
			//if(textView.getTextSize()<50)
			if (itemHeight > 0)
				while (textView.getLineHeight() > itemHeight) {

					textView.setTextSize((float) (textView.getTextSize() * 0.6));
				}
			Log.d("LineHeight",Integer.toString(position)+"/"+
					Float.toString(textView.getTextSize())+"/"+
					Integer.toString(textView.getLineHeight())+"/"+
					Integer.toString(itemHeight));
			
		} else {
			textView = (TextView) convertView;
			textView.setText(Integer.toString(position)+"N");
			textView.setLayoutParams(new GridView.LayoutParams(itemWidth, itemWidth));
			Log.d("LineHeight2",Integer.toString(position)+"/"+
					Float.toString(textView.getTextSize())+"/"+
					Integer.toString(textView.getLineHeight())+"/"+
					Integer.toString(itemHeight));


			if (itemHeight > 0)
				while (textView.getLineHeight() > itemHeight) {

					textView.setTextSize((float) (textView.getTextSize() * 0.6));
				}
			//textView.setTextSize(textView.getTextSize()/2);
			textView.setGravity(Gravity.LEFT|Gravity.TOP);
			textView.setTextColor(Color.BLACK);
			Log.d("text",textView.getText().toString());
			Log.d("text",Float.toString(textView.getTextSize()));
		
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
		return true;
	}

	private boolean areElementsAdjascent(int a, int b) {
		int columnA = a / mWordsGrid.getXCount();
		int rowA = a - (columnA * mWordsGrid.getXCount());
		int columnB = b / mWordsGrid.getXCount();
		int rowB = b - (columnB*mWordsGrid.getXCount());
		
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
