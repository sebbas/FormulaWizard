package com.ndroidstudios.android.helper;

import java.util.ArrayList;
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
	
	// Replace unicode characters since they cannot be handled by the processing library
	public static String[] replaceUnicode(String[] stringArray) {
		for (int i = 0; i < stringArray.length; i++) {
			stringArray[i] = stringArray[i]
					.replace("\u00F7", "/")
					.replace("\u2215", "/")
					.replace("\u2044", "/")
					.replace("\u00D7", "*")
					.replace("\u2212", "-")
					.replace("\u221A", "sqrt")
					.replace("\u03A0", "pi")
					.replace("\u03C0", "pi")
					.replace("\u1D70B", "pi")
					.replace("\u1D6D1", "pi")
					
					.replace("\u00BC", "(1/4)")
					.replace("\u00BD", "(1/2)")
					.replace("\u00BE", "(3/4)")
					.replace("\u2153", "(1/3)")
					.replace("\u2154", "(2/3)")
					.replace("\u2155", "(1/5)")
					.replace("\u2156", "(2/5)")
					.replace("\u2157", "(3/5)")
					.replace("\u2158", "(4/5)")
					.replace("\u2159", "(1/6)")
					.replace("\u215A", "(5/6)")
					.replace("\u215B", "(1/8)")
					.replace("\u215C", "(3/8)")
					.replace("\u215D", "(5/8)")
					.replace("\u215E", "(7/8)")
					
					.replace("\u2070", "^0")
					.replace("\u00B9", "^1")
					.replace("\u00B2", "^2")
					.replace("\u00B3", "^3")
					.replace("\u2074", "^4")
					.replace("\u2075", "^5")
					.replace("\u2076", "^6")
					.replace("\u2077", "^7")
					.replace("\u2078", "^8")
					.replace("\u2079", "^9");
					
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
