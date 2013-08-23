package com.ndroidstudios.android.helper;

import java.util.Arrays;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
public class CustomArrayAdapter extends ArrayAdapter<String> {
	private Context context;
	private String[] values;
	private int viewResource;
	private int rowViewResource;

	public CustomArrayAdapter(Context context, int viewResource, String[] values, int rowViewResource) {
		super(context, viewResource, values);
		this.context = context;
		this.values = values;
		System.out.println("Array is: " + Arrays.toString(this.values));
		this.viewResource = viewResource;
		this.rowViewResource = rowViewResource;
	}
	
	@Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
 
	public View getCustomView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(viewResource, parent, false);
		TextView rowItem = (TextView) rowView.findViewById(rowViewResource);
		FontHelper.overrideFonts(this.context, rowItem);
		rowItem.setText(this.values[position]);
		rowItem.setTextColor(Color.BLACK);
		return rowView;
	}
}