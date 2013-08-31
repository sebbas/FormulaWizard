package com.ndroidstudios.android.formulawizard;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.ndroidstudios.android.helper.FontHelper;
import com.ndroidstudios.android.helper.FormulaHelper;
import com.ndroidstudios.android.helper.UIHelper;

public class DistanceCalculator extends SherlockActivity {
	
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
    	if (UIHelper.isEmpty(mVariableA, mVariableB, mVariableC, mVariableD)) {
			UIHelper.setErrorText(mInfoText);
			UIHelper.setEditTextAlert(this, mVariableA, mVariableB, mVariableC, mVariableD);
		} else {
			double x1 = Double.parseDouble(mVariableA.getText().toString());
			double x2 = Double.parseDouble(mVariableB.getText().toString());
			double y1 = Double.parseDouble(mVariableC.getText().toString());
			double y2 = Double.parseDouble(mVariableD.getText().toString());
			result = FormulaHelper.distance(x1, x2, y1, y2);
			mInfoText.setText(this.getResources().getString(R.string.distance) + " = " + result);			  
		}
    }
}



