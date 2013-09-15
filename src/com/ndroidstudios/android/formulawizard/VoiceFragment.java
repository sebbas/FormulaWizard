package com.ndroidstudios.android.formulawizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.ndroidstudios.android.helper.FontHelper;
import com.ndroidstudios.android.helper.VoiceHelper;
import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;
 
/**
 * A very simple application to handle Voice Recognition intents
 * and display the results
 */
public class VoiceFragment extends SherlockFragment {

	private static final String KEY_PROMPT_TEXT = "prompt_text";
	private static final String KEY_RESULT_TEXT = "result_text";
	private static final String KEY_PROGRESSBAR_VISIBILITY = "progressbar_visibility";
	private static final String KEY_MORELAYOUT_VISIBILITY = "more_visibility";
	
	private TextView mVoicePrompt;
	private TextView mVoiceResult;
	private ProgressBar mProgressBar;
	private LinearLayout mMoreInfo;
	private ImageView mWolframIcon;
	private VoiceHelper voiceHelper;
    private static final int REQUEST_CODE = 1234;
    private Bundle mSavedInstanceState; // Since onRetainInstance does not support savedInstanceState, 
    									//	this is an alternative

	/**
     * Called with the activity is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
    		Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.voice_recog, container, false);
    	  	
    	// Find all the views in the layout
    	mVoicePrompt = (TextView) rootView.findViewById(R.id.voice_prompt);
    	mVoiceResult = (TextView) rootView.findViewById(R.id.result);
    	mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar1);
    	mMoreInfo = (LinearLayout) rootView.findViewById(R.id.more_info);
    	mWolframIcon = (ImageView) rootView.findViewById(R.id.wolfram_icon);
        
        // Override the font of the header text
        FontHelper.overrideFonts(this.getActivity(), rootView.findViewById(R.id.voice_prompt));
        FontHelper.overrideFonts(this.getActivity(), rootView.findViewById(R.id.more_text));
        FontHelper.overrideFonts(this.getActivity(), rootView.findViewById(R.id.result));
    	         
        setRetainInstance(true);
        
    	// Set up voice recognition button and set on-click listener
        rootView.findViewById(R.id.voice_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    	startVoiceRecognitionActivity();
                    	//testOnActivityResult();
                    }
                });  
        this.setHasOptionsMenu(true);
    	return rootView;
    }

    /*
     * TODO Implement the "share formula" functionality
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	   super.onCreateOptionsMenu(menu, inflater);
	   inflater.inflate(R.menu.menu_about, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId()) {
		case R.id.menu_about: 
			return true;
		default: 	
			return super.onOptionsItemSelected(item);
		}
	}*/
	
    // Alternate implementation with instance variable (mSavedInstanceState). Reason: setRetainInstance() was used in onCreateView()
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	mSavedInstanceState = new Bundle();
        mSavedInstanceState.putString(KEY_PROMPT_TEXT, mVoicePrompt.getText().toString());
        mSavedInstanceState.putString(KEY_RESULT_TEXT, mVoiceResult.getText().toString());
        mSavedInstanceState.putInt(KEY_PROGRESSBAR_VISIBILITY, mProgressBar.getVisibility());
        mSavedInstanceState.putInt(KEY_MORELAYOUT_VISIBILITY, mMoreInfo.getVisibility());
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	voiceHelper = new VoiceHelper(getActivity());
    	
    	if (mSavedInstanceState != null) {
    		mVoicePrompt.setText(mSavedInstanceState.getString(KEY_PROMPT_TEXT));
    		mVoiceResult.setText(mSavedInstanceState.getString(KEY_RESULT_TEXT));
    		mProgressBar.setVisibility(mSavedInstanceState.getInt(KEY_PROGRESSBAR_VISIBILITY));
    		mMoreInfo.setVisibility(mSavedInstanceState.getInt(KEY_MORELAYOUT_VISIBILITY));
    		if (!voiceHelper.isNetworkConnected()) {
    			mVoiceResult.setVisibility(View.GONE);
    		}
    	} else {
    		mVoicePrompt.setText(R.string.voice_prompt);
    	}
    }
 
    private void startVoiceRecognitionActivity() {
    	if (voiceHelper.isNetworkConnected()) {
    		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ask the wizard a question ...");
            startActivityForResult(intent, REQUEST_CODE);
    	} else {
    		mVoicePrompt.setText(this.getResources().getString(R.string.connection_error));
    		mVoiceResult.setVisibility(View.GONE);
    	}
    }
 
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			ArrayList<String> query = data.getStringArrayListExtra(
	                RecognizerIntent.EXTRA_RESULTS);
			setQueryText(query.get(0));
			WolframTask wolframTask = new WolframTask();
			wolframTask.execute(query.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    private void setQueryText(String query) {
    	String output = query.substring(0, 1).toUpperCase(Locale.getDefault()) + query.substring(1) + "?";  // Make 1st word capital
		mVoicePrompt.setText(output);
    }
      
    public static <E> String listToString(List<E> list) {
        String result = (String) list.get(0);
        for (int i = 1; i < list.size(); i++) {
            result += " " + list.get(i);
        }
        return result;
    }
       
    public void testOnActivityResult() {
    	ArrayList<String> wordList = new ArrayList<String>();
		wordList.add("What");
		wordList.add("is");
		wordList.add("the");
		wordList.add("volume");
		wordList.add("of");
		wordList.add("the");
		wordList.add("earth?");

		String query = listToString(wordList);
		setQueryText(wordList.get(0));
		WolframTask wolframTask = new WolframTask();
		wolframTask.execute(query);
    }
    
    private class WolframTask extends AsyncTask<String, Void, String> {

        private static final String appid = "OMITTED";
        private String output;
        private WAQuery mQuery;

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
            mVoiceResult.setText(Html.fromHtml(result));
            mVoiceResult.setMovementMethod(LinkMovementMethod.getInstance());
            mProgressBar.setVisibility(View.GONE);
            setIconLink();
        }
        
        @Override
        protected void onPreExecute() {
        	super.onPreExecute();
        	mMoreInfo.setVisibility(View.GONE);
        	mVoiceResult.setVisibility(View.VISIBLE);
        	mVoiceResult.setText(getResources().getString(R.string.analyze));
        	mProgressBar.setVisibility(View.VISIBLE);
        }
        
        private void setIconLink() {	
            mWolframIcon.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View v) {
    				// Create the URL to the actual Wolfram Alpha results webpage
    				String queryWebsiteURL = mQuery.toWebsiteURL();

    				Intent intent = new Intent();
    				intent.setAction(Intent.ACTION_VIEW);
    				intent.addCategory(Intent.CATEGORY_BROWSABLE);
    				intent.setData(Uri.parse(queryWebsiteURL));
    				startActivity(intent);
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
                    output = getResources().getString(R.string.query_error);
                } else if (!queryResult.isSuccess()) {
                    System.out.println("Query was not understood; no results available.");
                    output = getResources().getString(R.string.query_notunderstood);
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
            	result = getResources().getString(R.string.no_result);
            }
        	return result;
        }
    }
}


