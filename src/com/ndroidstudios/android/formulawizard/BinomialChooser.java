package com.ndroidstudios.android.formulawizard;

import com.ndroidstudios.android.helper.CustomArrayAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
 
public class BinomialChooser extends ListActivity {
  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 
		getListView().setBackgroundColor(Color.WHITE);
		getListView().setCacheColorHint(0);

		String[] values = this.getResources().getStringArray(R.array.binomial_list);
		setListAdapter(new CustomArrayAdapter(this, R.layout.list_view, values, R.id.label));
	}
 
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
 
		switch( position ) {
	       case 0:  Intent i = new Intent(this, Exponent2Activity.class);     
	                startActivity(i);
	                break;
	       case 1:  Intent j = new Intent(this, Exponent3Activity.class);     
	                startActivity(j);
	                break;    
	    }
	}
}
