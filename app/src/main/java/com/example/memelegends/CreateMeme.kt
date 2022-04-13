package com.example.memelegends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button

class CreateMeme : AppCompatActivity() {

    private var back2btn: Button?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meme)


        back2btn= findViewById(R.id.back2btn)

        back2btn?.setOnClickListener{
            Handler(Looper.getMainLooper()).postDelayed({
                val mainIntent = Intent(this, Profile::class.java)
                startActivity(mainIntent)
                finish()
            }, 1000)
        }
    }
}