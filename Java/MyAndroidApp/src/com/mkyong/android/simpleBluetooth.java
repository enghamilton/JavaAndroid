package com.mkyong.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.util.Log;

public class simpleBluetooth {

	private OutputStream outputStream;
	private InputStream inStream;
	private int position = 1;

	@SuppressWarnings("unused")
	private void init() throws IOException {
	    BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
	    if (blueAdapter != null) {
	        if (blueAdapter.isEnabled()) {
	            Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();

	            if(bondedDevices.size() > 0) {
	                Object[] devices = (Object []) bondedDevices.toArray();
	                BluetoothDevice device = (BluetoothDevice) devices[position];
	                ParcelUuid[] uuids = device.getUuids();
	                BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
	                socket.connect();
	                outputStream = socket.getOutputStream();
	                inStream = socket.getInputStream();
	            }

	            Log.e("error", "No appropriate paired devices.");
	        } else {
	            Log.e("error", "Bluetooth is disabled.");
	        }
	    }
	}

	public void write(String s) throws IOException {
	    outputStream.write(s.getBytes());
	}

	public void run() {
	    final int BUFFER_SIZE = 1024;
	    byte[] buffer = new byte[BUFFER_SIZE];
	    int bytes = 0;
	    int b = BUFFER_SIZE;

	    while (true) {
	        try {
	            bytes = inStream.read(buffer, bytes, BUFFER_SIZE - bytes);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	
}
