package com.ndroidstudios.android.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FontHelper {
    
    // Override fonts and set to chalkduster (for entire activity!)
    public static void overrideFonts(final Context context, View... views) {
        try {
        	for (View v : views) {
        		if (v instanceof ViewGroup) {
                    ViewGroup vg = (ViewGroup) v;
                    
                       for (int i = 0; i < vg.getChildCount(); i++) {
                       View child = vg.getChildAt(i);
                       overrideFonts(context, child);
                       }
                       
                  	} else if (v instanceof TextView ) {
                    	((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/chalkboardse.ttc"));
                  	}
        	}
        } catch (Exception e) {
        }
    }
    
    // Override fonts and set to defined font 
    public static void overrideFonts(final Context context, Typeface t, View... views) {
        try {
        	for (View v : views) {
        		if (v instanceof ViewGroup) {
                    ViewGroup vg = (ViewGroup) v;
                    
                       for (int i = 0; i < vg.getChildCount(); i++) {
                       View child = vg.getChildAt(i);
                       overrideFonts(context, child);
                       }
                       
                  	} else if (v instanceof TextView ) {
                    	((TextView) v).setTypeface(t);
                  	}
        	}
        } catch (Exception e) {
        }
    }
}
