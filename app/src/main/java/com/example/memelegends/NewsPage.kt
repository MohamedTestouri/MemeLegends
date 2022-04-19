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

    var data = ArrayList<Meme>()
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

        service.getAll().enqueue(object: Callback<List<Meme>> {
            override fun onFailure(call: Call<List<Meme>>, t: Throwable) {
                Toast.makeText(applicationContext, "Error: "+t.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<List<Meme>>, response: Response<List<Meme>>) {
                if (response.code()==200){
                    Toast.makeText(applicationContext, "get Meme: "+response.body().toString(), Toast.LENGTH_LONG).show()
                   // data = response.body() as ArrayList<Meme>//
                   // copyData(response.body()!!)
                    memeListView?.adapter = CustomAdapter(applicationContext,  response.body()!!)
                }else Toast.makeText(applicationContext, "Cannot get"+response.body().toString(), Toast.LENGTH_LONG).show()
            }})
      /*  var data = arrayOf("test", "test2")
var arrayAdapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.simple_item, data)
       */
Log.d("Body size", data.size.toString())
       // data.add(Meme("Test", "Test", "https://images.unsplash.com/photo-1453728013993-6d66e9c9123a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8dmlld3xlbnwwfHwwfHw%3D&w=1000&q=80"))
       // memeListView?.adapter = CustomAdapter(applicationContext,  data)
        profilebtn?.setOnClickListener{
            Handler(Looper.getMainLooper()).postDelayed({
                val mainIntent = Intent(this, Profile::class.java)
                startActivity(mainIntent)
                finish()
            }, 1000)
        }
    }

    private fun copyData(body: List<Meme>) {
      //  data.addAll(body)
        data = body as ArrayList<Meme>
        Log.d("Body ", data.size.toString())
    }
}

class CustomAdapter(context: Context, listItem: List<Meme>) : BaseAdapter() {
    private val context : Context
    private var data : List<Meme>
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    init {
        this.data = listItem
        this.context = context
    }
    override fun getCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        Log.d("Body data", data[position].toString())
        return data.get(position)
    }

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        //return imageView
        val rowView = inflater.inflate(R.layout.simple_item, viewGroup, false)
        val imageView = rowView.findViewById(R.id.memeImageView) as ImageView
        val textView = rowView.findViewById(R.id.memeTextView) as TextView
        val meme = getItem(position) as Meme
        textView.text = meme.text.toString()
        Picasso.with(context)
            .load(Statics.BASE_URL+"/image/"+meme.image.toString())
           // .load(Statics.BASE_URL+"/image/1645210409355image.png")
            .into(imageView);
        return rowView
    }

}
