package com.ndroidstudios.android.formulawizard;

import com.ndroidstudios.android.helper.MyAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
 
public class PerimeterChooser extends ListActivity {
 
	static final String [] PERIMETER = new String [] { "Circle", "Ellipse", "Rectangle", "Square", "Trapezoid", "Triangle"};
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 
		getListView().setBackgroundColor(Color.WHITE);
		getListView().setCacheColorHint(0);

		setListAdapter(new MyAdapter(this, PERIMETER));
 
	}
 
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
 
		switch( position )
	    {
	       case 0:  Intent i = new Intent(this, CirclePerimeterActivity.class);     
	                startActivity(i);
	                break;
	       case 1:  Intent j = new Intent(this, EllipsePerimeterActivity.class);     
	                startActivity(j);
	                break;
	       case 2:  Intent k = new Intent(this, RectanglePerimeterActivity.class);     
	                startActivity(k);
	                break;
	       case 3:  Intent m = new Intent(this, SquarePerimeterActivity.class);     
	                startActivity(m);
	                break;
	       case 4:  Intent n = new Intent(this, TrapezoidPerimeterActivity.class);     
	                startActivity(n);
	                break;
	       case 5:  Intent o = new Intent(this, TrianglePerimeterActivity.class);     
           			startActivity(o);
           			break;
	    }
	}
}
