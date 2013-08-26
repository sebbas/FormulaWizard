package com.ndroidstudios.android.formulawizard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.ndroidstudios.android.helper.CustomArrayAdapter;
 
public class VolumeChooser extends SherlockListActivity {
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getListView().setBackgroundColor(Color.WHITE);
		getListView().setCacheColorHint(0);
 
		String[] values = this.getResources().getStringArray(R.array.volume_list);
		setListAdapter(new CustomArrayAdapter(this, R.layout.listview_item, values, R.id.label));
	}
 
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
 
		switch( position ) {
	       case 0:  Intent j = new Intent(this, ConeVolumeActivity.class);     
	                startActivity(j);
	                break;
	       case 1:  Intent k = new Intent(this, CubeVolumeActivity.class);     
	                startActivity(k);
	                break;
	       case 2:  Intent m = new Intent(this, CylinderVolumeActivity.class);     
	                startActivity(m);
	                break;
	       case 3:  Intent n = new Intent(this, EllipsoidVolumeActivity.class);     
	                startActivity(n);
	                break;
	       case 4:  Intent o = new Intent(this, PrismVolumeActivity.class);     
           			startActivity(o);
           			break;
	       case 5:  Intent p = new Intent(this, PyramidVolumeActivity.class);     
           			startActivity(p);
           			break;
	       case 6:  Intent q = new Intent(this, SphereVolumeActivity.class);     
           			startActivity(q);
           			break;
	    }
	}
 
}