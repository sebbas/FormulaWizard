package com.ndroidstudios.android.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ndroidstudios.android.formulawizard.R;
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
    private TextView mResultText;
    private ProgressBar mProgressBar;
    private LinearLayout mResultInfo;
    private LinearLayout mMoreInfo;
    private ImageView mWolframIcon;
    private WAQuery mQuery;
    private Context context;

    public WolframTask (TextView resultText, ProgressBar progressBar, LinearLayout mResultInfo,
    		LinearLayout mMoreInfo, ImageView mWolframIcon, Context context) {
    	this.mResultText = resultText;
    	this.mProgressBar = progressBar;
    	this.mResultInfo = mResultInfo;
    	this.mMoreInfo = mMoreInfo;
    	this.mWolframIcon = mWolframIcon;
    	this.context = context;
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
        mMoreInfo.setVisibility(View.VISIBLE);
        mResultText.setText(Html.fromHtml(result));
        mResultText.setMovementMethod(LinkMovementMethod.getInstance());
        mProgressBar.setVisibility(View.GONE);
        setIconLink();
    }
    
    @Override
    protected void onPreExecute() {
    	super.onPreExecute();
    	mMoreInfo.setVisibility(View.GONE);
    	mResultInfo.setVisibility(View.VISIBLE);
    	mResultText.setText(context.getResources().getString(R.string.analyze));
    	mProgressBar.setVisibility(View.VISIBLE);
    }
    
    private void setIconLink() {
    	
        mWolframIcon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Create the URL to the actual Wolfram Alpha results webpage
				String queryWebsiteURL = mQuery.toWebsiteURL();
		        System.out.println("Website is: " + queryWebsiteURL);
		        
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.addCategory(Intent.CATEGORY_BROWSABLE);
				intent.setData(Uri.parse(queryWebsiteURL));
				context.startActivity(intent);
			}
		});
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
        engine.addPodIndex(2);

        // Create the query.
        mQuery = engine.createQuery();
        
        // Set properties of the query.
        mQuery.setInput(input);
        
        try {
            // For educational purposes, print out the URL we are about to send:
            System.out.println("Query URL:");
            System.out.println(engine.toURL(mQuery));
            System.out.println("");
            
            // This sends the URL to the Wolfram|Alpha server, gets the XML result
            // and parses it into an object hierarchy held by the WAQueryResult object.
            WAQueryResult queryResult = engine.performQuery(mQuery);
            
            if (queryResult.isError()) {
                System.out.println("Query error");
                System.out.println("  error code: " + queryResult.getErrorCode());
                System.out.println("  error message: " + queryResult.getErrorMessage());
                output = context.getResources().getString(R.string.query_error);
            } else if (!queryResult.isSuccess()) {
                System.out.println("Query was not understood; no results available.");
                output = context.getResources().getString(R.string.query_notunderstood);
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
                                    output = pod.getTitle() + " = " + ((WAPlainText) element).getText();
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
        return defineOutput(output);
    }
    
    private String defineOutput(String output) {
    	String result = "";
    	if (output != "") {
    		result = output;
        } else {
        	result = context.getResources().getString(R.string.no_result);
        }
    	return result;
    }
}
