package com.ndroidstudios.android.helper;

import com.ndroidstudios.android.formulawizard.R;

import android.widget.EditText;
import android.widget.TextView;

public class UIHelper {	
	
	public void setDefaultText(TextView mInfoText) {
	    mInfoText.setText(R.string.input_prompt);
	}
	
	public void setErrorText(TextView mInfoText) {
		mInfoText.setText(R.string.input_not_complete);
	}
	
	public boolean editTextIsEmpty (EditText... args ) {
		for (int i = 0 ; i < args.length; i++) {
			if (args[i].getText().toString().equals("")) {
				return true;
			} 
		}
		return false;
	}
}


