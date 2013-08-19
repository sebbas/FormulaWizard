package com.ndroidstudios.android.formulawizard;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ndroidstudios.android.helper.DBAdapter;

public class CustomFormulaFragment extends SherlockFragment {
	
	private DBAdapter myDB;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.custom_list, container, false);
		
		//openDB();
		//populateListViewFromDB();
		
		return rootView;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	   super.onCreateOptionsMenu(menu, inflater);
	   inflater.inflate(R.menu.menu_add, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_add: 
			Intent intent = new Intent(this.getActivity(), CustomFormulaEdit.class);
			this.getActivity().startActivity(intent);
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
		myDB.open();
	}
	
	private void closeDB() {
		myDB.close();
	}
	
	private void populateListViewFromDB() {
		Cursor cursor = myDB.getAllRows();
		
	}
}
