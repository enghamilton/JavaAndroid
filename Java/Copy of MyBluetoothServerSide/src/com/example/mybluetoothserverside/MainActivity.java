package com.example.mybluetoothserverside;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager ;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.Iterator;
import java.util.LinkedHashMap;



import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
 
 private static final int REQUEST_ENABLE_BT = 1;
 public static String play_audio = "play";
 
 Intent callIntent;
 
 BluetoothAdapter bluetoothAdapter;
 
 ArrayList<BluetoothDevice> pairedDeviceArrayList;
 
 TextView textInfo, textStatus, textAsync;
 ListView listViewPairedDevice;
 ArrayAdapter<BluetoothDevice> pairedDeviceAdapter;
 private UUID myUUID;
 
 LinearLayout inputPane;
 EditText inputField;
 Button btnSend;
 
 ThreadConnectBTdevice myThreadConnectBTdevice;
 ThreadConnected myThreadConnected;
 
 TelephonyManager myTelephonyManager;
 
 private static int lastState = TelephonyManager.CALL_STATE_IDLE;
 private static Date callStartTime;
 private static boolean isIncoming;
 private static String savedNumber;
 
 public static Map<String, Integer> map = new LinkedHashMap<String, Integer>();
 private AsyncTask<String, Integer, Void> asyncTask;
 private String PREFS_CALL_ACTION = "IntentActionCalledLast";

 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

  textInfo = (TextView)findViewById(R.id.info);
  textStatus = (TextView)findViewById(R.id.status);
  textAsync = (TextView)findViewById(R.id.async);
  listViewPairedDevice = (ListView)findViewById(R.id.pairedlist);
  
  inputPane = (LinearLayout)findViewById(R.id.inputpane);
  inputField = (EditText)findViewById(R.id.input);
  
  myTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
  
  SharedPreferences setting = getSharedPreferences(PREFS_CALL_ACTION, 0);
  String sharedToast = Integer.toString(setting.getInt("restartFromLastCalled", 0));
  
  Toast.makeText(MainActivity.this, sharedToast, Toast.LENGTH_SHORT).show();
  
  map.put("call 1 of 4",43904355);
  map.put("call 2 of 4",43397436);
  map.put("call 3 of 4",43349859);
  map.put("call 4 of 4",45940239);
  map.put("last call(er) next will works",977519520);
  map.put("ELENO JOSE DA SILVA",971123033);
  map.put("MARIANGELA RUSSO",999372088);
  map.put("SONIA VALERIO DA COSTA",992987793);
  map.put("MILTON CUANO",983145716);
  map.put("FRANCISCO MARTINS GALHARDO FILHO",998041684);
  //map.put("bauducco call 3 of 3",08007011599);
  //map.put("positivo call 3 of 3",0800 644-7500);
  //map.put("djarun call 3 of 3",0800 726 6666);
  //map.put("danone call 3 of 3",0800 7017561);
  //map.put("lexus call 3 of 3",0800 5398727);//LEXUSBR
  //map.put("volkswagen call 3 of 3",0800 019 5775);
  //map.put("itaucard call 3 of 3",3003 3030);
  
  /*
  map.put("bairro Jd Beatriz SBC",43904355);
  map.put("Rua Brasilio Machado, bairro : centro SBC",43397436);
  map.put("Rua Brasilio Machado",43349859);
  map.put("Avenida Robert Kennedy",45940239);
  map.put("error",977519520);
  map.put("TIM 11 SP",958919315);
  */
  
  if(sharedToast != "0"){
	  for(Iterator<Entry<String, Integer>> it = map.entrySet().iterator(); it.hasNext(); ) {
	      Entry<String, Integer> entry = it.next();
	      if(entry.getValue().equals(Integer.parseInt(sharedToast))) {
	        it.remove();
	        break;
	      } else {
	    	  it.remove();
	      }
	    }
	  /*
	   for(Map.Entry<String, Integer> entry : map.entrySet()) {
		    final String key = entry.getKey();
		    final Integer value = entry.getValue();
		    if(value.toString() != sharedToast) {
		    	//map.remove(key);
		    } else {
		    	//map.remove(key);
		    	//break;
		    }
	  }
	  */
  }
  
  btnSend = (Button)findViewById(R.id.send);
  btnSend.setOnClickListener(new OnClickListener(){

   @Override
   public void onClick(View v) {
	   asyncTask = new outgoingCallAsyncTask();
	   //outgoingCallAsyncTask asyncTask = new outgoingCallAsyncTask();
	   asyncTask.execute();
	   /*
	   if(myThreadConnected!=null){
		   byte[] bytesToSend = play_audio.getBytes();
		   myThreadConnected.write(bytesToSend);
		   Intent callIntent = new Intent(Intent.ACTION_CALL);
		   String dialPhoneNumber = inputField.getText().toString(); 
		   callIntent.setData(Uri.parse("tel:"+dialPhoneNumber));
		   startActivity(callIntent);
	   }
	   */
	   /*
	   for(Map.Entry<String, Integer> entry : map.entrySet()) {
		    String key = entry.getKey();
		    Integer value = entry.getValue();
		
		    Intent callIntent = new Intent(Intent.ACTION_CALL);
		 
			String dialPhoneNumber = value.toString();
			callIntent.setData(Uri.parse("tel:"+dialPhoneNumber));
			startActivity(callIntent);
			Integer timeInMills = 10000;
			SystemClock.sleep(timeInMills);
		}
		*/
	   /*
	   Intent callIntent = new Intent(Intent.ACTION_CALL);
	   String dialPhoneNumber = inputField.getText().toString();
	   callIntent.setData(Uri.parse("tel:"+dialPhoneNumber));
	   startActivity(callIntent);
	   */
   }});
  
  if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)){
   Toast.makeText(this, 
    "FEATURE_BLUETOOTH NOT support", 
    Toast.LENGTH_LONG).show();
            finish();
            return;
  }

  //generate UUID on web: http://www.famkruithof.net/uuid/uuidgen
  //have to match the UUID on the another device of the BT connection
  myUUID = UUID.fromString("ec79da00-853f-11e4-b4a9-0800200c9a66");
  
  bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
  if (bluetoothAdapter == null) {
   Toast.makeText(this, 
    "Bluetooth is not supported on this hardware platform", 
    Toast.LENGTH_LONG).show();
            finish();
            return;
  }
  
  String stInfo = bluetoothAdapter.getName() + "\n" +
    bluetoothAdapter.getAddress();
  textInfo.setText(stInfo);
  
 }

 @Override
 protected void onStart() {
  super.onStart();
  
  //Turn ON BlueTooth if it is OFF
  if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
  
  setup();
 }
 
 private void setup() {
  Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
  if (pairedDevices.size() > 0) {
   pairedDeviceArrayList = new ArrayList<BluetoothDevice>();
   
   for (BluetoothDevice device : pairedDevices) {
    pairedDeviceArrayList.add(device);
            }
   
   pairedDeviceAdapter = new ArrayAdapter<BluetoothDevice>(this,
                 android.R.layout.simple_list_item_1, pairedDeviceArrayList);
   listViewPairedDevice.setAdapter(pairedDeviceAdapter);
   
   listViewPairedDevice.setOnItemClickListener(new OnItemClickListener(){

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
      int position, long id) {
     BluetoothDevice device = 
       (BluetoothDevice)parent.getItemAtPosition(position);
      Toast.makeText(MainActivity.this, 
       "Name: " + device.getName() + "\n"
       + "Address: " + device.getAddress() + "\n"
       + "BondState: " + device.getBondState() + "\n"
       + "BluetoothClass: " + device.getBluetoothClass() + "\n"
       + "Class: " + device.getClass(),
       Toast.LENGTH_LONG).show();
      
      textStatus.setText("start ThreadConnectBTdevice");
      myThreadConnectBTdevice = new ThreadConnectBTdevice(device);
      myThreadConnectBTdevice.start();
    }});
  }
 }

 @Override
 protected void onDestroy() {
  super.onDestroy();
  
  if(myThreadConnectBTdevice!=null){
   myThreadConnectBTdevice.cancel();
  }
 }

 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  
  if(requestCode==REQUEST_ENABLE_BT){
   if(resultCode == Activity.RESULT_OK){
    setup();
   }else{
    Toast.makeText(this, 
     "BlueTooth NOT enabled", 
     Toast.LENGTH_SHORT).show();
             finish();
   }
  } 
 }
 
 private class ThreadConnectBTdevice extends Thread {
  private BluetoothSocket bluetoothSocket = null;
        private final BluetoothDevice bluetoothDevice;
        
        
        public ThreadConnectBTdevice(BluetoothDevice device) {
         bluetoothDevice = device;

         try {
    bluetoothSocket = device.createRfcommSocketToServiceRecord(myUUID);
    textStatus.setText("bluetoothSocket: \n" + bluetoothSocket);
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
        }

  @Override
  public void run() {
   boolean success = false;
   try {
    bluetoothSocket.connect();
    success = true;
   } catch (IOException e) {
    e.printStackTrace();
    
    final String eMessage = e.getMessage();
    runOnUiThread(new Runnable(){

     @Override
     public void run() {
      textStatus.setText("something wrong bluetoothSocket.connect(): \n" + eMessage);
     }});
    
    try {
     bluetoothSocket.close();
    } catch (IOException e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
    }
   }
   
   if(success){
    //connect successful
    final String msgconnected = "connect successful:\n"
     + "BluetoothSocket: " + bluetoothSocket + "\n"
     + "BluetoothDevice: " + bluetoothDevice;
    
    runOnUiThread(new Runnable(){

     @Override
     public void run() {
      textStatus.setText(msgconnected);
      
      listViewPairedDevice.setVisibility(View.GONE);
      inputPane.setVisibility(View.VISIBLE);
     }});
    
    startThreadConnected(bluetoothSocket);
   }else{
    //fail
   }
  }
  
  public void cancel() {
   
   Toast.makeText(getApplicationContext(), 
     "close bluetoothSocket", 
     Toast.LENGTH_LONG).show();
   
   try {
    bluetoothSocket.close();
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
   
        }
 }
 
 private void startThreadConnected(BluetoothSocket socket){
  
  myThreadConnected = new ThreadConnected(socket);
  myThreadConnected.start();
 }
 
 private class ThreadConnected extends Thread {
  private final BluetoothSocket connectedBluetoothSocket;
        private final InputStream connectedInputStream;
        private final OutputStream connectedOutputStream;
  
  public ThreadConnected(BluetoothSocket socket) {
   connectedBluetoothSocket = socket;
   InputStream in = null;
            OutputStream out = null;
            
            try {
    in = socket.getInputStream();
    out = socket.getOutputStream();
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
            
            connectedInputStream = in;
            connectedOutputStream = out;
  }

  @Override
  public void run() {
   byte[] buffer = new byte[1024];
            int bytes;
            
            while (true) {
             try {
     bytes = connectedInputStream.read(buffer);
     String strReceived = new String(buffer, 0, bytes);
     final String msgReceived = String.valueOf(bytes) + 
       " bytes received:\n" 
       + strReceived;
     
     runOnUiThread(new Runnable(){

      @Override
      public void run() {
       textStatus.setText(msgReceived);
      }});
     
     if(1==1){
    	 asyncTask.cancel(true);
     }
    } catch (IOException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     
     final String msgConnectionLost = "Connection lost:\n"
       + e.getMessage();
     runOnUiThread(new Runnable(){

      @Override
      public void run() {
       textStatus.setText(msgConnectionLost);
      }});
    }
            }
  }
  
  public void write(byte[] buffer) {
   try {
    connectedOutputStream.write(buffer);
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
        }
  
  public void cancel() {
   try {
    connectedBluetoothSocket.close();
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
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
			    
			    SharedPreferences setting = getSharedPreferences(PREFS_CALL_ACTION, 0);
			    
			    // apagar as 2 linhas e usa o broadcastreceiver pra mandar o play
			    //to usando esse aqui quando não manda argumento ACTION_CALL			   
			    
			    byte[] bytesToSend = play_audio.getBytes();
				myThreadConnected.write(bytesToSend);
				
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
				// wait 20 seconds to next for loop iteration
				
				Integer timeInMills = 40000;//20 seconds = 20.000 milliseconds
				//SystemClock.sleep(timeInMills);
				
				try {
					Thread.sleep(timeInMills);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//sendSMS(value.toString());
				/*
				String phone_Num = value.toString();
		        String send_msg = "Apto novo R$815.000 para : "+value.toString();
		        try {
		            SmsManager sms = SmsManager.getDefault();  // using android SmsManager
		            sms.sendTextMessage(phone_Num, null, send_msg, null, null);  // adding number and text 
		        } catch (Exception e) {
		            Toast.makeText(getBaseContext(), "Sms not Send", Toast.LENGTH_SHORT).show();
		            e.printStackTrace();
		        }
		        */
				SharedPreferences.Editor editor = setting.edit();
				editor.putInt("restartFromLastCalled", value);
			    editor.commit();
				
				if (isCancelled()) break;
				
			}
			return null;
		}
 		
 	}
 	 
}