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
import com.ndroidstudios.android.helper.FontHelper;

public class CustomFormulaFragment extends SherlockFragment {
	
	private static int EDITFORMULA_REQ = 1;
	
	private DBAdapter myDB;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		setHasOptionsMenu(true);
		openDB();
		View rootView = populateViewFromDB(inflater, container);
		
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
			this.getActivity().startActivityForResult(intent, EDITFORMULA_REQ);
			return true;
		default: 	
			return super.onOptionsItemSelected(item);
		}
	}

	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		closeDB();
	}

	
	private void openDB() {
		myDB = new DBAdapter(this.getActivity());
		myDB.open();
	}
	
	private void closeDB() {
		myDB.close();
	}
	
	private View populateViewFromDB(LayoutInflater inflater, ViewGroup container) {
		View rootView;
		if (myDB.isEmpty()) {
			rootView = inflater.inflate(R.layout.custom_empty, container, false);
			FontHelper.overrideFonts(this.getActivity(), rootView.findViewById(R.id.empty_list_info));
			return rootView;
		} else {
			Cursor cursor = myDB.getAllRows();
			rootView = inflater.inflate(R.layout.custom_empty, container, false);
			return rootView;
		}
		
		
	}
}
