package com.melimelo.wordninja.adapters;

import java.util.ArrayList;

import com.melimelo.wordninja.model.WordsGrid;

import android.content.Context;
import android.graphics.Color;
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

	private int nbRows = 6;
	private int nbColumns = 7;
	private Context mContext;
	private WordsGrid mWordsGrid;
	
	private ArrayList<Integer> selectedItems;
	public ArrayList<Integer> getSelectedItems(){return this.selectedItems;}

	private boolean isDiagonaleAllowed(){
		return true;
	}

	private boolean isNonUniformAllowed(){
		return false;
	}
	
	private boolean isMultiPointSelectionAllowed(){
		return true;
	}
	
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
			textView.setGravity(Gravity.CENTER);
			
			textView.setText(Integer.toString(position));
			textView.setBackgroundColor( mContext.getResources().getColor(android.R.color.tertiary_text_light));
			if (itemHeight > 0)//itemHeight = 0 if grid not initialized yet
				while (textView.getLineHeight() > itemHeight) {

					textView.setTextSize((float) (textView.getTextSize() * 0.6));
				}
		} else {
			textView = (TextView) convertView;
			textView.setText(Integer.toString(position)+"N");
			textView.setLayoutParams(new GridView.LayoutParams(itemWidth, itemWidth));
			if (itemHeight > 0)
				while (textView.getLineHeight() > itemHeight) {

					textView.setTextSize((float) (textView.getTextSize() * 0.6));
				}
		}
		return textView;
	}

	
	public PointAction checkSelectedItem(int selectedItemIndex) {
		if((selectedItems.size()>0) && (!isValid(selectedItemIndex)))
		{
			if(checkMultiPointValidity(selectedItemIndex)){
				return PointAction.AddNewPoint;
			}
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
	
	public boolean checkMultiPointValidity(int selectedItemIndex){
		boolean vReturn = true;
		
		vReturn &= ((selectedItems.size()>0) && (selectedItemIndex != selectedItems.get(selectedItems.size()-1)));
		if(vReturn == false){
			Log.d("checkMultiPointValidity","not different");
		}
		vReturn &= (isMultiPointSelectionAllowed() &&
				areElementsInSameLine(selectedItems.get(selectedItems.size()-1),selectedItemIndex));
		if(vReturn == false){
			Log.d("checkMultiPointValidity","isMultiPointSelectionAllowed");
		}
		if((!isNonUniformAllowed()) && (selectedItems.size()>1))
			vReturn &= areElementsUniform(	selectedItems.get(selectedItems.size()-2),
											selectedItems.get(selectedItems.size()-1),
											selectedItemIndex);

		if(vReturn == false){
			Log.d("checkMultiPointValidity","isNonUniformAllowed");
		}
		if(vReturn == true){
			Log.d("checkMultiPointValidity","addMultiPoints");
		}
		if(vReturn)
			addMultiPoints(selectedItems.get(selectedItems.size()-1),selectedItemIndex);
		return vReturn;
	}
		
		
	private void addMultiPoints(int a, int b) {
		
		int columnA = a / mWordsGrid.getXCount();
		int rowA = a - (columnA * mWordsGrid.getXCount());
		int columnB = b / mWordsGrid.getXCount();
		int rowB = b - (columnB*mWordsGrid.getXCount());
		
		int rowC = -1;
		int columnC = -1;
		
		Log.d("addMultiPoints", Integer.toString(a) + "/" +Integer.toString(b));
		
		if (rowA == rowB) {
			rowC = rowA;
			for (int i = 1; i < Math.abs(columnA - columnB); i++) {
				columnC = Math.min(columnA,columnB) + i;
				selectedItems.add(rowC + (mWordsGrid.getXCount() * columnC));
				Log.d("addMultiPoints1", Integer.toString(selectedItems
						.get(selectedItems.size() - 1)));
			}
		} else if (columnA == columnB) {
			columnC = columnA;
			for (int i = 1; i < Math.abs(rowA - rowB); i++) {
				rowC = Math.min(rowA, rowB) + i;
				selectedItems.add(rowC + (mWordsGrid.getXCount() * columnC));
				Log.d("addMultiPoints2", Integer.toString(selectedItems
						.get(selectedItems.size() - 1)));
			}
		} else if (Math.abs(rowA - rowB) == Math.abs(columnA - columnB)) {
			for (int i = 1; i < Math.abs(columnA - columnB); i++) 
				{
					rowC = Math.min(rowA, rowB)+i;
					columnC = (rowA < rowB ? columnA : columnB);
					columnC += ((rowA < rowB) == (columnA < columnB)) ? i : -i;
					selectedItems.add(rowC + (mWordsGrid.getXCount() * columnC));
					
					Log.d("addMultiPoints3", Integer.toString(selectedItems
							.get(selectedItems.size() - 1)));
				}
		}
	}



	
	
	private boolean isValid(int selectedItemIndex){
		boolean vReturn = ((selectedItems.size()>0) && 
				(areElementsAdjascent(selectedItemIndex,selectedItems.get(selectedItems.size()-1))));//Must be adjascent
		
		if((!isNonUniformAllowed()) && (selectedItems.size()>1))
			vReturn &= areElementsUniform(	selectedItems.get(selectedItems.size()-2),
											selectedItems.get(selectedItems.size()-1),
											selectedItemIndex);
		return vReturn;
	}
	
	private boolean areElementsInSameLine(int a, int b){
		
		int columnA = a / mWordsGrid.getXCount();
		int rowA = a - (columnA * mWordsGrid.getXCount());
		int columnB = b / mWordsGrid.getXCount();
		int rowB = b - (columnB*mWordsGrid.getXCount());
		
		boolean vReturn = false;
		
		Log.d("areElementsInSameLine",Integer.toString(a)+"/"+Integer.toString(b)+
				Integer.toString(rowA)+"/"+Integer.toString(rowB)+
				Integer.toString(columnA)+"/"+Integer.toString(columnB));
		if(isDiagonaleAllowed()){
			vReturn = (Math.abs(rowA - rowB) == Math.abs(columnA - columnB));
		}
		vReturn |= ((rowA == rowB)||(columnA==columnB));
		
		return vReturn;
		
	}
	private boolean areElementsUniform(int a, int b, int c){
		int columnA = a / mWordsGrid.getXCount();
		int rowA = a - (columnA * mWordsGrid.getXCount());
		int columnB = b / mWordsGrid.getXCount();
		int rowB = b - (columnB*mWordsGrid.getXCount());
		int columnC = c / mWordsGrid.getXCount();
		int rowC = c - (columnC*mWordsGrid.getXCount());
		return (((rowB - rowA) == (rowC - rowB))&&((columnB - columnA) == (columnC - columnB)));
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
