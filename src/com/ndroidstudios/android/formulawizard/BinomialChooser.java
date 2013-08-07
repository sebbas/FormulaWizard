package com.ndroidstudios.android.formulawizard;

import com.ndroidstudios.android.helper.MyAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
 
public class BinomialChooser extends ListActivity {
 
	static final String [] EX = new String [] { "Exponent of 2", "Exponent of 3"};
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 
		getListView().setBackgroundColor(Color.WHITE);
		getListView().setCacheColorHint(0);

		setListAdapter(new MyAdapter(this, EX));
 
	}
 
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
 
		switch( position )
	    {
	       case 0:  Intent i = new Intent(this, Exponent2Activity.class);     
	                startActivity(i);
	                break;
	       case 1:  Intent j = new Intent(this, Exponent3Activity.class);     
	                startActivity(j);
	                break;
	       
	    }
	}
}
