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

public class EllipsePerimeterActivity extends Activity {

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
        setContentView(R.layout.ellipse_perimeter);
        
        mVariableA = (EditText)findViewById(R.id.variable_radius1);
        mVariableB = (EditText)findViewById(R.id.variable_radius2);
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
			} else {
				double radius1 = Double.parseDouble(mVariableA.getText().toString());
				double radius2 = Double.parseDouble(mVariableB.getText().toString());
				result = FormulaHelper.ellipsePerimeter(radius1, radius2);
				mInfoText.setText("Perimeter = " + result);				  
			}
    	} catch (InvalidInputException e) {
    		mInfoText.setText("The radius can't be negative! Enter a positive value!");
    	}
    }
}



