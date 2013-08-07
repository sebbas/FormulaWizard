package com.ndroidstudios.android.helper;

import com.ndroidstudios.android.formulawizard.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
public class MyAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
 
	public MyAdapter(Context context, String[] values) {
		super(context, R.layout.list_view, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.list_view, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/chalkboardse.ttc");
		textView.setTypeface(tf);
		textView.setTextColor(Color.BLACK);
		textView.setText(values[position]);
		
		
 
		return rowView;
	}
}