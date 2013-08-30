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

public class CubeActivity extends SherlockActivity {
	
	// Private instance variables
	private EditText mVariableA;	
	private Button mCalculateButton;
	private TextView mInfoText;
	private double result;

	/** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cube);
                
        mVariableA = (EditText)findViewById(R.id.variable_side);
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
    		if (UIHelper.isEmpty(mVariableA)) {
				UIHelper.setErrorText(mInfoText);
				UIHelper.setEditTextAlert(this, mVariableA);
			} else {
				double side = Double.parseDouble(mVariableA.getText().toString());
				result = FormulaHelper.cubeSurface(side);
				mInfoText.setText("Surface = " + result);				  
			}
    	} catch (InvalidInputException e) {
    		mInfoText.setText("The side can't be negative! Enter a positive value!");
    	}
    }
}
