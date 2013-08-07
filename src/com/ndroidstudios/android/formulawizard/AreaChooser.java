package com.ndroidstudios.android.formulawizard;

import com.ndroidstudios.android.helper.MyAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
 
public class AreaChooser extends ListActivity {
 
	static final String [] AREA = new String [] { "Circle", "Cone", "Cube", "Cylinder", 
		"Ellipse", "Rectangle", "Square", "Trapezoid", "Triangle"};
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getListView().setBackgroundColor(Color.WHITE);
		getListView().setCacheColorHint(0);
		
		setListAdapter(new MyAdapter(this, AREA));
		
	}
 
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
 
		switch( position )
	    {
	       case 0:  Intent i = new Intent(this, CircleActivity.class);     
	                startActivity(i);
	                break;
	       case 1:  Intent j = new Intent(this, ConeActivity.class);     
	                startActivity(j);
	                break;
	       case 2:  Intent k = new Intent(this, CubeActivity.class);     
	                startActivity(k);
	                break;
	       case 3:  Intent m = new Intent(this, CylinderActivity.class);     
	                startActivity(m);
	                break;
	       case 4:  Intent n = new Intent(this, EllipseActivity.class);     
	                startActivity(n);
	                break;
	       case 5:  Intent o = new Intent(this, RectangleActivity.class);     
           			startActivity(o);
           			break;
	       case 6:  Intent p = new Intent(this, SquareActivity.class);     
           			startActivity(p);
           			break;
	       case 7:  Intent q = new Intent(this, TrapezoidActivity.class);     
           			startActivity(q);
           			break;
	       case 8:  Intent r = new Intent(this, TriangleActivity.class);     
           			startActivity(r);
           			break;
	    }
	}
}
