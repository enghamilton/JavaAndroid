package com.mkyong.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final int MESSAGE_STATE_CHANGE = 0;
	public static final int MESSAGE_DEVICE_NAME = 0;
	public static final String DEVICE_NAME = null;
	public static final int MESSAGE_TOAST = 0;
	public static final String TOAST = null;
	public static final String TOAST_RUN = "data String bluetooth received : ";
	public static final int MESSAGE_READ = 0;
	public static final int MESSAGE_WRITE = 0;
	final Context context = this;
	private Button button;
	private TextView textView;
	private EditText editTextPhoneNumber;
	private OutputStream outputStream;
	private InputStream inputStream;
	private static final String LOG_ERROR = "Log error : ";
	private static final String LOG_SUCCESS = "Log  success: ";
	public static BluetoothDevice device;
	String readMessage;
	String play_audio = "a";
	String stringReceivedBuffer;
	private UUID MY_UUID;
	private String APP_NAME;
	BluetoothDevice pairedDevice;
	BluetoothAdapter mBluetoothAdapter;
	private static final int REQUEST_ENABLE_BT = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		button = (Button) findViewById(R.id.buttonCall);
		textView = (TextView)findViewById(R.id.TextView01);
		editTextPhoneNumber = (EditText)findViewById(R.id.editTextPhoneNumber);
		
		// add PhoneStateListener
		PhoneCallListener phoneListener = new PhoneCallListener();
		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener,
				PhoneStateListener.LISTEN_CALL_STATE);

		// add button listener
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent callIntent = new Intent(Intent.ACTION_CALL);
				//callIntent.setData(Uri.parse("tel:011959819315"));
				//callIntent.setData(Uri.parse("tel:9090999283277"));
				String dialPhoneNumber = editTextPhoneNumber.getText().toString(); 
				callIntent.setData(Uri.parse("tel:"+dialPhoneNumber));
				startActivity(callIntent);
				new AcceptThread().start();
				init();
				write(play_audio);

			}

		});
		
		MY_UUID = UUID.fromString("ec79da00-853f-11e4-b4a9-0800200c9a66");
		APP_NAME = MY_UUID.toString();

	}	
	
	@Override
	protected void onStart(){
		super.onStart();		  
		  //Turn ON BlueTooth if it is OFF
		  if (!mBluetoothAdapter.isEnabled()) {
		            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		        }
		  
		  setup();

	}
	
	private void setup() {
		  textView.setText("setup()");
		  Thread myThreadBeConnected = new AcceptThread();
		  myThreadBeConnected.start();
	}

	
	private class PhoneCallListener extends PhoneStateListener {
		
		private boolean isPhoneCalling = false;

		String LOG_TAG = "LOG Phone State : ";

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			if (TelephonyManager.CALL_STATE_RINGING == state) {
				//init();
				
			    BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
			    if (blueAdapter != null) {
			        if (blueAdapter.isEnabled()) {
			            Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();

			            if(bondedDevices.size() > 0) {
			                Object[] devices = (Object []) bondedDevices.toArray();
			                //BluetoothDevice device = (BluetoothDevice) devices[0];// api level 5
			                device = (BluetoothDevice) devices[0];//devices[1] no sony xperia devices[0] no Samsung E5
			                new ConnectThread(device).start();
			                try {
								BluetoothSocket socketConnectedThread = device.createRfcommSocketToServiceRecord(MY_UUID);
								new ConnectedThread(socketConnectedThread).start();
							} catch (IOException e) {
								e.printStackTrace();
								Log.e(LOG_ERROR+" ConnectedThread() : ", e.getMessage());
							}
							Log.i(LOG_SUCCESS+" ConnectThread(device) : ", " successed instanciate new ConnectedThread().start()");
			            }
			            //Log.i("error", "No appropriate paired devices.");
			        } else {
			            Log.e("error", "Bluetooth is disabled.");
			        }
			    }
			    
				// phone ringing
				Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
			}

			if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
				// active
				Log.i(LOG_TAG, "OFFHOOK");

				isPhoneCalling = true;
			}

			if (TelephonyManager.CALL_STATE_IDLE == state) {
				// run when class initial and phone call ended, need detect flag
				// from CALL_STATE_OFFHOOK
				Log.i(LOG_TAG, "IDLE");

				if (isPhoneCalling) {

					Log.i(LOG_TAG, "restart app");

					// restart app
					Intent i = getBaseContext().getPackageManager()
							.getLaunchIntentForPackage(
									getBaseContext().getPackageName());
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);

					isPhoneCalling = false;
				}

			}
		}
		
	}
	
	String remoteDeviceName;
	BluetoothDevice remoteDevice;
	BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();

	/*registra a broadcast da seguinte maneira :
	 * phone_state_offhook quando o telefone fica fora do gancho
	 * a ligação (que estava tocando) foi atendida
	 * então registra a broadcast que tem como filtro o estado do Bluetooth
	 * (que deve está previamente acionado nos dois celulares android
	 * Samgung Galaxy E5 e Sony Xperia Miro st23a)
	 * que vai com enviar a String de text "play audio" da
	 * discoveryResult 
	*/
	public class discoveryResult extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			remoteDeviceName = arg1.getStringExtra(BluetoothDevice.EXTRA_NAME);
		    remoteDevice = arg1.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		    Toast.makeText(getApplicationContext(), "Discovered: " + remoteDeviceName + " address " + remoteDevice.getAddress(), Toast.LENGTH_SHORT).show();
		    
		    
		    
		    try{
				BluetoothDevice device = bluetooth.getRemoteDevice(remoteDevice.getAddress());
				Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
	            BluetoothSocket clientSocket =  (BluetoothSocket) m.invoke(device, 1);
	            clientSocket.connect();
	            //DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
	            //clientSock() classe que envia a String "play audio"
	            
		    }catch(Exception e){
		    	e.printStackTrace();
	            Log.e("Log, Bluetooth try/catch block exception message : ", e.getMessage());
		    }
		    
		}
	}
	
	//public void init() throws IOException {
	public void init() {
	    BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
	    if (blueAdapter != null) {
	        if (blueAdapter.isEnabled()) {
	            Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();

	            if(bondedDevices.size() > 0) {
	                Object[] devices = (Object []) bondedDevices.toArray();
	                //BluetoothDevice device = (BluetoothDevice) devices[0];// api level 5
	                device = (BluetoothDevice) devices[0];//devices[1] no sony xperia devices[0] no Samsung E5
	                ParcelUuid[] uuids = device.getUuids();
	                BluetoothSocket socket;
					try {
						
						socket = device.createRfcommSocketToServiceRecord(uuids[1].getUuid());
						/*
						device = bluetooth.getRemoteDevice(remoteDevice.getAddress());
						Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
			            BluetoothSocket clientSocket =  (BluetoothSocket) m.invoke(device, 1);
						*/
						//blueAdapter.cancelDiscovery();
						
						socket.connect();
		                outputStream = socket.getOutputStream();
		                inputStream = socket.getInputStream();
		                Log.i("Log : init()", "success socket connected");
					} catch (IOException e) {
						e.printStackTrace();
						Log.e(LOG_ERROR+" init()",e.getMessage()+" ,device Name : "+device.getName());
					}
	            }
	            //Log.i("error", "No appropriate paired devices.");
	        } else {
	            Log.e("error", "Bluetooth is disabled.");
	        }
	    }
	}

	//public void write(String s) throws IOException {
	public void write(String s) {
		try {
			outputStream.write(s.getBytes());
			Log.i(LOG_SUCCESS, "success writebytes");
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(LOG_ERROR+" write()",e.getMessage());
		}
	}

	public void read() {
	    byte[] buffer = new byte[1024];
	    int bytes;

	    while (true) {
	    	
	        try {
	        
	        	BluetoothSocket readerSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
	        	readerSocket.connect();
	        	InputStream readerInputStream = readerSocket.getInputStream();
	        	
	            bytes = readerInputStream.read(buffer);
	            readMessage = new String(buffer, 0, bytes);
	            stringReceivedBuffer = String.valueOf(bytes);
	            
	            // Send the obtained bytes to the UI Activity via handler
	            Log.i("log run() success : ", "bluetooth read data : "+ stringReceivedBuffer+" , bytes received : "+readMessage);
	            
	            runOnUiThread(new Runnable(){

					@Override
					public void run() {
						textView.setText(stringReceivedBuffer);
					}
				});
	            
	            if(stringReceivedBuffer == play_audio){
	            	Toast.makeText(getApplicationContext(), readMessage, Toast.LENGTH_LONG).show();
	            	AssetFileDescriptor afd = getAssets().openFd("teste.mp3");
	            	MediaPlayer player = new MediaPlayer();
	            	player.setDataSource(afd.getFileDescriptor());
	            	player.prepare();
	            	player.start();
	            }
	            //Toast.makeText(getApplicationContext(), TOAST_RUN, Toast.LENGTH_LONG).show();
	
	        } catch (IOException e) {
	        	Log.e(LOG_ERROR+" run()", e.getMessage());
	            e.printStackTrace();
	            break;
	        }

	    }
	    
	}
	
	public void cancel(){
		try {
			inputStream.close();
        } catch (IOException e) { }

	}
	
	
	private class AcceptThread extends Thread {
        private final BluetoothServerSocket serverSocket;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        
        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            
            try {
            	tmp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(APP_NAME, MY_UUID);
            	Log.i(LOG_SUCCESS+" AcceptThread() : ", " success BluetoothServerSocket");
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.e(LOG_ERROR+"AcceptThread()", ex.getMessage());
            }
            serverSocket = tmp;
        }

        @Override
        public void run() {
            setName("AcceptThread");
            BluetoothSocket socket;
            while (true) {
                try {
                    socket = serverSocket.accept();
                    Log.i(LOG_SUCCESS+" AcceptThread() run() : ", " ");
                } catch (IOException e) {
                	Log.e(LOG_ERROR+" AcceptThread() run() : ", e.getMessage());
                	break;
                }

                // If a connection was accepted
                if (socket != null) {
                	try {
                        socket.close();
                        Log.i(LOG_SUCCESS, "acceptThread serverSocket success close");
                    } catch (IOException e) {
                    	Log.e(LOG_ERROR+" socket == null : ", e.getMessage());
                    }
                }
            }
        }
        
        public void cancel(){
        	try{
        		serverSocket.close();
        	} catch (IOException e){
        		
        	}
        }
	}
	
	private class ConnectThread extends Thread {
	    private final BluetoothSocket mmSocket;
	    private final BluetoothDevice mmDevice;

	    public ConnectThread(BluetoothDevice device) {
	        // Use a temporary object that is later assigned to mmSocket,
	        // because mmSocket is final
	        BluetoothSocket tmp = null;
	        mmDevice = device;

	        // Get a BluetoothSocket to connect with the given BluetoothDevice
	        try {
	            // MY_UUID is the app's UUID string, also used by the server code
	            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
	            Log.i(LOG_SUCCESS+" ConnectThread() : ", " success connect RFcomm");
	        } catch (IOException e) { 
	        	Log.e(LOG_ERROR+" ConnectThread() : ", "");
	        }
	        mmSocket = tmp;
	    }

	    @Override
	    public void run() {
	        // Cancel discovery because it will slow down the connection
	        bluetooth.cancelDiscovery();

	        try {
	            // Connect the device through the socket. This will block
	            // until it succeeds or throws an exception
	        	//BluetoothSocket tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
	        	BluetoothSocket tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
	        	tmp.connect();
	            //outputStream = mmSocket.getOutputStream();
                //inputStream = mmSocket.getInputStream();
                outputStream = tmp.getOutputStream();
                inputStream = tmp.getInputStream();
                Log.i("Log : ConnectThread() run() : ", "success socket connected");                
	        } catch (IOException connectException) {
	            // Unable to connect; close the socket and get out
	        	Log.e(LOG_ERROR+" ConnectThread() run() : ", connectException.getMessage());
	            try {
	                mmSocket.close();
	            } catch (IOException closeException) { }
	            return;
	        }
	        
	        // Do work to manage the connection (in a separate thread)
	        //manageConnectedSocket(mmSocket);
	    }

	    /** Will cancel an in-progress connection, and close the socket */
	    public void cancel() {
	        try {
	            mmSocket.close();
	        } catch (IOException e) { }
	    }
	}
	
	private class ConnectedThread extends Thread {
	    private final BluetoothSocket mmSocket;
	    private final InputStream mmInStream;
	    private final OutputStream mmOutStream;

	    public ConnectedThread(BluetoothSocket socket) {
	        mmSocket = socket;
	        InputStream tmpIn = null;
	        OutputStream tmpOut = null;

	        // Get the input and output streams, using temp objects because
	        // member streams are final
	        try {
	            tmpIn = socket.getInputStream();
	            tmpOut = socket.getOutputStream();
	        } catch (IOException e) { }

	        mmInStream = tmpIn;
	        mmOutStream = tmpOut;
	    }

	    @Override
	    public void run() {
	        byte[] buffer = new byte[1024];  // buffer store for the stream
	        int bytes; // bytes returned from read()

	        // Keep listening to the InputStream until an exception occurs
	        while (true) {
	            try {
	                // Read from the InputStream
	                
	                bytes = mmInStream.read(buffer);
		            readMessage = new String(buffer, 0, bytes);
		            stringReceivedBuffer = String.valueOf(bytes);
		            
		            // Send the obtained bytes to the UI Activity via handler
		            Log.i("log run() success : ", "bluetooth read data : "+ stringReceivedBuffer+" , bytes received : "+readMessage);
		            
		            runOnUiThread(new Runnable(){

						@Override
						public void run() {
							textView.setText(stringReceivedBuffer);
						}
					});
		            
		            if(stringReceivedBuffer == play_audio){
		            	Toast.makeText(getApplicationContext(), readMessage, Toast.LENGTH_LONG).show();
		            	AssetFileDescriptor afd = getAssets().openFd("teste.mp3");
		            	MediaPlayer player = new MediaPlayer();
		            	player.setDataSource(afd.getFileDescriptor());
		            	player.prepare();
		            	player.start();
		            }
	                
	                // Send the obtained bytes to the UI activity
		            /*
	                mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
	                        .sendToTarget();
	                        */
	            } catch (IOException e) {
	                break;
	            }
	        }
	    }

	    /* Call this from the main activity to send data to the remote device */
	    public void write(byte[] bytes) {
	        try {
	            mmOutStream.write(bytes);
	        } catch (IOException e) { }
	    }

	    /* Call this from the main activity to shutdown the connection */
	    public void cancel() {
	        try {
	            mmSocket.close();
	        } catch (IOException e) { }
	    }
	}
	
}