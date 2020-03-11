package com.example.mvc_java_android_retrofit;

import io.reactivex.Observable;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.*;

//Retrofit requires at minimum Java 7 or Android 2.3.

//https://www.vogella.com/tutorials/Retrofit/article.html
//https://blog.matheuscastiglioni.com.br/consumindo-web-service-no-android-com-retrofit/
public class MainActivity extends Activity {

	public ApiInterface retrofitApi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Retrofit retrofit = new Retrofit.Builder()
	    .baseUrl("http://javarestjson.herokuapp.com/api")
	    .addConverterFactory(GsonConverterFactory.create())
	    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
	    .build();
		
		retrofitApi = retrofit.create(ApiInterface.class);
		
		//https://www.baeldung.com/retrofit-rxjava
		Call<Repo> call = retrofitApi.listRepository(null);
		call.enqueue(new Callback<Repo>() {
			@Override
			public void onFailure(Call<Repo> arg0, Throwable arg1) {
				
			}

			@Override
			public void onResponse(Call<Repo> arg0, Response<Repo> arg1) {
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
