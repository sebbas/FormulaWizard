package com.ndroidstudios.android.formulawizard;

import com.ndroidstudios.android.helper.Formulas;
import com.ndroidstudios.android.helper.FontHelper;
import com.ndroidstudios.android.helper.UIHelper;
import com.ndroidstudios.android.helper.Formulas.InvalidInputException;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class QuadraticCalculator extends Activity {
	
	// Private instance variables
	private EditText mVariableA;
	private EditText mVariableB;
	private EditText mVariableC;
	private Button mCalculateButton;
	private TextView mInfoText;
	private TextView mInfoText2;
	private double[] results;
	
	// Helper instance variables
	private UIHelper uiHelper = new UIHelper();
	private Formulas calculator = new Formulas();
	
	/** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quadratic);
                
        mVariableA = (EditText)findViewById(R.id.variable_a);
        mVariableB = (EditText)findViewById(R.id.variable_b);
        mVariableC = (EditText)findViewById(R.id.variable_c);
        mCalculateButton = (Button)findViewById(R.id.calculate_button);
        mInfoText = (TextView)findViewById(R.id.display_x1);
        mInfoText2 = (TextView)findViewById(R.id.display_x2);
        
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
    	try {
    		if (uiHelper.editTextIsEmpty(mVariableA, mVariableB, mVariableC)) {
				uiHelper.setErrorText(mInfoText);
			} else {
				double a = Double.parseDouble(mVariableA.getText().toString());
		    	double b = Double.parseDouble(mVariableB.getText().toString());
		    	double c = Double.parseDouble(mVariableC.getText().toString());
				results = calculator.quadratic(a, b, c);
				mInfoText.setText(Html.fromHtml("<body>x<sub><small><small>1</small></small></sub> = </body>" + results[0]));
				mInfoText2.setText(Html.fromHtml("<body>x<sub><small><small>2</small></small></sub> = </body>" + results[1]));			  
			}
    	} catch (InvalidInputException e) {
    		mInfoText.setText("There are no real roots!");
    		mInfoText2.setText("");
    	}
    }
}


