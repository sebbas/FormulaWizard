package com.ndroidstudios.android.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ndroidstudios.android.formulawizard.R;

public class CustomCalculatorHelper {
	
	public static ArrayList<String> getVariableArray(String formula) {
		ArrayList<String> variableNames = new ArrayList<String>();
		String tmpWord = "";
		for (int i = 0; i < formula.length(); i++) {
			char currentChar = formula.charAt(i);
			if(Character.isLetter(currentChar)) {
				tmpWord = tmpWord + currentChar; // Append letter to temp String
			} else {
				if(tmpWord != "") variableNames.add(tmpWord); // Only add word to list if it is not empty
				tmpWord = ""; // Clear temp String
			}
			// If last character is a letter and temp String is not empty, add that last word to the list
			if(i == formula.length()-1 && tmpWord != "") variableNames.add(tmpWord);
		}
		return variableNames;
	}
	
	public static HashMap<String, Double> getValuesMap(Activity activity) {
		HashMap<String, Double> valuesMap = new HashMap<String, Double>(); // HashMap that maps the variable names to their values	
		LinearLayout parent = (LinearLayout) activity.findViewById(R.id.variable_container);
		
		for (int i = 0; i != parent.getChildCount(); i++) {
			if (parent.getChildAt(i).getId() == R.id.variable_container_item) {
				LinearLayout child = (LinearLayout) parent.findViewById(R.layout.variable_container_item);
				TextView containerItemTextView = (TextView) child.getChildAt(0);
				EditText containerItemEditText = (EditText) child.findViewById(R.id.variable_edit);
				
				String name = containerItemTextView.getText().toString();
				double value = Double.parseDouble(containerItemEditText.getText().toString());
				valuesMap.put(name, value);
			}
		}
		return valuesMap;
	}
	
	public static LinkedList<EditText> getEditTextList(Activity activity) {
		LinkedList<EditText> editTextList = new LinkedList<EditText>(); // List that will hold all EditTexts	
		LinearLayout parent = (LinearLayout) activity.findViewById(R.id.variable_container);
		
		for (int i = 0; i != parent.getChildCount(); i++) {
			if (parent.getChildAt(i).getId() == R.id.variable_container_item) {
				LinearLayout child = (LinearLayout) parent.getChildAt(i);
				EditText containerItemEditText = (EditText) child.findViewById(R.id.variable_edit);
				editTextList.add(i, containerItemEditText);
			}
		}
		return editTextList;
	}
	
	public static double calculateResult(String formula) {
		double result = 0;
		return result;
	}
}
