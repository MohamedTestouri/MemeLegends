package com.example.memelegends

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.memelegends.entities.Meme
import com.example.memelegends.utils.MemeInterface
import com.example.memelegends.utils.MemeResponse
import com.example.memelegends.utils.Statics
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Array

class NewsPage : AppCompatActivity() {


    private var profilebtn: Button? =null
    private var memeListView: ListView? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_page)

memeListView = findViewById(R.id.memeListView)
        profilebtn = findViewById(R.id.profilebtn)
        val service = Retrofit.Builder()
            .baseUrl(Statics.getBaseURL())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MemeInterface::class.java)
        var data = ArrayList<Meme>()
        service.getAll().enqueue(object: Callback<MemeResponse> {
            override fun onFailure(call: Call<MemeResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Error: "+t.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<MemeResponse>, response: Response<MemeResponse>) {
                if (response.code()==200){
                    Toast.makeText(applicationContext, "get Meme: "+response.body(), Toast.LENGTH_LONG).show()
                }else Toast.makeText(applicationContext, "Cannot get", Toast.LENGTH_LONG).show()
            }})
      /*  var data = arrayOf("test", "test2")
var arrayAdapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.simple_item, data)
       */

        data.add(Meme("Test", "Test", "https://images.unsplash.com/photo-1453728013993-6d66e9c9123a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8dmlld3xlbnwwfHwwfHw%3D&w=1000&q=80"))
        memeListView?.adapter = CustomAdapter(applicationContext,  data)
        profilebtn?.setOnClickListener{
            Handler(Looper.getMainLooper()).postDelayed({
                val mainIntent = Intent(this, Profile::class.java)
                startActivity(mainIntent)
                finish()
            }, 1000)
        }
    }
}

class CustomAdapter(context: Context, data: ArrayList<Meme> = ArrayList()) : BaseAdapter() {
    private val context : Context
    private var data : ArrayList<Meme> = ArrayList()
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    init {
        this.data = data
        this.context = context
    }
    override fun getCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        //return imageView
        val rowView = inflater.inflate(R.layout.simple_item, viewGroup, false)
        val imageView = rowView.findViewById(R.id.memeImageView) as ImageView
        val textView = rowView.findViewById(R.id.memeTextView) as TextView
        val meme = getItem(position) as Meme
        textView.text = meme.text
        Picasso.with(context)
          //  .load(Statics.BASE_URL+"/"+meme.image)
            .load(meme.image)
            .into(imageView);
        return rowView
    }

}
