package com.example.controller;

import com.example.model.Model;

import android.os.AsyncTask;

public class Controller extends AsyncTask {

	public Model model;
	
	@Override
	protected Object doInBackground(Object... arg0) {
		
		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		
		this.model = new Model();
		this.model.id = 0;
		this.model.username = "";
		this.model.phone = "";
		this.model.price = "";
		
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
	}
	
}
