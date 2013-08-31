package com.ndroidstudios.android.formulawizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ndroidstudios.android.helper.CustomCalculatorHelper;
import com.ndroidstudios.android.helper.FontHelper;
import com.ndroidstudios.android.helper.UIHelper;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class CustomCalculator extends SherlockActivity {

	private static int EDITFORMULA_REQ = 1;
	private TextView mNameText;
	private TextView mFormulaText;
	private TextView mInfoText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_calculator);	
		
		mNameText = (TextView)findViewById(R.id.formula_name_heading);
		mFormulaText = (TextView)findViewById(R.id.formula_on_chalkboard);
		mInfoText = (TextView)findViewById(R.id.display_result);
		
		overrideFonts();
		populateViews(this.getIntent());
		createVariableContainers();
		registerButtonListener();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		// TODO inflater.inflate(R.menu.menu_share, menu);
		inflater.inflate(R.menu.menu_edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_edit: 
			Intent intent = new Intent(this, CustomFormulaEdit.class);
			putIntentExtras(intent);
			startActivityForResult(intent, EDITFORMULA_REQ);
			return true;
		case R.id.menu_share:
			return true;
		default: 	
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);	
		if (requestCode == EDITFORMULA_REQ) {
			if (resultCode == MainActivity.RESULT_OK) {	
				populateViews(data);
				createVariableContainers();
			}
		}
	}

	private String getStringIntentExtras(String name) {
		Intent intent = getIntent();
		String value = intent.getStringExtra(name);
		return value;
	}
	
	private long getLongIntentExtras(String name) {
		Intent intent = getIntent();
		long value = intent.getLongExtra("rowID", 0);
		return value;
	}
	
	private String getNameString() {
		return mNameText.getText().toString();
	}
	
	private String getFormulaString() {
		return mFormulaText.getText().toString();
	}
	
	private void putIntentExtras(Intent intent) {
		intent.putExtra("name", getNameString());
		intent.putExtra("formula", getFormulaString());
		intent.putExtra("category", getStringIntentExtras("category"));
		intent.putExtra("rowID", getLongIntentExtras("rowID"));
	}
	
	private void overrideFonts() {
		FontHelper.overrideFonts(this, findViewById(android.R.id.content));
	}
	
	private void populateViews(Intent i) {
		// Override fonts again, for specific content
		Typeface chalkdust = Typeface.createFromAsset(this.getAssets(), "fonts/chalkduster.ttf");
		FontHelper.overrideFonts(this, mFormulaText, chalkdust);
		
		mNameText.setText(i.getStringExtra("name"));
		mFormulaText.setText(i.getStringExtra("formula"));
		
		// Set the default prompt text
		TextView mInfoText = (TextView)findViewById(R.id.display_result);
		UIHelper.setDefaultText(mInfoText);
	}
	
	private void createVariableContainers() {
		ArrayList<String> variableNames = CustomCalculatorHelper.getVariableArray(getFormulaString()); // Holds all variable names
		LinearLayout variableContainer = (LinearLayout)this.findViewById(R.id.variable_container);	// Major item container
		
		variableContainer.removeAllViews(); // Make sure our container is clean before inflating it
		for (int i = 0; i < variableNames.size(); i++) {
			String variableName = variableNames.get(i);
			
			// Create a new container item that holds a TextView and an EditText. Then add it to main container
			LinearLayout variableContainerItem = new ItemContainerLayout(this, variableName, i);
			variableContainer.addView(variableContainerItem, i);
		}
	}
	
	private void registerButtonListener() {
		Button calculateButton = (Button)this.findViewById(R.id.calculate_button);
    	calculateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				handleInput();
			}
		});
    }
	
	private void handleInput() {
		if (UIHelper.isEmpty(CustomCalculatorHelper.getEditTextList(this))) {
			UIHelper.setErrorText(mInfoText);
			UIHelper.setEditTextAlert(this, CustomCalculatorHelper.getEditTextList(this));
		} else {
			Calculable calc;
			try {
				// Get an a String[] from the arraylist that contains all variables
				String[] variableNames = CustomCalculatorHelper.getVariableArray(getFormulaString()).toArray(
						new String[CustomCalculatorHelper.getVariableArray(getFormulaString()).size()]);
				
				// Create new calculable object
				calc = new ExpressionBuilder(getFormulaString())
					.withVariableNames(variableNames)
					.build();
				
				// Create and loop over hashmap so that all variables with values are registered in calc object
				HashMap<String, Double> valuesMap = CustomCalculatorHelper.getValuesMap(this);
				for (Entry<String, Double> entry : valuesMap.entrySet()) {
					calc.setVariable(entry.getKey(), entry.getValue());
				}
				double result = calc.calculate();
				mInfoText.setText(getResources().getString(R.string.result) + " = " + result);
				
			} catch (UnknownFunctionException e) {
				mInfoText.setText(getResources().getString(R.string.unknown_function));
				e.printStackTrace();
			} catch (UnparsableExpressionException e) {
				mInfoText.setText(getResources().getString(R.string.unparsable_error));
				e.printStackTrace();
			} catch (Exception e) {
				mInfoText.setText(getResources().getString(R.string.unknown_error));
				e.printStackTrace();
			}
		}
	}
}


