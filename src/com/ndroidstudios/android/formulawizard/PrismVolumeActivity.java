package com.ndroidstudios.android.formulawizard;

import com.ndroidstudios.android.helper.FormulaHelper;
import com.ndroidstudios.android.helper.FontHelper;
import com.ndroidstudios.android.helper.UIHelper;
import com.ndroidstudios.android.helper.FormulaHelper.InvalidInputException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PrismVolumeActivity extends Activity {

	// Private instance variables
	private EditText mVariableA;
	private EditText mVariableB;
	private EditText mVariableC;
	private Button mCalculateButton;
	private TextView mInfoText;
	private double result;

	/** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prism_volume);
                
        mVariableA = (EditText)findViewById(R.id.variable_length);
        mVariableB = (EditText)findViewById(R.id.variable_width);
        mVariableC = (EditText)findViewById(R.id.variable_height);
        mCalculateButton = (Button)findViewById(R.id.calculate_button);
        mInfoText = (TextView)findViewById(R.id.display_x1);
        
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
    		if (UIHelper.isEmpty(mVariableA, mVariableB, mVariableC)) {
				UIHelper.setErrorText(mInfoText);
			} else {
				double length = Double.parseDouble(mVariableA.getText().toString());
		    	double width = Double.parseDouble(mVariableB.getText().toString());
		    	double height = Double.parseDouble(mVariableC.getText().toString());
				result = FormulaHelper.prismVolume(length, width, height);
				mInfoText.setText("Volume = " + result);				  
			}
    	} catch (InvalidInputException e) {
    		mInfoText.setText("The length/ width/ height can't be negative! Enter a positive value!");
    	}
    }
}



