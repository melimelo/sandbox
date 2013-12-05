package com.melimelo.wordninja.views;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.melimelo.wordninja.views.SimpleDrawView.Point;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.view.View.OnTouchListener;

public class WordsGridView extends GridView implements OnTouchListener {

	private static final float STROKE_WIDTH = 5f;

	List<Point> points = new ArrayList<Point>();
	Point currentPoint = null;
	Paint paint = new Paint();
	ArrayList<Path> pathsList;
	Path currentPath = null;
	Path composedPath = null;

	public WordsGridView(Context context, AttributeSet attributeSet) {

		super(context, attributeSet);

		setFocusable(true);
		setFocusableInTouchMode(true);

		this.setOnTouchListener(this);

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(STROKE_WIDTH);
		paint.setColor(Color.BLACK);

		currentPath = new Path();
		composedPath = new Path();
		composedPath.addPath(currentPath);
	}

	@Override
	public void onDraw(Canvas canvas) {
		/*
		 * for(int i=0; i<getChildCount();i++) Log.d(null, Integer.toString(i) +
		 * "-X:" + Float.toString(getChildAt(i).getX())
		 * +"-Y:"+Float.toString(getChildAt(i).getY())
		 * +"-B:"+Float.toString(getChildAt(i).getBottom())
		 * +"-T:"+Float.toString(getChildAt(i).getTop())
		 * +"-L:"+Float.toString(getChildAt(i).getLeft())
		 * +"-R:"+Float.toString(getChildAt(i).getRight()));
		 */

		composedPath.rewind();
		currentPath.rewind();
		boolean first = true;
		for (Point point : points) {
			if (first) {
				first = false;
				currentPath.moveTo(point.x, point.y);
			} else {
				currentPath.lineTo(point.x, point.y);
			}
		}

		if(currentPoint!=null)
			currentPath.lineTo(currentPoint.x, currentPoint.y);
		
		composedPath.addPath(currentPath);
		canvas.drawPath(composedPath, paint);

		// canvas.clipPath(path, paint);
	}

	public boolean onTouch(View view, MotionEvent event) {
		View selected = null;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			points.clear();
			Log.d("onTouch",
					Float.toString(event.getX()) + "/"
							+ Float.toString(event.getY()));

			selected = getViewByCoords(event.getX(), event.getY());
			if (selected != null) {
				int x = selected.getRight() - (selected.getWidth() / 2);
				int y = selected.getBottom() - (selected.getHeight() / 2);
				currentPoint = new Point(x, y);
				points.add(new Point(x,y));
				Log.d("","New point - down");
				
			}
			invalidate();
			return true;
		case MotionEvent.ACTION_MOVE:
			currentPoint.setX(event.getX()); currentPoint.setY(event.getY()); 
			selected = getViewByCoords(event.getX(), event.getY());
			if (selected != null) {//Valid element to rattach
				int x = selected.getRight() - (selected.getWidth() / 2);
				int y = selected.getBottom() - (selected.getHeight() / 2);
				currentPoint.setX(x);currentPoint.setY(y);
				if (points.contains(currentPoint)) {
					String s="";
					for(Point p : points)
						s+=p.toString()+"/";
					s+=currentPoint.toString();
					Log.d("Coords",s);
					if((points.get(points.size() - 1).equals(currentPoint))){
						Log.d("","Same point - do nothing");
					}
					else if((points.size()>2) && (points.get(points.size() - 2).equals(currentPoint))){
						Log.d("","Go back to old point");
						points.remove(points.size() - 1);
					}
					else {
						Log.d("","New point");
						points.add(new Point(x,y));
						/*onTouch(this, MotionEvent.obtain(
								SystemClock.uptimeMillis(),
								SystemClock.uptimeMillis() + 100,
								MotionEvent.ACTION_UP, 0.0f, 0.0f, 0));*/
					}
				}
				else{
					Log.d("","New point - else");
					points.add(new Point(x,y));
				}
			}
			invalidate();
			return true;
		case MotionEvent.ACTION_UP:
			currentPoint = null;
		default:
			return true;
		}
	}

	public View getViewByCoords(float f, float g) {
		for (int i = 0; i < getChildCount(); i++) {
			if (contains(getChildAt(i), f, g))
				return getChildAt(i);
		}
		return null;
	}

	private boolean contains(View view, float f, float g) {
		
		  Log.d(null, " x:"+Float.toString(f)+ " y:"+Float.toString(g)+
		  " T:"+Integer.toString(view.getTop())+
		  " B:"+Integer.toString(view.getBottom())+
		  " L:"+Integer.toString(view.getLeft())+
		 " R:"+Integer.toString(view.getRight()));
		 
		return ((g > view.getTop()) && (g < view.getBottom())
				&& (f > view.getLeft()) && (f < view.getRight()));
	}

	public void clear() {
		points.clear();
		currentPoint = null;
	}

	class Point {
		private float x, y;
		

		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}
		
		public void setX(float x){
			this.x = x;
		}
		
		public void setY(float y){
			this.y = y;
		}

		@Override
		public String toString() {
			return x + ", " + y;
		}

		@Override
		public boolean equals(Object p) {
			return ((p instanceof Point) && (((Point) p).x == x) && (((Point) p).y == y));
		}
	}
}
