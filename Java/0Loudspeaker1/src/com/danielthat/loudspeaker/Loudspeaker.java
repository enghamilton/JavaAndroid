package com.danielthat.loudspeaker;

import java.util.LinkedHashMap;
import java.util.Map;

import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
public class Loudspeaker extends Activity {
 
	// http://danielthat.blogspot.com/2013/06/android-make-phone-call-with-speaker-on.html
	
 Button mButton;
 EditText mEdit;
 TextView textAsync;
 TelephonyManager manager;
 StatePhoneReceiver myPhoneStateListener;
 boolean callFromApp=false; // To control the call has been made from the application
 boolean callFromOffHook=false; // To control the change to idle state is from the app call
 
 public static Map<String, Integer> map = new LinkedHashMap<String, Integer>();
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.layout_loudspeaker);
 
  map.put("bairro Jd Beatriz SBC",43904355);
  map.put("Rua Brasilio Machado, bairro : centro SBC",43397436);
  //map.put("Rua Brasilio Machado",43349859);
  //map.put("Avenida Robert Kennedy",45940239);
  
  //To be notified of changes of the phone state create an instance
  //of the TelephonyManager class and the StatePhoneReceiver class
  myPhoneStateListener = new StatePhoneReceiver(this);
  manager = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE));
 
  mEdit   = (EditText)findViewById(R.id.editText1);
      
  textAsync = (TextView)findViewById(R.id.textView2);
  
  mButton = (Button) findViewById(R.id.button1);
  mButton.setOnClickListener(new View.OnClickListener() {
   
   public void onClick(View v) {
   
    String phoneNumber = mEdit.getText().toString();
    manager.listen(myPhoneStateListener,
    PhoneStateListener.LISTEN_CALL_STATE); // start listening to the phone changes
    callFromApp=true;
    Intent i = new Intent(android.content.Intent.ACTION_CALL,
                          Uri.parse("tel:+" + phoneNumber)); // Make the call       
    startActivity(i);
    
    Integer timeInMills = 20000;
	//SystemClock.sleep(timeInMills);
	try {
		Thread.sleep(timeInMills);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    new outgoingCallAsyncTask().execute();
    
   } 
  }); 
 }
 
 
 // Monitor for changes to the state of the phone
 public class StatePhoneReceiver extends PhoneStateListener {
     Context context;
     public StatePhoneReceiver(Context context) {
         this.context = context;
     }
 
     @Override
     public void onCallStateChanged(int state, String incomingNumber) {
         super.onCallStateChanged(state, incomingNumber);
         
         switch (state) {
         
         case TelephonyManager.CALL_STATE_OFFHOOK: //Call is established
          if (callFromApp) {
              callFromApp=false;
              callFromOffHook=true;
                   
              try {
                Thread.sleep(500); // Delay 0,5 seconds to handle better turning on loudspeaker
              } catch (InterruptedException e) {
              }
           
              //Activate loudspeaker
              AudioManager audioManager = (AudioManager)
                                          getSystemService(Context.AUDIO_SERVICE);
              audioManager.setMode(AudioManager.MODE_IN_CALL);
              audioManager.setSpeakerphoneOn(true);
           }
           break;
         
        case TelephonyManager.CALL_STATE_IDLE: //Call is finished
          if (callFromOffHook) {
                callFromOffHook=false;
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setMode(AudioManager.MODE_NORMAL); //Deactivate loudspeaker
                manager.listen(myPhoneStateListener, // Remove listener
                      PhoneStateListener.LISTEN_NONE);
             }
          break;
         }
     }
 }
 
 public class outgoingCallAsyncTask extends AsyncTask<String,Integer,Void>{

		@Override
		protected Void doInBackground(String... arg0) {
			 
			for(Map.Entry<String, Integer> entry : map.entrySet()) {
			    final String key = entry.getKey();
			    final Integer value = entry.getValue();		
			    
			    Intent callIntent = new Intent(Intent.ACTION_CALL);
			 
			    String dialPhoneNumber = value.toString();
				callIntent.setData(Uri.parse("tel:"+dialPhoneNumber));
				startActivity(callIntent);
			    			
				runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                 	textAsync.setText(key+" cel.: "+value.toString());
                 }
             });
				// espera 10s para enviar o play, senão vai 2x playplay por causa dos map.put() map.put()
				/*
				Integer timeInMills = 10000;
				*/
				// wait 25 seconds to next for loop iteration
				
				Integer timeInMills = 25000;
				//SystemClock.sleep(timeInMills);
				try {
					Thread.sleep(timeInMills);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (isCancelled()) break;

			}
			return null;
		}
		
	}
 
 
}