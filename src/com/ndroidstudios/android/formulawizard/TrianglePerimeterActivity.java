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

public class TrianglePerimeterActivity extends SherlockActivity {
	
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
        setContentView(R.layout.triangle_perimeter);
                
        mVariableA = (EditText)findViewById(R.id.variable_base);
        mVariableB = (EditText)findViewById(R.id.variable_side1);
        mVariableC = (EditText)findViewById(R.id.variable_side2);
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
    		if (UIHelper.isEmpty(mVariableA, mVariableB, mVariableC)) {
				UIHelper.setErrorText(mInfoText);
				UIHelper.setEditTextAlert(this, mVariableA, mVariableB, mVariableC);
			} else {
				double base = Double.parseDouble(mVariableA.getText().toString());
		    	double side1 = Double.parseDouble(mVariableB.getText().toString());
		    	double side2 = Double.parseDouble(mVariableC.getText().toString());
				result = FormulaHelper.trianglePerimeter(base, side1, side2);
				mInfoText.setText("Perimeter = " + result);				  
			}
    	} catch (InvalidInputException e) {
    		mInfoText.setText("The base/ sides can't be negative! Enter a positive value!");
    	}
    }
}


