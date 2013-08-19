package com.ndroidstudios.android.formulawizard;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.ndroidstudios.android.helper.FontHelper;

import android.content.Intent;
import android.os.Bundle;

public class CustomFormulaEdit extends SherlockActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_edit);
		
		FontHelper.overrideFonts(this, findViewById(android.R.id.content));
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_cancel, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId()) {
		case R.id.menu_cancel: 
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			return true;
		default: 	
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	
	
	
	
	
	

}
