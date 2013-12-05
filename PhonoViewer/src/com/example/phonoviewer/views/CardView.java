package com.example.phonoviewer.views;

import com.example.phonoviewer.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CardView extends LinearLayout {

	public CardView(Context context) {
		this(context, null);
	}

	public CardView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		// Setup background
		/*setBackgroundResource(R.drawable.bg_card_normal_new);
		setForeground(getResources().getDrawable(R.drawable.card_selector));*/

		// Setup
		final int padding = getResources().getDimensionPixelSize(
				R.dimen.space_normal);
		//setPadding(padding, padding, padding, padding);
		
		// Margin
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		/*setLayoutParams(params);**/
		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
				.getDisplayMetrics());
		
		
		// Text
		TextView v = new TextView(context);
		params.setMargins(margin, margin, margin, margin);
		v.setLayoutParams(params);
		v.setTextSize(22);
		v.setPadding(padding, 6*padding, padding, padding);
		v.setMinHeight(50);
		
		//v.setHeight(62);
		//v.setLa
		//params.setMargins(margin, margin, margin, margin);
		//v.setLayoutParams(params);
		v.setGravity(Gravity.CENTER);
		v.setBackgroundResource(R.drawable.background_card);
		v.setText("CARD " + ("cardinfo\ntest" + 1));
		
		TextView v2 = new TextView(context);
		v2.setText("text2");
		v2.setVisibility(View.VISIBLE);

		v.setVisibility(View.VISIBLE);
		addView(v);
		addView(v2);
		
		Log.d("Frame",Integer.toString(getHeight()));
		Log.d("Frame",Integer.toString(getMinimumHeight()));
		Log.d("Text",Integer.toString(v.getHeight()));
		Log.d("Text",Integer.toString(v.getMinimumHeight()));
		
		//setMinimumHeight(R.dimen.space_normal*3);
	}

}
