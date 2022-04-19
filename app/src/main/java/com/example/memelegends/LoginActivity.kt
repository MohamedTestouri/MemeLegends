package com.example.memelegends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.memelegends.entities.User
import com.example.memelegends.utils.Statics
import com.example.memelegends.utils.UserInterface
import com.example.memelegends.utils.UserResponse
import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class LoginActivity : AppCompatActivity() {

    private var Emailtxt: EditText? = null
    private var Pwtxt: EditText? = null
    private var forgetpw: Button? =null
    private  var loginbtn : Button? = null
    private  var googlelogin: Button? = null
    private var signbtn : Button? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Emailtxt = findViewById(R.id.Emailtxt)
        Pwtxt = findViewById(R.id.Pwtxt)
        forgetpw = findViewById(R.id.forgetpw)
        loginbtn = findViewById(R.id.loginbtn)
        googlelogin = findViewById(R.id.googlelogin)
        signbtn = findViewById(R.id.signbtn)
        val service = Retrofit.Builder()
            .baseUrl(Statics.getBaseURL())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserInterface::class.java)

        loginbtn?.setOnClickListener{
            val user =  User(Emailtxt?.text.toString(), Pwtxt?.text.toString())
            service.login(user).enqueue(object: Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("TAG_", "An error happened!"+t)
                    t.printStackTrace()
                    Toast.makeText(applicationContext, "User doesn't exist", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    /* This will print the response of the network call to the Logcat */
                    Log.d("TAG_", response.code().toString())
if (response.code() == 200) {
    Handler(Looper.getMainLooper()).postDelayed({
        val mainIntent = Intent(applicationContext, NewsPage::class.java)
        startActivity(mainIntent)
        finish()
    }, 1000)
} else Toast.makeText(applicationContext, "User doesn't exist", Toast.LENGTH_LONG).show()
                }
            })
        }

        signbtn?.setOnClickListener{
            Handler(Looper.getMainLooper()).postDelayed({
                val mainIntent = Intent(this, Register::class.java)
                startActivity(mainIntent)
                finish()
            }, 1000)
        }

    }
}