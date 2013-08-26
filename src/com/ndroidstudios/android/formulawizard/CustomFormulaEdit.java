package com.ndroidstudios.android.formulawizard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ndroidstudios.android.helper.CustomArrayAdapter;
import com.ndroidstudios.android.helper.DBAdapter;
import com.ndroidstudios.android.helper.FontHelper;
import com.ndroidstudios.android.helper.UIHelper;

public class CustomFormulaEdit extends SherlockActivity {
	
    int arr_images[] = { R.drawable.formulawizard_button,
                         R.drawable.formulawizard_button, R.drawable.formulawizard_button,
                         R.drawable.formulawizard_button, R.drawable.formulawizard_button, R.drawable.formulawizard_button};
    
	private TextView mFormulaHeading;
	private EditText mFormulaName;	
	private TextView mFormulaOnChalkboard;
	private EditText mFormula;
	private Spinner mCategorySpinner;
	private boolean mIsEdited = false;
	private String[] editTextState;
	private DBAdapter myDB;
	
	private TextWatcher nameWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
			mFormulaHeading.setText(mFormulaName.getText().toString());
			if (UIHelper.isEmpty(mFormulaHeading)) {
				mFormulaHeading.setText(R.string.your_custom_formula);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {			
		}
	};
	
	private TextWatcher formulaWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
			mFormulaOnChalkboard.setText(mFormula.getText().toString());	
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_edit);
		
		FontHelper.overrideFonts(this, findViewById(android.R.id.content));
		Typeface chalkdust = Typeface.createFromAsset(this.getAssets(), "fonts/chalkduster.ttf");
		FontHelper.overrideFonts(this, findViewById(R.id.formula_on_chalkboard), chalkdust);
		
		mFormulaHeading = (TextView)findViewById(R.id.formula_name_heading);
		mFormulaName = (EditText)findViewById(R.id.formula_name_edit);
		mFormulaOnChalkboard = (TextView)findViewById(R.id.formula_on_chalkboard);
		mFormula = (EditText)findViewById(R.id.formula_edit);
		mCategorySpinner = (Spinner)findViewById(R.id.category_spinner);
		mCategorySpinner.setAdapter(getCustomSpinner());
		
		// This happens when the activity is called to edit an exiting formula
		tryPopulatingView(this.getIntent()); 
		saveEditTextState();
		mFormula.addTextChangedListener(formulaWatcher);
		mFormulaName.addTextChangedListener(nameWatcher);
		
		openDB(); // So that we can make changes and save them
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_save, menu);
		inflater.inflate(R.menu.menu_cancel, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId()) {
		case R.id.menu_cancel: 
			if(noDataEntered()) {
				cancelActivity();
			} else {
				startAlertPrompt();
			}
			return true;
			
		case R.id.menu_save: 
			if (allFieldsSet()) {
				Intent saveIntent = new Intent();
				saveIntent.putExtra("name", mFormulaName.getText().toString());		
				saveIntent.putExtra("formula", mFormula.getText().toString());
				saveIntent.putExtra("category", mCategorySpinner.getSelectedItem().toString());
				
				saveData();
				setResult(RESULT_OK, saveIntent);
				Toast.makeText(getApplicationContext(), this.getResources().
						getString(R.string.saved_successfully), Toast.LENGTH_LONG).show();
				finish();
			} else {
				Toast.makeText(getApplicationContext(), this.getResources().
						getString(R.string.input_not_complete2), Toast.LENGTH_LONG).show();	
			}
			return true;
			
		default: 	
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		closeDB();
	}

	private void openDB() {
		myDB = new DBAdapter(this);
		myDB.open();
	}
	
	private void closeDB() {
		myDB.close();
	}
	
	private boolean allFieldsSet() {
		if (UIHelper.isEmpty(mFormulaName) || UIHelper.isEmpty(mFormula)) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean noDataEntered() {
		if (editTextState[0].equals(mFormulaName.getText().toString()) 
				&& editTextState[1].equals(mFormula.getText().toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	private void startAlertPrompt() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
			.setTitle(R.string.alert_notsaved_title)
			.setMessage(R.string.alert_notsaved_subtitle)
			.setCancelable(false)
			.setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					cancelActivity();
				}
			})
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	private void cancelActivity() {
		Intent cancelIntent = new Intent();
		setResult(RESULT_CANCELED, cancelIntent);
		finish();
	}
	
	@Override
	public void onBackPressed() {
		if(noDataEntered()) {
			cancelActivity();
		} else {
			startAlertPrompt();
		}
	}
	
	private void tryPopulatingView(Intent intent) {
		if (intent.getExtras() != null) {
			String name = intent.getStringExtra("name");
			String formula = intent.getStringExtra("formula");
			String category = intent.getStringExtra("category");
			
			// Populate EditTexts
			mFormulaName.setText(name);
			mFormula.setText(formula);
			
			// Populate TextViews
			mFormulaHeading.setText(name);
			mFormulaOnChalkboard.setText(formula);
			
			// Set the selected Spinner item
			mCategorySpinner.setSelection(getCustomSpinner().getPosition(category));
			
			// We are working with an existing formula -> we are editing
			mIsEdited = true;
		}
	}
	
	private CustomArrayAdapter getCustomSpinner() {
		String[] values = this.getResources().getStringArray(R.array.category_content);
		return new CustomArrayAdapter(this, R.layout.spinner_row, values, R.id.row_label);		
	}
	
	private void saveData() {
		String name = mFormulaName.getText().toString();		
		String formula =  mFormula.getText().toString();
		String category = mCategorySpinner.getSelectedItem().toString();
		if (mIsEdited) {
			long rowID = getIntent().getLongExtra("rowID", 0);
			myDB.updateRow(rowID, name, formula, category);
		} else {
			myDB.insertRow(name, formula, category);
		}
	}
	
	// Save the content of the EditTexts so that we can cancel the activity without alert
	// if nothing changed
	private String[] saveEditTextState() {
		editTextState = new String[2];
		editTextState[0] = (mFormulaName.getText().toString());
		editTextState[1] = (mFormulaName.getText().toString());
		return editTextState;
	}
}


