package com.ndroidstudios.android.helper;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;

public class WolframTask extends AsyncTask<String, Void, String> {

	// PUT YOUR APPID HERE:
    private static String appid = "OMITTED";
    private String output;
    private TextView resultText;
    private TextView analyzeText;
    private ProgressBar progressBar;

    public WolframTask (TextView resultText, TextView analyzeText, ProgressBar progressBar) {
    	this.resultText = resultText;
    	this.analyzeText = analyzeText;
    	this.progressBar = progressBar;
    }
    
    @Override
	protected String doInBackground(String... args) {
    	for (int i = 0 ; i < args.length; i++) {
			output = this.getResultFromQuery(args[i]); 
		}
    	return output;
	}
    
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        resultText.setVisibility(View.VISIBLE);
        resultText.setText(result);
        analyzeText.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }
    
    @Override
    protected void onPreExecute() {
    	resultText.setVisibility(View.GONE);
    	analyzeText.setVisibility(View.VISIBLE);
    	progressBar.setVisibility(View.VISIBLE);
    	super.onPreExecute();
    }
    
    public String getResultFromQuery(String queryString) {

        String input = queryString;
        
        // The WAEngine is a factory for creating WAQuery objects,
        // and it also used to perform those queries. You can set properties of
        // the WAEngine (such as the desired API output format types) that will
        // be inherited by all WAQuery objects created from it. Most applications
        // will only need to crete one WAEngine object, which is used throughout
        // the life of the application.
        WAEngine engine = new WAEngine();
        
        // These properties will be set in all the WAQuery objects created from this WAEngine.
        engine.setAppID(appid);
        engine.addFormat("plaintext");

        // Create the query.
        WAQuery query = engine.createQuery();
        
        // Set properties of the query.
        query.setInput(input);
        
        try {
            // For educational purposes, print out the URL we are about to send:
            System.out.println("Query URL:");
            System.out.println(engine.toURL(query));
            System.out.println("");
            
            // This sends the URL to the Wolfram|Alpha server, gets the XML result
            // and parses it into an object hierarchy held by the WAQueryResult object.
            WAQueryResult queryResult = engine.performQuery(query);
            
            if (queryResult.isError()) {
                System.out.println("Query error");
                System.out.println("  error code: " + queryResult.getErrorCode());
                System.out.println("  error message: " + queryResult.getErrorMessage());
                output = "Query error";
            } else if (!queryResult.isSuccess()) {
                System.out.println("Query was not understood; no results available.");
                output = "Query was not understood";
            } else {
                // Got a result.
                System.out.println("Successful query. Pods follow:\n");
                for (WAPod pod : queryResult.getPods()) {
                    if (!pod.isError()) {
                        System.out.println(pod.getTitle());
                        System.out.println("------------");
                        String title = pod.getTitle();
                        for (WASubpod subpod : pod.getSubpods()) {
                            for (Object element : subpod.getContents()) {
                                if (element instanceof WAPlainText) {
                                    System.out.println(((WAPlainText) element).getText());
                                    System.out.println("");
                                    if (title.equals("Result")) {
                                    	output = "Result = " + ((WAPlainText) element).getText();
                                    }  
                                }
                            }
                        }
                        System.out.println("");
                    }
                }
                // We ignored many other types of Wolfram|Alpha output, such as warnings, assumptions, etc.
                // These can be obtained by methods of WAQueryResult or objects deeper in the hierarchy.
            }
        } catch (WAException e) {
            e.printStackTrace();
        }
        return output;
    }
}
