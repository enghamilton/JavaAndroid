package com.example.mvc_java_android_retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

	@GET("products/")
	Call<Repo> listRepository(@Path("products") String name);
	
	/*
	@GET("products/")
	Observable<List<Repo>> listRepos(@Path("name") String name);
	*/
	
	/*
	@GET("products/")
    Call<List<Repo>> loadChanges(@Query("q") String status);
    */
    
}