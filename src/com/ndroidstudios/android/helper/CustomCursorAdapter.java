package com.ndroidstudios.android.helper;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.ndroidstudios.android.formulawizard.R;
 
public class CustomCursorAdapter extends SimpleCursorAdapter {

	private Context mContext;
	
	int imageId[] = { R.drawable.formulawizard_volume,
            R.drawable.formulawizard_volume, R.drawable.formulawizard_volume,
            R.drawable.formulawizard_volume, R.drawable.formulawizard_volume, R.drawable.formulawizard_weight};
	
	public CustomCursorAdapter(Context context, int layout, Cursor cursor, String[] fromDB, int[] toView) {
	    super(context, layout, cursor, fromDB, toView);
	    this.mContext = context;
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
	   
	   // Populate ImageView with corresponding category icon
	   ImageView icon = (ImageView)view.findViewById(R.id.item_icon);
	   System.out.println("Called");
	   icon.setImageResource(getCategoryImage(categoryString));
	 }
	
	private int getCategoryImage(String category) {
		String[] categories = mContext.getResources().getStringArray(R.array.category_content);
		for (int i = 0; i < categories.length; i++) {
			if (categories[i].equals(category)) return (imageId[i]);
		}
		return (imageId[0]);
	}
}