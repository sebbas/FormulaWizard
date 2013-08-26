package com.ndroidstudios.android.helper;

import com.ndroidstudios.android.formulawizard.R;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
 
public class CustomCursorAdapter extends SimpleCursorAdapter {

	public CustomCursorAdapter(Context context, int layout, Cursor cursor, String[] fromDB, int[] toView) {
	    super(context, layout, cursor, fromDB, toView);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
	
	   Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/chalkboardse.ttc");
	   
	   TextView nameView = (TextView)view.findViewById(R.id.item_name); 
	   TextView categoryView = (TextView)view.findViewById(R.id.item_category);
	   
	   String nameString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_NAME));
	   String categoryString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_CATEGORY));
	   
	   nameView.setText(nameString);
	   categoryView.setText(categoryString);
	   
	   nameView.setTypeface(tf);
	   categoryView.setTypeface(tf);
	 }
}