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
import com.example.memelegends.entities.Meme
import com.example.memelegends.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CreateMeme : AppCompatActivity() {

    private var back2btn: Button?= null
    private var createButton: Button?= null
    private var memeTextEditText: EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meme)
        val service = Retrofit.Builder()
            .baseUrl(Statics.getBaseURL())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MemeInterface::class.java)

        memeTextEditText = findViewById(R.id.memeTextEditText)
        createButton = findViewById(R.id.createButton)
        back2btn= findViewById(R.id.back2btn)

createButton?.setOnClickListener{
    val meme = Meme(memeTextEditText?.text.toString(), "Med", "image")
service.createMeme(meme).enqueue(object: Callback<MemeResponse>{
    override fun onFailure(call: Call<MemeResponse>, t: Throwable) {
        Toast.makeText(applicationContext, "Error: "+t.toString(), Toast.LENGTH_LONG).show()
    }
    override fun onResponse(call: Call<MemeResponse>, response: Response<MemeResponse>) {
        if (response.code()==200){
            Toast.makeText(applicationContext, "Meme create", Toast.LENGTH_LONG).show()
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(applicationContext, NewsPage::class.java))
                finish()
            }, 1000)
        }else Toast.makeText(applicationContext, "Cannot create", Toast.LENGTH_LONG).show()
    }
})
}
        back2btn?.setOnClickListener{
            Handler(Looper.getMainLooper()).postDelayed({
                val mainIntent = Intent(this, Profile::class.java)
                startActivity(mainIntent)
                finish()
            }, 1000)
        }
    }
}

