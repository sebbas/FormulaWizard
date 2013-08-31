package com.ndroidstudios.android.formulawizard;

import com.ndroidstudios.android.helper.FontHelper;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemContainerLayout extends LinearLayout {

	private String itemName;
	private int id;
	
	public ItemContainerLayout(Context context, AttributeSet attrs, String itemName, int id) {
		super(context, attrs);
		this.itemName = itemName;
		this.id = id;
		init(context);
	}
	
	public ItemContainerLayout(Context context, String itemName, int id) {
		super(context);
		this.itemName = itemName;
		this.id = id;
		init(context);
	}
	
	private void init(Context context) {
		inflate(context, R.layout.variable_container_item, this);
		this.setId(id);
		
		// Set text for item container
		TextView itemText = (TextView)this.findViewById(R.id.variable_text);
		EditText itemEdit = (EditText)this.findViewById(R.id.variable_edit);
		itemEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER); // Restrict input type to numbers only
		FontHelper.overrideFonts(context, itemText);
		FontHelper.overrideFonts(context, itemEdit);
		itemText.setText(this.itemName);
	}
}
