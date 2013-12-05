package com.joker.colormixer;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnSeekBarChangeListener, OnFocusChangeListener {

	RGBColor currentColor = null;
	ImageView currentColorView = null;
	RGBColor backgroundColor = null;
	SeekBar	rSeekBar = null;
	SeekBar gSeekBar = null;
	SeekBar bSeekBar = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        currentColor = new RGBColor();
        currentColorView = (ImageView)findViewById(R.id.currentColor);
        currentColorView.setBackgroundColor(Color.rgb(currentColor.getR(),currentColor.getG(),currentColor.getB()));
        
        rSeekBar = (SeekBar)findViewById(R.id.seekBarR);
        gSeekBar = (SeekBar)findViewById(R.id.seekBarG);
        bSeekBar = (SeekBar)findViewById(R.id.seekBarB);
        
        rSeekBar.setOnSeekBarChangeListener(this);
        gSeekBar.setOnSeekBarChangeListener(this);
        bSeekBar.setOnSeekBarChangeListener(this);
        
        
        rSeekBar.setMax(255);
        gSeekBar.setMax(255);
        bSeekBar.setMax(255);
        
        rSeekBar.setBackgroundColor(android.R.color.white);
        rSeekBar.setOnFocusChangeListener(this);
        backgroundColor = new RGBColor();
        rSeekBar.setProgress(backgroundColor.getR());
        gSeekBar.setProgress(backgroundColor.getG());
        bSeekBar.setProgress(backgroundColor.getB());
        currentColorView.getRootView().setBackgroundColor(Color.rgb(backgroundColor.getR(),backgroundColor.getG(),backgroundColor.getB()));
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if(!fromUser)
				return;
		switch (seekBar.getId()) {
		case R.id.seekBarR:
			backgroundColor.setR(progress);
			break;
		case R.id.seekBarG:
			backgroundColor.setG(progress);
			break;
		case R.id.seekBarB:
			backgroundColor.setB(progress);
			break;
		default:
			break;
		}
		seekBar.getRootView().setBackgroundColor(Color.rgb(backgroundColor.getR(),backgroundColor.getG(),backgroundColor.getB()));
	
	}


	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
	}


	@Override
	public void onStopTrackingTouch(SeekBar arg0) {			
	}


	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(!hasFocus){
			if(backgroundColor.compare(currentColor)<10)
				Toast.makeText(getApplicationContext(), "Bravo!", 2000).show();
			else Toast.makeText(getApplicationContext(), "Keep trying!" + Integer.toString(backgroundColor.compare(currentColor)), 1000).show(); 
		}
	}
    
}
