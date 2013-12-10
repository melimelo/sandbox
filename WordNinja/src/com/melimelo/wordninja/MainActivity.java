package com.melimelo.wordninja;

import com.melimelo.wordninja.adapters.WordsGridAdapter;
import com.melimelo.wordninja.model.WordsGrid;
import com.melimelo.wordninja.observers.GridDataSetObserver;
import com.melimelo.wordninja.views.WordsGridView;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private WordsGridView wordsGridView;
	private DataSetObserver gridDataSetObserver;
	
	private int Rows = 6;
	private int Columns = 7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.wordsGridView = (WordsGridView) findViewById(R.id.wordsGridview);
		wordsGridView.setAdapter(new WordsGridAdapter(this, new WordsGrid(Columns, Rows)));
		
		wordsGridView.setGravity(Gravity.CENTER_VERTICAL);
		final TextView textView = (TextView) findViewById(R.id.wordTextView);
		textView.setText("abc");
		
		gridDataSetObserver = new DataSetObserver(){
			@Override
			public void onChanged(){
				textView.setText(((WordsGridAdapter)wordsGridView.getAdapter()).getSelectedText());
			}
		};
		((WordsGridAdapter)wordsGridView.getAdapter()).registerDataSetObserver(gridDataSetObserver);;
		
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout_root);
		Log.d("RelativeLayout",Integer.toString(layout.getBottom())+"/"+Integer.toString(layout.getTop())+"/"+Integer.toString(layout.getHeight())+"/");
		textView.setMinHeight(100);
		textView.setTextSize(40);
	}

	@Override
	public void onClick(View v) {
		wordsGridView.clear();
	}

}