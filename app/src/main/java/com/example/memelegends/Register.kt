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

class Register : AppCompatActivity() {


    private var backbtn: Button?= null
    private var registerButton: Button?= null
    private var passwordTextInput: EditText?= null
    private var rePasswordTextInput: EditText?= null
    private var emailTextInput: EditText?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        backbtn= findViewById(R.id.backbtn)
        registerButton = findViewById(R.id.registerButton)
        passwordTextInput = findViewById(R.id.passwordTextInput)
        rePasswordTextInput = findViewById(R.id.rePasswordTextInput)
        emailTextInput = findViewById(R.id.emailTextInput)

        val service = Retrofit.Builder()
            .baseUrl(Statics.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserInterface::class.java)

        registerButton?.setOnClickListener{
            if (isEmailValid(emailTextInput?.text.toString())){
            if (passwordTextInput?.text.toString().equals(rePasswordTextInput?.text.toString())){
                // register Ws
             //
val user =  User(emailTextInput?.text.toString(), passwordTextInput?.text.toString())
                    service.register(user).enqueue(object: Callback<UserResponse>{
                        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                            Log.d("TAG_", "An error happened!"+t)
                            t.printStackTrace()
                            Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                        }
                        override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                            /* This will print the response of the network call to the Logcat */
                            Log.d("TAG_", response.code().toString())
                            if (response.code()==200)
                                startActivity(Intent(applicationContext, LoginActivity::class.java))
                            else Toast.makeText(applicationContext, "User exists", Toast.LENGTH_LONG).show()
                        }
                    })
            }
            else Toast.makeText(this, "Password doesn't match", Toast.LENGTH_LONG).show()
            }else Toast.makeText(this, "Email is invalid", Toast.LENGTH_LONG).show()
        }
        backbtn?.setOnClickListener{
            Handler(Looper.getMainLooper()).postDelayed({
                val mainIntent = Intent(this, LoginActivity::class.java)
                startActivity(mainIntent)
                finish()
            }, 1000)
        }
    }
    private fun isEmailValid(email: String): Boolean {
        val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
        return EMAIL_REGEX.toRegex().matches(email);
    }

}
//data class UserResponse(val results: List<User>)