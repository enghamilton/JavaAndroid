package com.example.mvp_kotlin_android_wearable
package com.example.mvp_kotlin_android_wearable.com.example.model

import com.example.mvp_kotlin_android_wearable.com.example.model.UsersList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.common.api.Api

import retrofit2.Callback
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val BASE_URL:String = "http://javarestjson.herokuapp.com/api/produtos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadSQL()
    }

    // function to call server and update ui
    fun loadSQL() {
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var api = retrofit.create(Api::class.java)
        var call = api.users
        call.enqueue(object : Callback&lt; UsersList&gt; {

            override fun onResponse(call: Call&lt;UsersList&gt;?, response: Response&lt;UsersList&gt;?) {
            var ures = response?.body()
            var user = ures?.users
            var length = user!!.size

            for (i in 0 until length) {
                str = str + "\n" + user.get(i).id + " " + user.get(i).login
            }

            //tv_user!!.text = str
        }

            override fun onFailure(call: Call&lt;UsersList&gt;?, t: Throwable?) {
            Log.v("Error", t.toString())
        }
        })
    }
}
