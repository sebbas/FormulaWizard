package com.ndroidstudios.android.formulawizard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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
		
	int imageId[] = { R.drawable.formulawizard_volume2,
            R.drawable.formulawizard_volume2, R.drawable.formulawizard_volume2,
            R.drawable.formulawizard_volume2, R.drawable.formulawizard_volume2, R.drawable.formulawizard_weight};

	private TextView mFormulaHeading;
	private EditText mFormulaName;	
	private TextView mFormulaOnChalkboard;
	private EditText mFormula;
	private Spinner mCategorySpinner;
	private boolean mIsEdited = false;
	private String[] initialState;
	private DBAdapter myDB;	
	private AlertDialog mAlertDialog;
	private AlertDialog mInfoDialog;
	
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
		FontHelper.overrideFonts(this, chalkdust, findViewById(R.id.formula_on_chalkboard));
		
		mFormulaHeading = (TextView)findViewById(R.id.formula_name_heading);
		mFormulaName = (EditText)findViewById(R.id.formula_name_edit);
		mFormulaOnChalkboard = (TextView)findViewById(R.id.formula_on_chalkboard);
		mFormula = (EditText)findViewById(R.id.formula_edit);
		mCategorySpinner = (Spinner)findViewById(R.id.category_spinner);
		mCategorySpinner.setAdapter(getCustomSpinner());
				
		// This happens when the activity is called to edit an exiting formula
		tryPopulatingView(this.getIntent()); 
		saveEditTextState(this.getIntent());
		mFormula.addTextChangedListener(formulaWatcher);
		mFormulaName.addTextChangedListener(nameWatcher);
		
		// DB setup
		openDB(); // So that we can make changes and save them
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_about, menu);
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
		
		case R.id.menu_about:
			startInfoPrompt();
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
	
	@Override
	public void onPause() {
		super.onPause();
		if(mAlertDialog != null && mAlertDialog.isShowing()) {
			mAlertDialog.dismiss(); 
		}
		if(mInfoDialog != null && mInfoDialog.isShowing()) {
			mInfoDialog.dismiss(); 
		}
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
		if (initialState[0].equals(mFormulaName.getText().toString()) 
				&& initialState[1].equals(mFormula.getText().toString())
				&& initialState[2].equals(mCategorySpinner.getSelectedItem().toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	private void startAlertPrompt() {
		LayoutInflater inflater = getLayoutInflater();
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
			.setView(inflater.inflate(R.layout.cancel_alert, null))
			.setCancelable(false)
			.setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					cancelActivity();
				}
			})
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		mAlertDialog = alertDialogBuilder.create();
		mAlertDialog.show();
		
		TextView title = (TextView) mAlertDialog.findViewById(R.id.title);
		TextView message = (TextView) mAlertDialog.findViewById(R.id.message);
		TextView button1 = (TextView) mAlertDialog.findViewById(android.R.id.button1);
		TextView button2 = (TextView) mAlertDialog.findViewById(android.R.id.button2);
		TextView[] views = {message, title, button1, button2};
		FontHelper.overrideFonts(this, views);
		
	}
	
	private void startInfoPrompt() {
		LayoutInflater inflater = getLayoutInflater();
		AlertDialog.Builder infoDialogBuilder = new AlertDialog.Builder(this);
		infoDialogBuilder
			.setView(inflater.inflate(R.layout.info_dialog, null))
			.setCancelable(false)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
			}
		});
		mInfoDialog = infoDialogBuilder.create();
		mInfoDialog.show();
		
		TextView title = (TextView) mInfoDialog.findViewById(R.id.info_title);
		TextView message = (TextView) mInfoDialog.findViewById(R.id.info_message);
		TextView button1 = (TextView) mInfoDialog.findViewById(android.R.id.button1);
		TextView[] views = {message, title, button1};
		FontHelper.overrideFonts(this, views);
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
	
	// Save the content of the EditTexts and the Spinner so that we can cancel the activity 
	// without alert if nothing changed
	private String[] saveEditTextState(Intent intent) {
		initialState = new String[3];
		initialState[0] = (mFormulaName.getText().toString());
		initialState[1] = (mFormula.getText().toString());
		initialState[2] = mCategorySpinner.getSelectedItem().toString();
		return initialState;
	}
}


