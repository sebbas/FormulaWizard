package com.ndroidstudios.android.helper;

import com.ndroidstudios.android.formulawizard.R;

import android.widget.EditText;
import android.widget.TextView;

public class UIHelper {	
	
	public static void setDefaultText(TextView mInfoText) {
	    mInfoText.setText(R.string.input_prompt);
	}
	
	public static void setErrorText(TextView mInfoText) {
		mInfoText.setText(R.string.input_not_complete);
	}
	
	public static boolean isEmpty (TextView... args ) {
		for (int i = 0 ; i < args.length; i++) {
			if (args[i].getText().toString().equals("")) {
				return true;
			} 
		}
		return false;
	}
}


