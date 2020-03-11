package com.example.view;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.example.controller.Controller;

public class FirstActivity extends Activity {

	//https: //www.devmedia.com.br/android-mvc-criando-um-framework-model-view-controller-para-android/29924
	
	private Controller controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.controller = new Controller();
		
	}

}
