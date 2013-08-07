package com.ndroidstudios.android.formulawizard;

import com.ndroidstudios.android.helper.MyAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
 
public class Exponent2Activity extends ListActivity {

	static final String [] EX2 = new String [] { "Binomial theorem for a sum", 
		"Binomial theorem for a difference"};
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getListView().setBackgroundColor(Color.WHITE);
		getListView().setCacheColorHint(0);
 
		setListAdapter(new MyAdapter(this, EX2));
 
	}
 
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
 
		switch( position )
	    {
	       case 0:  Intent i = new Intent(this, Exponent2positiveActivity.class);     
	                startActivity(i);
	                break;
	       case 1:  Intent j = new Intent(this, Exponent2negativeActivity.class);     
	                startActivity(j);
	                break;
	       
	    }
	}
}
