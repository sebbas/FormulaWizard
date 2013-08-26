package com.ndroidstudios.android.formulawizard;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ndroidstudios.android.helper.FontHelper;

public class CustomCalculator extends SherlockActivity {

	private static int EDITFORMULA_REQ = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_calculator);		
		populateViews(this.getIntent());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_share, menu);
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
	
	private void putIntentExtras(Intent intent) {
		intent.putExtra("name", getStringIntentExtras("name"));
		intent.putExtra("formula", getStringIntentExtras("formula"));
		intent.putExtra("category", getStringIntentExtras("category"));
		intent.putExtra("rowID", getLongIntentExtras("rowID"));
	}
	
	private void populateViews(Intent i) {
		TextView name = (TextView)findViewById(R.id.formula_name_heading);
		TextView formula = (TextView)findViewById(R.id.formula_on_chalkboard);
				
		FontHelper.overrideFonts(this, findViewById(android.R.id.content));
		Typeface chalkdust = Typeface.createFromAsset(this.getAssets(), "fonts/chalkduster.ttf");
		FontHelper.overrideFonts(this, formula, chalkdust);
		
		name.setText(i.getStringExtra("name"));
		formula.setText(i.getStringExtra("formula"));
	}
	
}
