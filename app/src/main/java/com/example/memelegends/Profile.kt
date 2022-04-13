package com.example.memelegends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button

class Profile : AppCompatActivity() {

    private var newsbtn: Button? =null
    private var createbtn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        newsbtn = findViewById(R.id.newsbtn)
        createbtn = findViewById(R.id.createbtn)

        newsbtn?.setOnClickListener{
            Handler(Looper.getMainLooper()).postDelayed({
                val mainIntent = Intent(this, NewsPage::class.java)
                startActivity(mainIntent)
                finish()
            }, 1000)
        }



        createbtn?.setOnClickListener{
            Handler(Looper.getMainLooper()).postDelayed({
                val mainIntent = Intent(this, CreateMeme::class.java)
                startActivity(mainIntent)
                finish()
            }, 1000)
        }
    }
}