package com.ndroidstudios.android.formulawizard;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ndroidstudios.android.helper.FontHelper;
 
/**
 * A very simple application to handle Voice Recognition intents
 * and display the results
 */
public class VoiceFragment extends SherlockFragment {
 
    private static final int REQUEST_CODE = 1234;
 
    /**
     * Called with the activity is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
    		Bundle savedInstanceState) {
    	
    	View rootView = inflater.inflate(R.layout.voice_recog, container, false);
    	
    	// Override the font of the header text
        final TextView voice_prompt = (TextView) rootView.findViewById(R.id.voice_prompt);
        FontHelper.overrideFonts(this.getActivity(), voice_prompt);
        voice_prompt.setText(R.string.voice_prompt);   
    	
    	// Set up voice recognition button and set on-click listener
        rootView.findViewById(R.id.voice_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    	startVoiceRecognitionActivity();
                    	//voice_prompt.setText("The button was clicked! The button was clicked! The button was clicked! The button was clicked! The button was clicked!");
                    }
                });
        
    	return rootView;
    }
 
    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
        startActivityForResult(intent, REQUEST_CODE);
    }
 
    /**
     * Handle the results from the voice recognition activity.
     */
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // TODO
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

