package com.melimelo.wordninja;

import com.melimelo.wordninja.adapters.WordsGridAdapter;
import com.melimelo.wordninja.views.WordsGridView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private WordsGridView wordsGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.wordsGridView = (WordsGridView) findViewById(R.id.wordsGridview);
		//wordsGridView.setNumColumns(6);
		//wordsGridView.setNumColumns(7);
		//wordsGridView.initLayout(6,7);
		wordsGridView.setAdapter(new WordsGridAdapter(this));

		Button clearButton = (Button) findViewById(R.id.clearbutton);
		clearButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		wordsGridView.clear();
	}

}