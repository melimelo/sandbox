package com.melimelo.wordninja.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.ArrayList;
import java.util.List;

public class SimpleDrawView extends View implements OnTouchListener {

    private static final float STROKE_WIDTH = 5f;

    List<Point> points = new ArrayList<Point>();
    Paint paint = new Paint();

    public SimpleDrawView(Context context, AttributeSet attributeSet) {
       
    	super(context, attributeSet);
    	
        setFocusable(true);
        setFocusableInTouchMode(true);

        this.setOnTouchListener(this);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setColor(Color.BLACK);
        
        points.add(new Point()); points.add(new Point());
    }

    @Override
    public void onDraw(Canvas canvas) {
    	Path path = new Path();
        boolean first = true;
        for (Point point : points) {
            if (first) {
                first = false;
                path.moveTo(point.x, point.y);
            } else {
                path.lineTo(point.x, point.y);
            }
        }

        canvas.drawPath(path, paint);
    }

	public boolean onTouch(View view, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			points.get(0).x = event.getX();
			points.get(0).y = event.getY();
			if(points.size()>1)points.remove(1);
			invalidate();
            return true;
		case MotionEvent.ACTION_MOVE:
			if(points.size()<2)points.add(new Point());
			points.get(1).x = event.getX();
			points.get(1).y = event.getY();
			invalidate();
            return true;
		case MotionEvent.ACTION_UP:
		default:
			return super.onTouchEvent(event);
		}
	}

    public void clear() {
        points.clear();
    }

    class Point {
        float x, y;
        float dx, dy;

        @Override
        public String toString() {
            return x + ", " + y;
        }
    }
}