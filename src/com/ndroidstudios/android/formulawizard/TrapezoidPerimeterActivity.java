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

public class TrapezoidPerimeterActivity extends SherlockActivity {
	
	// Private instance variables
	private EditText mVariableA;
	private EditText mVariableB;
	private EditText mVariableC;
	private EditText mVariableD;
	private Button mCalculateButton;
	private TextView mInfoText;
	private double result;

	/** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trapezoid_perimeter);
                
        mVariableA = (EditText)findViewById(R.id.variable_base1);
        mVariableB = (EditText)findViewById(R.id.variable_base2);
        mVariableC = (EditText)findViewById(R.id.variable_base3);
        mVariableD = (EditText)findViewById(R.id.variable_base4);
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
    		if (UIHelper.isEmpty(mVariableA, mVariableB, mVariableC, mVariableD)) {
				UIHelper.setErrorText(mInfoText);
			} else {
				double base1 = Double.parseDouble(mVariableA.getText().toString());
		    	double base2 = Double.parseDouble(mVariableB.getText().toString());
		    	double base3 = Double.parseDouble(mVariableC.getText().toString());
		    	double base4 = Double.parseDouble(mVariableC.getText().toString());
				result = FormulaHelper.trapezoidPerimeter(base1, base2, base3, base4);
				mInfoText.setText("Perimeter = " + result);				  
			}
    	} catch (InvalidInputException e) {
    		mInfoText.setText("The base  can't be negative! Enter a positive value!");
    	}
    }
}


