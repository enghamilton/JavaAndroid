package com.example.speechtotextserverside;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;

public class SecondActivity extends Activity {
	private static MediaPlayer mediaPlayer;
	public static String INTENT_PLAY_AUDIO = "audioPlayPhone";
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.second_activity);
        
        Intent intentGetPhone = new Intent();
        intentGetPhone.getStringExtra(INTENT_PLAY_AUDIO);
        
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
 	 	try {
			//mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/AndRecorder/moov.3gpp");
 	 		mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/recordedCalls/20170728"+intentGetPhone+".mp3");
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 	 	try {
			mediaPlayer.prepare();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 		mediaPlayer.start();
     	
     	try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
     	mediaPlayer.stop();
     	mediaPlayer.release();
     	finish();
    }

}