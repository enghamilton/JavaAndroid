package com.example.mvp_kotlin_android_wearable.com.example.model

import com.google.gson.annotations.SerializedName

class UsersList {
    @SerializedName("items")
    var users: List<`Users.kt`>? = null
}