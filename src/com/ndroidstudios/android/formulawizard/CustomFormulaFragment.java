package com.ndroidstudios.android.formulawizard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ndroidstudios.android.helper.CustomCursorAdapter;
import com.ndroidstudios.android.helper.DBAdapter;
import com.ndroidstudios.android.helper.FontHelper;

public class CustomFormulaFragment extends SherlockFragment {
	
	private static int EDITFORMULA_REQ = 1;
	private ListView listFromDB;
	private TextView emptyListInfoText;
	private View rootView;	
	private DBAdapter myDB;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		setHasOptionsMenu(true);
		openDB();
		rootView = inflater.inflate(R.layout.custom_list, container, false);
		listFromDB = (ListView)rootView.findViewById(R.id.list_from_db);
		emptyListInfoText = (TextView)rootView.findViewById(R.id.empty_list_info);
		populateViewFromDB();
		
		return rootView;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	   super.onCreateOptionsMenu(menu, inflater);
	   inflater.inflate(R.menu.menu_add, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId()) {
		case R.id.menu_add: 
			Intent intent = new Intent(this.getActivity(), CustomFormulaEdit.class);
			startActivityForResult(intent, EDITFORMULA_REQ);
			return true;
		default: 	
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == EDITFORMULA_REQ) {
			if (resultCode == MainActivity.RESULT_OK) {	
				populateViewFromDB();
			}
		}
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
	
	private void populateViewFromDB() {
		listFromDB.setVisibility(View.VISIBLE);
		emptyListInfoText.setVisibility(View.VISIBLE);
		
		if (myDB.isEmpty()) {
			FontHelper.overrideFonts(this.getActivity(), rootView.findViewById(R.id.empty_list_info));
			listFromDB.setVisibility(View.GONE);
		} else {
			// TODO Implement code that is not deprecated. This runs on the UI thread.
			Cursor cursor = myDB.getAllRows();
				
			// Allow activity to manage lifetime of cursor
			this.getActivity().startManagingCursor(cursor);
						
			// Setup mapping from cursor to fields in the UI
			String[] fromDBFields = new String[] {DBAdapter.KEY_NAME, DBAdapter.KEY_CATEGORY};
			int[] toViewIDs = new int[] {R.id.item_name, R.id.item_category};
			
			// Create adapter to map columns from DB to elements in the UI
			CustomCursorAdapter myCursorAdapter = new CustomCursorAdapter(
					this.getActivity(), 
					R.layout.custom_list_item,  // Row layout 
					cursor,						// cursor (set of DB records to map)
					fromDBFields,				// DB column names
					toViewIDs					// View IDs to put information in
					);
							
			emptyListInfoText.setVisibility(View.GONE);
			listFromDB.setAdapter(myCursorAdapter);
			registerListClickCallback(rootView);
		}
	}
	
	private void registerListClickCallback(View rootView) {
		
		listFromDB.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position,
					long idInDB) {
				Cursor cursor = myDB.getRow(idInDB);
				if(cursor.moveToFirst()) {
					String name = cursor.getString(DBAdapter.COL_NAME);
					String formula = cursor.getString(DBAdapter.COL_FORMULA);
					String category = cursor.getString(DBAdapter.COL_CATEGORY);
					
					Intent customCalculator = new Intent(CustomFormulaFragment.this.getActivity(), CustomCalculator.class);
					customCalculator.putExtra("name", name);
					customCalculator.putExtra("formula", formula);
					customCalculator.putExtra("category", category);
					customCalculator.putExtra("rowID", idInDB);
					startActivity(customCalculator);
				}
				cursor.close();
			}
		});
		
		listFromDB.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View viewClicked,
					int position, long idInDB) {
				startAlertPrompt(idInDB);
				return false;
			}
		});
	}
	
	private void startAlertPrompt(final long idInDB) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity());
		alertDialogBuilder
			.setTitle(R.string.alert_deleteformula_title)
			.setMessage(R.string.alert_deleteformula_subtitle)
			.setCancelable(false)
			.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					deleteFormula(idInDB);
					populateViewFromDB(); // refresh the UI
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
	
	private void deleteFormula(long idInDB) {
		myDB.deleteRow(idInDB);
	}
}


