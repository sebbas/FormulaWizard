package com.ndroidstudios.android.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ndroidstudios.android.formulawizard.R;

public class CustomCalculatorHelper {
	
	private static final String[] functions = {"abs", "acos", "asin", "atan", "cbrt", "ceil", "cos", "cosh",
		"exp", "floor", "log", "sin", "sinh", "sqrt", "tan", "tanh"};
	
	public static final String[] constants = {"pi", "e"};
	
	public static final HashMap<String, Double> constantsMap;
    static {
    	constantsMap = new HashMap<String, Double>();
    	constantsMap.put("pi", 3.14159265359);
    	constantsMap.put("e", 2.71828182845);
    }
	
	public static ArrayList<String> getVariableArray(String formula) {
		ArrayList<String> variableNames = new ArrayList<String>();
		String tmpWord = "";
		for (int i = 0; i < formula.length(); i++) {
			char currentChar = formula.charAt(i);
			if(Character.isLetter(currentChar)) {
				tmpWord = tmpWord + currentChar; // Append letter to temp String
			} else {
				if(tmpWord != "") addVariableToList(variableNames, tmpWord); // Only add word to list if it is not empty
				tmpWord = ""; // Clear temp String
			}
			// If last character is a letter and temp String is not empty, add that last word to the list
			if(i == formula.length()-1 && tmpWord != "") addVariableToList(variableNames, tmpWord);
		}
		return variableNames;
	}
	
	public static String listToString(List<String> variables) {
    	String result = "[";
    	for (String a : variables) {
    		result += a + ", ";    		
    	}
    	return result += "]";
    }
	
	public static String[] splitStringAtEqualSign(String formula) {
		return formula.split("=");
	}
	
	public static String removeWhiteSpaceFromString(String s) {
		return s.replaceAll("\\s+","");
	}
	
	public static String[] replaceUnicode(String[] stringArray) {
		for (int i = 0; i < stringArray.length; i++) {
			stringArray[i] = stringArray[i].replace("\u00F7", "/").replace("\u00D7", "*").replace("\u221A", "sqrt"); 
		}
		return stringArray;
	}
	
	private static void addVariableToList(ArrayList<String> variableNames, String variable) {
		if (!isKeyword(variable) && !variableAlreadyAdded(variableNames, variable)) {
			variableNames.add(variable);
		}
	}
	
	private static boolean isKeyword(String word) {
		word = word.toLowerCase(Locale.getDefault());
		for (String item : functions) {
			if (item.equals(word)) {
				return true;
			}
		}
		for (String item : constants) {
			if (item.equals(word)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean variableAlreadyAdded(ArrayList<String> variableNames, String variable) {
		if (variableNames.contains(variable)) {
			return true;
		}
		return false;
	}
	
	public static HashMap<String, Double> getValuesMap(Activity activity) {
		HashMap<String, Double> valuesMap = new HashMap<String, Double>(); // HashMap that maps the variable names to their values	
		LinearLayout parent = (LinearLayout) activity.findViewById(R.id.variable_container);
		
		for (int i = 0; i != parent.getChildCount(); i++) {
			if (parent.getChildAt(i).getId() == i) {
				LinearLayout child = (LinearLayout) parent.getChildAt(i);
				TextView containerItemTextView = (TextView) child.findViewById(R.id.variable_text);
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
			if (parent.getChildAt(i).getId() == i) {
				LinearLayout child = (LinearLayout) parent.getChildAt(i);
				EditText containerItemEditText = (EditText) child.findViewById(R.id.variable_edit);
				editTextList.add(i, containerItemEditText);
			}
		}
		return editTextList;
	}
}
