package com.ndroidstudios.android.formulawizard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.ndroidstudios.android.helper.FontHelper;
import com.ndroidstudios.android.helper.FormulaHelper;
import com.ndroidstudios.android.helper.FormulaHelper.InvalidInputException;
import com.ndroidstudios.android.helper.UIHelper;

public class CylinderActivity extends SherlockActivity {
	
	// Private instance variables
	private EditText mVariableA;
	private EditText mVariableB;
	private Button mCalculateButton;
	private TextView mInfoText;
	private double result;

	/** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cylinder);
 
        mVariableA = (EditText)findViewById(R.id.variable_radius);
        mVariableB = (EditText)findViewById(R.id.variable_height);
        mCalculateButton = (Button)findViewById(R.id.calculate_button);
        mInfoText = (TextView)findViewById(R.id.display_result);
        
        registerButtonListener();
        UIHelper.setDefaultText(mInfoText);
        FontHelper.overrideFonts(this, findViewById(android.R.id.content));
    }

    @Override
    protected void onPause() {
    	super.onPause();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    }
    
    private void registerButtonListener() {	
    	mCalculateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				handleInput();
			}
		});
    }
    
    private void handleInput() {
    	try {
    		if (UIHelper.isEmpty(mVariableA, mVariableB)) {
				UIHelper.setErrorText(mInfoText);
				UIHelper.setEditTextAlert(this, mVariableA, mVariableB);
			} else {
				double radius = Double.parseDouble(mVariableA.getText().toString());
				double height = Double.parseDouble(mVariableB.getText().toString());
				result = FormulaHelper.cylinderSurface(radius, height);
				mInfoText.setText(this.getResources().getString(R.string.surface) + " = " + result);				  
			}
    	} catch (InvalidInputException e) {
    		mInfoText.setText(this.getResources().getString(R.string.radiusheight_not_negative) 
    				+ " " + this.getResources().getString(R.string.enter_positive_value));
    	}
    }
}


