package com.ndroidstudios.android.formulawizard;

import com.ndroidstudios.android.helper.Formulas;
import com.ndroidstudios.android.helper.FontHelper;
import com.ndroidstudios.android.helper.UIHelper;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DistanceCalculator extends Activity {
	
	// Private instance variables
	private EditText mVariableA;
	private EditText mVariableB;
	private EditText mVariableC;
	private EditText mVariableD;
	private Button mCalculateButton;
	private TextView mInfoText;
	private double result;
	
	// Helper instance variables
	private UIHelper uiHelper = new UIHelper();
	private Formulas calculator = new Formulas();
	
	/** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distance);
        
        mVariableA = (EditText)findViewById(R.id.variable_x1);
        mVariableB = (EditText)findViewById(R.id.variable_x2);
        mVariableC = (EditText)findViewById(R.id.variable_y1);
        mVariableD = (EditText)findViewById(R.id.variable_y2);
        
        // Set text for variable strings. Necessary for subscripts
        ((TextView)findViewById(R.id.string_x1)).setText(Html.fromHtml("x<sub><small><small>1</small></small></sub>"));
        ((TextView)findViewById(R.id.string_x2)).setText(Html.fromHtml("x<sub><small><small>2</small></small></sub>"));
        ((TextView)findViewById(R.id.string_y1)).setText(Html.fromHtml("y<sub><small><small>1</small></small></sub>"));
        ((TextView)findViewById(R.id.string_y2)).setText(Html.fromHtml("y<sub><small><small>2</small></small></sub>"));

        mCalculateButton = (Button)findViewById(R.id.calculate_button);
        mInfoText = (TextView)findViewById(R.id.display_x1);
        
        registerButtonListener();
        uiHelper.setDefaultText(mInfoText);
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
    	if (uiHelper.editTextIsEmpty(mVariableA, mVariableB, mVariableC, mVariableD)) {
			uiHelper.setErrorText(mInfoText);
		} else {
			double x1 = Double.parseDouble(mVariableA.getText().toString());
			double x2 = Double.parseDouble(mVariableB.getText().toString());
			double y1 = Double.parseDouble(mVariableC.getText().toString());
			double y2 = Double.parseDouble(mVariableD.getText().toString());
			result = calculator.distance(x1, x2, y1, y2);
			mInfoText.setText("Distance = " + result);				  
		}
    }
}



