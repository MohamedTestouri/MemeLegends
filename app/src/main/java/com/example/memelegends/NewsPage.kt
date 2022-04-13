package com.example.memelegends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button

class NewsPage : AppCompatActivity() {


    private var profilebtn: Button? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_page)


        profilebtn = findViewById(R.id.profilebtn)

        profilebtn?.setOnClickListener{
            Handler(Looper.getMainLooper()).postDelayed({
                val mainIntent = Intent(this, Profile::class.java)
                startActivity(mainIntent)
                finish()
            }, 1000)
        }
    }
}