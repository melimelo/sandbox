package com.melimelo.wordninja.views;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.melimelo.wordninja.adapters.WordsGridAdapter;
import com.melimelo.wordninja.adapters.WordsGridAdapter.PointAction;
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
	WordsGridAdapter adapter = null;

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
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			WordsGridAdapter adapter = (WordsGridAdapter) getAdapter();
			adapter.startNewSelection();
			points.clear();
			onGridPointTouched(event.getX(),event.getY());
			return true;
		case MotionEvent.ACTION_MOVE:
			if (currentPoint != null) {
				currentPoint.setXY(event.getX(), event.getY());
				invalidate();
			}
			onGridPointTouched(event.getX(), event.getY());
			return true;
		case MotionEvent.ACTION_UP:
			currentPoint = null;
		default:
			return true;
		}
	}

	private void onGridPointTouched(float x, float y) {
		int selectedItemIndex = getChildIndexByCoords(x, y);
		checkSelectedItem(selectedItemIndex);	
	}

	private void checkSelectedItem(int selectedItemIndex) {
		
		WordsGridAdapter adapter = (WordsGridAdapter) getAdapter();
		if(selectedItemIndex > -1){
			int x = getChildAt(selectedItemIndex).getRight() - (getChildAt(selectedItemIndex).getWidth() / 2);
			int y = getChildAt(selectedItemIndex).getBottom() - (getChildAt(selectedItemIndex).getHeight() / 2);
			PointAction pointAction = adapter.checkSelectedItem(selectedItemIndex);
			switch (pointAction) {
			case AddNewPoint:
				points.add(new Point(x,y));
				if(currentPoint == null){//on First Touch Down
					currentPoint = new Point(x,y);
				}
				else{
					currentPoint.setXY(x,y);
				}
				invalidate();
				break;
			case RemoveLast:
				points.remove(points.size() - 1);
				currentPoint.setXY(points.get(points.size()-1).getX(),points.get(points.size()-1).getY());
				invalidate();
				break;
			case DoNothing:
				break;
			default:
				break;
			}
		}
	}

	public int getChildIndexByCoords(float f, float g) {
		for (int i = 0; i < getChildCount(); i++) {
			if (contains(getChildAt(i), f, g))
				return i;
		}
		return -1;
	}

	private boolean contains(View view, float f, float g) {
		return ((g > view.getTop()) && (g < view.getBottom())
				&& (f > view.getLeft()) && (f < view.getRight()));
	}

	public void clear() {
		points.clear();
		currentPoint = null;
		invalidate();
	}

	class Point {
		private float x, y;
		

		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}
		
		public float getY() {
			return y;
		}

		public float getX() {
			return x;
		}

		public void setXY(float x, float y) {
			this.x=x;
			this.y=y;
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
