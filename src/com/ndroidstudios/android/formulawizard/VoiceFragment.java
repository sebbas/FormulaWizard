package com.ndroidstudios.android.formulawizard;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ndroidstudios.android.helper.FontHelper;
import com.ndroidstudios.android.helper.WolframTask;
import com.ndroidstudios.android.helper.VoiceHelper;
 
/**
 * A very simple application to handle Voice Recognition intents
 * and display the results
 */
public class VoiceFragment extends SherlockFragment {
	
	private TextView mVoicePrompt;
	private TextView mVoicerResult;
	private ProgressBar mProgressBar;
	private TextView mMoreText;
	private LinearLayout mResultInfo;
	private LinearLayout mMoreInfo;
	private ImageView mWolframIcon;
	private VoiceHelper voiceHelper;
    private static final int REQUEST_CODE = 1234;
 
    /**
     * Called with the activity is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
    		Bundle savedInstanceState) {
    	super.onSaveInstanceState(savedInstanceState);
    	View rootView = inflater.inflate(R.layout.voice_recog, container, false);
    	
    	// Find all the views in the layout
    	mVoicePrompt = (TextView) rootView.findViewById(R.id.voice_prompt);
    	mVoicerResult = (TextView) rootView.findViewById(R.id.result);
    	mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar1);
    	mMoreText = (TextView) rootView.findViewById(R.id.more_text);
    	mResultInfo = (LinearLayout) rootView.findViewById(R.id.result_layout);
    	mMoreInfo = (LinearLayout) rootView.findViewById(R.id.more_info);
    	mWolframIcon = (ImageView) rootView.findViewById(R.id.wolfram_icon);
        
        // Override the font of the header text
        FontHelper.overrideFonts(this.getActivity(), mVoicePrompt);
        FontHelper.overrideFonts(this.getActivity(), mVoicerResult);
        FontHelper.overrideFonts(this.getActivity(), mMoreText);
        
        mVoicePrompt.setText(R.string.voice_prompt); 
    	
    	// Set up voice recognition button and set on-click listener
        rootView.findViewById(R.id.voice_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    	//startVoiceRecognitionActivity();
                    	testOnActivityResult();
                    }
                });      
    	return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	voiceHelper = new VoiceHelper(getActivity());
    }
 
    private void startVoiceRecognitionActivity() {
    	if (voiceHelper.isNetworkConnected()) {
    		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
            startActivityForResult(intent, REQUEST_CODE);
    	} else {
    		mVoicePrompt.setText(this.getResources().getString(R.string.connection_error));
    	}
    }
 
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			ArrayList<String> query = data.getStringArrayListExtra(
	                RecognizerIntent.EXTRA_RESULTS);
			setQueryText(query);
			WolframTask wolframTask = new WolframTask(mVoicerResult, mProgressBar,
					mResultInfo, mMoreInfo, mWolframIcon, this.getActivity());
			wolframTask.execute(listToString(query));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    private void setQueryText(ArrayList<String> query) {
		mVoicePrompt.setText(listToString(query));
    }
      
    public static String listToString(List<String> list) {
        String result = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            result += " " + list.get(i);
        }
        return result;
    }
       
  
    public void testOnActivityResult() {
    	ArrayList<String> wordList = new ArrayList<String>();
		wordList.add("adfsa");
		wordList.add("fsadf");
		wordList.add("fsdf");
		wordList.add("sfa");
		wordList.add("fasdf");
		wordList.add("sdf");
		wordList.add("fsf?");	
		wordList.add("dsf?");
		
		String query = listToString(wordList);
		//mVoiceResult.setText("bgienrgorbgoerignieeeeeeeeeeeeeehrwoefioregnoi4nfgirnfirnfirenooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		setQueryText(wordList);
		WolframTask wolframTask = new WolframTask(mVoicerResult, mProgressBar, 
				mResultInfo, mMoreInfo, mWolframIcon, this.getActivity());
		wolframTask.execute(query);
    }
}

