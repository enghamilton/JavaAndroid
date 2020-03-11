package com.example.mybluetoothclientside;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.RemoteException;
import android.speech.SpeechRecognizer;
import android.speech.RecognitionService;
import android.widget.Toast;
/**
 * 
 * >>> https://android.googlesource.com/platform/development/+/master/samples/VoiceRecognitionService/src/com/example/android/voicerecognitionservice/VoiceRecognitionService.java?autodive=0%2F%2F%2F
 * 
 * https://www.simplifiedcoding.net/android-speech-to-text-tutorial/
 * 
 * https://www.techjini.com/blog/android-speech-to-text-tutorial-part1/
 * 
 * A sample implementation of a {@link RecognitionService}. This very simple implementation does
 * no actual voice recognition. It just immediately returns fake recognition results.
 * Depending on the setting chosen in {@link VoiceRecognitionSettings}, it either returns a
 * list of letters ("a", "b", "c"), or a list of numbers ("1", "2", "3").
 */
public class VoiceRecognitionService extends RecognitionService {
    
	// The name of the SharedPreferences file we'll store preferences in.
    public static final String SHARED_PREFERENCES_NAME = "VoiceRecognitionService";
    
    // The key to the preference for the type of results to show (letters or numbers).
    // Identical to the value specified in res/values/strings.xml.
    public static final String PREF_KEY_RESULTS_TYPE = "results_type";
    
    // The values of the preferences for the type of results to show (letters or numbers).
    // Identical to the values specified in res/values/strings.xml.
    public static final int RESULT_TYPE_LETTERS = 0;
    public static final int RESULT_TYPE_NUMBERS = 1;
	
	@Override
    protected void onCancel(Callback listener) {
        // A real recognizer would do something to shut down recognition here.
    }
    @Override
    protected void onStartListening(Intent recognizerIntent, Callback listener) {
        // A real recognizer would probably utilize a lot of the other listener callback
        // methods. But we'll just skip all that and pretend we've got a result.
        ArrayList<String> results = new ArrayList<String>();
        
        SharedPreferences prefs = getSharedPreferences(
                SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        
        String resultType = prefs.getString(
                PREF_KEY_RESULTS_TYPE,
                String.valueOf(RESULT_TYPE_LETTERS));
        int resultTypeInt = Integer.parseInt(resultType);
        
        if (resultTypeInt == RESULT_TYPE_LETTERS) {
            results.add("a");
            results.add("b");
            results.add("c");            
        } else if (resultTypeInt == RESULT_TYPE_NUMBERS) {
            results.add("1");
            results.add("2");
            results.add("3");
        }
        
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION, results);
        
        try {
            listener.results(bundle);
            Toast.makeText(getBaseContext(), listener.toString(), Toast.LENGTH_LONG).show();
            
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onStopListening(Callback listener) {
        // Not implemented - in this sample we assume recognition would be endpointed
        // automatically, though certain applications may wish to expose an affordance
        // for stopping recording manually.	
        
    }
    
}