package com.ndroidstudios.android.helper;

import com.ndroidstudios.android.formulawizard.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class CustomArrayAdapter extends ArrayAdapter<String> {
	
	private Context context;
	private String[] values;
	private int viewResource;
	private int rowViewResource;
	
	int imageId[] = { R.drawable.formulawizard_general,
            R.drawable.formulawizard_area2, R.drawable.formulawizard_currency,
            R.drawable.formulawizard_perimeter2, R.drawable.formulawizard_temperature, 
            R.drawable.formulawizard_volume2, R.drawable.formulawizard_weight, 
            R.drawable.formulawizard_length};

	public CustomArrayAdapter(Context context, int viewResource, String[] values, int rowViewResource) {
		super(context, viewResource, values);
		this.context = context;
		this.values = values;
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
		String rowText = this.values[position];
		rowItem.setText(rowText);
		rowItem.setTextColor(Color.BLACK);
		setImageIcon(rowView, rowText);
		return rowView;
	}
	
	private void setImageIcon(View rowView, String rowText) {
		try {
			ImageView icon = (ImageView)rowView.findViewById(R.id.row_icon);
			icon.setImageResource(getCategoryImage(rowText));
		}
		catch (Exception e) {}
	}
	
	private int getCategoryImage(String category) {
		String[] categories = context.getResources().getStringArray(R.array.category_content);
		for (int i = 0; i < categories.length; i++) {
			if (categories[i].equals(category)) return (imageId[i]);
		}
		return (imageId[0]);
	}
}


