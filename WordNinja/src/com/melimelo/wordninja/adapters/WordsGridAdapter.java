package com.melimelo.wordninja.adapters;

import java.util.ArrayList;

import com.melimelo.wordninja.R;
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

	public enum PointAction {
		AddNewPoint, RemoveLast, DoNothing
	}

	private int nbRows = -1;
	private int nbColumns = -1;
	private double spacingCoeff = 0;
	private Context mContext;
	private WordsGrid mWordsGrid = null;

	private ArrayList<Integer> selectedItems;

	private boolean isDiagonaleAllowed() {
		return true;
	}

	private boolean isNonUniformAllowed() {
		return true;
	}

	private boolean isMultiPointSelectionAllowed() {
		return true;
	}

	private boolean isReuseAllowed(){
		return false;
	}
	
	private boolean isGoBackAllowed(){
		return false;
	}
	
	public WordsGridAdapter(Context context) {
		this.mContext = context;
	}
	
	public WordsGridAdapter(Context context, WordsGrid wordsGrid) {
		this(context);
		setWordsGrid(wordsGrid);
	}

	@Override
	public int getCount() {
		return mWordsGrid.getXCount() * mWordsGrid.getYCount();
	}

	public int getNbColumns() {
		return mWordsGrid.getXCount();
	}

	public int getNbRows() {
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
	
	public ArrayList<Integer> getSelectedItems() {
		return this.selectedItems;
	}

	
	public void initLayout(ViewGroup parent, int i, int j) {
		int itemWidth = (int) (((GridView) parent).getWidth() / (i
				* (1 + spacingCoeff)));
		int itemHeight = itemWidth; // ((GridView) parent).getHeight()/(j*2+1);

		((GridView) parent).setNumColumns(i);
		((GridView) parent).setColumnWidth(itemWidth);
		((GridView) parent)
				.setVerticalSpacing((int) (itemWidth * spacingCoeff));
		((GridView) parent)
				.setHorizontalSpacing((int) (itemHeight * spacingCoeff));

		parent.setPadding((int) (itemWidth * spacingCoeff),
				(int) (itemHeight * spacingCoeff),
				(int) (itemWidth * spacingCoeff),
				(int) (itemHeight * spacingCoeff));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		initLayout(parent, nbColumns, nbRows);
		int itemWidth = (int) (parent.getWidth() / (nbColumns
				* (1 + spacingCoeff)));
		int itemHeight = itemWidth;
		if (itemWidth == 0) {
			itemWidth = (int) (((GridView) parent).getColumnWidth() / (nbColumns
					* (1 + spacingCoeff)));
		}
		TextView textView;
		if (convertView == null) {
			textView = new TextView(mContext);
			textView.setLayoutParams(new GridView.LayoutParams(itemWidth,
					itemHeight));
			textView.setGravity(Gravity.CENTER);
			textView.setText(mWordsGrid.getItem(position).toString());
			textView.setBackgroundColor(((position % 3 > 0) ? mContext.getResources().getColor(R.color.light_green) : 
											mContext.getResources().getColor(R.color.dark_green)));
			textView.setTextColor(mContext.getResources().getColor(android.R.color.white));
			if (itemHeight > 0)// itemHeight = 0 if grid not initialized yet
				while (textView.getLineHeight() > itemHeight) {

					textView.setTextSize((float) (textView.getTextSize() * 0.6));
				}
		} else {
			textView = (TextView) convertView;
			textView.setText(mWordsGrid.getItem(position).toString());
			textView.setLayoutParams(new GridView.LayoutParams(itemWidth,
					itemWidth));
			if (itemHeight > 0)
				while (textView.getLineHeight() > itemHeight) {

					textView.setTextSize((float) (textView.getTextSize() * 0.6));
				}
		}
		return textView;
	}

	public PointAction checkSelectedItem(int selectedItemIndex) {
		if((!isReuseAllowed())&&(selectedItems.contains(selectedItemIndex)))
			return PointAction.DoNothing;
		if ((selectedItems.size() > 0) && (!isValid(selectedItemIndex))) {
			if (checkMultiPointValidity(selectedItemIndex)) {
				notifyDataSetChanged();
				return PointAction.AddNewPoint;
			}
			return PointAction.DoNothing;
		} else if (selectedItems.contains(selectedItemIndex)) {
			if ((selectedItems.get(selectedItems.size() - 1)
					.equals(selectedItemIndex))) {
				return PointAction.DoNothing;
			} else if ((selectedItems.size() > 1)
					&& (selectedItems.get(selectedItems.size() - 2)
							.equals(selectedItemIndex))) {
				if (isGoBackAllowed()) {
					selectedItems.remove(selectedItems.size() - 1);
					notifyDataSetChanged();
					return PointAction.RemoveLast;
				} else {
					return PointAction.DoNothing;
				}
			} else {// Boucle
				if (isGoBackAllowed()) {
					selectedItems.add(selectedItemIndex);
					notifyDataSetChanged();
					return PointAction.AddNewPoint;
				}
				return PointAction.DoNothing;
			}
		} else {
			selectedItems.add(selectedItemIndex);
			notifyDataSetChanged();
			return PointAction.AddNewPoint;
		}
	}

	public boolean checkMultiPointValidity(int selectedItemIndex) {

		boolean vReturn = true;
		vReturn &= ((selectedItems.size() > 0) && (selectedItemIndex != selectedItems
				.get(selectedItems.size() - 1)));

		vReturn &= (isMultiPointSelectionAllowed() && areElementsInSameLine(
				selectedItems.get(selectedItems.size() - 1), selectedItemIndex));
		if ((!isNonUniformAllowed()) && (selectedItems.size() > 1)) {

			vReturn &= areElementsInSameLine(
					selectedItems.get(selectedItems.size() - 2),
					selectedItems.get(selectedItems.size() - 1),
					selectedItemIndex);
		}
		if (vReturn)
			addMultiPoints(selectedItems.get(selectedItems.size() - 1),
					selectedItemIndex);
		return vReturn;
	}

	private void addMultiPoints(int a, int b) {

		int columnA = a / mWordsGrid.getXCount();
		int rowA = a - (columnA * mWordsGrid.getXCount());
		int columnB = b / mWordsGrid.getXCount();
		int rowB = b - (columnB * mWordsGrid.getXCount());
		int rowC = -1;
		int columnC = -1;
		int c = -1;

		boolean allpreviouselementsexist = true;

		for (int i = 1; i < Math.max(Math.abs(columnA - columnB),
				Math.abs(rowA - rowB)); i++) {
			rowC = ((rowA == rowB) ? rowA : (rowA + ((rowB > rowA) ? i : -i)));
			columnC = ((columnA == columnB) ? columnA
					: (columnA + ((columnB > columnA) ? i : -i)));
			c = rowC + (mWordsGrid.getXCount() * columnC);

			int duplicatedStartIndex = (i == 1) ? selectedItems.size() - 2
					: selectedItems.size() - 1;
			if (allpreviouselementsexist && (duplicatedStartIndex > -1)
					&& (selectedItems.get(duplicatedStartIndex) == c)) {
				if(!isGoBackAllowed())
					return;
				for (int j = selectedItems.size() - 1; j > duplicatedStartIndex - 1; j--)
					selectedItems.remove(j);
			} else {
				if((!isReuseAllowed())&&(selectedItems.contains(c)))
					return;
				allpreviouselementsexist = false;
				selectedItems.add(c);
			}
		}
	}

	private boolean isValid(int selectedItemIndex) {
		boolean vReturn = ((selectedItems.size() > 0) && (areElementsAdjascent(
				selectedItemIndex, selectedItems.get(selectedItems.size() - 1))));// Must
																					// be
																					// adjascent

		if ((!isNonUniformAllowed()) && (selectedItems.size() > 1))
			vReturn &= areElementsInSameLine(
					selectedItems.get(selectedItems.size() - 2),
					selectedItems.get(selectedItems.size() - 1),
					selectedItemIndex);
		return vReturn;
	}

	private boolean areElementsInSameLine(int a, int b) {

		int columnA = a / mWordsGrid.getXCount();
		int rowA = a - (columnA * mWordsGrid.getXCount());
		int columnB = b / mWordsGrid.getXCount();
		int rowB = b - (columnB * mWordsGrid.getXCount());

		boolean vReturn = false;

		if (isDiagonaleAllowed()) {
			vReturn = (Math.abs(rowA - rowB) == Math.abs(columnA - columnB));
		}
		vReturn |= ((rowA == rowB) || (columnA == columnB));

		return vReturn;
	}

	private boolean areElementsInSameLine(int a, int b, int c) {
		int columnA = a / mWordsGrid.getXCount();
		int rowA = a - (columnA * mWordsGrid.getXCount());
		int columnB = b / mWordsGrid.getXCount();
		int rowB = b - (columnB * mWordsGrid.getXCount());
		int columnC = c / mWordsGrid.getXCount();
		int rowC = c - (columnC * mWordsGrid.getXCount());
		return (((rowB - rowA) * (columnC - columnB)) == ((rowC - rowB) * (columnB - columnA)));
	}

	private boolean areElementsAdjascent(int a, int b) {
		int columnA = a / mWordsGrid.getXCount();
		int rowA = a - (columnA * mWordsGrid.getXCount());
		int columnB = b / mWordsGrid.getXCount();
		int rowB = b - (columnB * mWordsGrid.getXCount());

		if (isDiagonaleAllowed()) {
			return ((Math.abs(rowA - rowB) < 2) && (Math.abs(columnA - columnB) < 2));
		} else {
			return (((Math.abs(rowA - rowB)) + (Math.abs(columnA - columnB))) < 2);
		}
	}

	public void startNewSelection() {
		if (selectedItems == null)
			selectedItems = new ArrayList<Integer>();
		else
			selectedItems.clear();
	}

	public void setWordsGrid(WordsGrid wordsGrid) {
		this.mWordsGrid=wordsGrid;
		this.nbRows = wordsGrid.getYCount();
		this.nbColumns = wordsGrid.getXCount();
	}

	public WordsGrid getWordsGrid() {
		return this.mWordsGrid;
	}

	public CharSequence getSelectedText() {
		String s= new String();
		for(int i=0; i<selectedItems.size();i++)
			s+=this.mWordsGrid.getItem(selectedItems.get(i));
		return s;
	}
}
