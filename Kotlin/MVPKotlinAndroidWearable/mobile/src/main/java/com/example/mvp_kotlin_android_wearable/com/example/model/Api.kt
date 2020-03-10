package com.example.mvp_kotlin_android_wearable.com.example.model

import retrofit2.Call
import retrofit2.http.GET

interface Api {
    //urls
    @get:GET("users?q=rokano")
    val users: Call<UsersList>
}