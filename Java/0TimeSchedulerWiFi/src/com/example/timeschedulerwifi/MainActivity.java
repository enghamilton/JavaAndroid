package com.example.timeschedulerwifi;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	WifiManager wm;	
	Timer timer = new Timer();
	// url www.coders-hub.com/2013/10/how-to-enable-and-disable-wifi-in.html#.XAZKnttKjIU
  @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

  @Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	
	wm=(WifiManager)getSystemService(WIFI_SERVICE);
	//wm.setWifiEnabled(true);
	wm.setWifiEnabled(false);
	timer.cancel();
}

@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	
	/*
	wm=(WifiManager)getSystemService(WIFI_SERVICE);
	wm.setWifiEnabled(true);
	*/
	//wm.setWifiEnabled(false);
	
	Calendar today = Calendar.getInstance();
	today.set(Calendar.HOUR_OF_DAY, 11);
	today.set(Calendar.MINUTE, 0);
	today.set(Calendar.SECOND, 0);

	int interval = 10000;  // intervalo de 6 seg.
	  timer.schedule(new TimerTask() {
		        public void run() {
		        	wm=(WifiManager)getSystemService(WIFI_SERVICE);
		        	wm.setWifiEnabled(true);
		    }
	  }, today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
	
}

@Override
  protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   setContentView(R.layout.activity_main);
  }
  //this method will call on button click
  public void wwf(View v)
  {
   Button b1=(Button)findViewById(R.id.button1);
   //get Wifi service
   wm=(WifiManager)getSystemService(WIFI_SERVICE);
   //Check Wifi is on or off
   if(wm.isWifiEnabled())
   {
     b1.setText("Wifi ON");
     //enable or disable Wifi
     //for enable pass true value
     //for disable pass false value
     wm.setWifiEnabled(false);
   }
   else
   {
     b1.setText("Wifi OFF");
     wm.setWifiEnabled(true);
    }
  }
}