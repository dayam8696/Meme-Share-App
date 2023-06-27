package com.example.memeshare

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memeshare.databinding.ActivityMainBinding
import java.util.Objects

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    var currentImageUrl : String? = null
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)
        setContentView(binding.root)
        loadMeme()

    }
private fun loadMeme(){
binding.progressBar.visibility = View.VISIBLE

    val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET , url , null ,
        Response.Listener { response ->
      currentImageUrl = response.getString("url")
            Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                    TODO("Not yet implemented")
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                   TODO("Not yet implemented")
                }
            }).into(binding.memeImageView)

        },
        Response.ErrorListener {
            Toast.makeText(this, "Somthing went wrong" ,Toast.LENGTH_LONG).show()
        })

// Add the request to the RequestQueue.
   MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


}
    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plan"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey check out this cool Meme I got from Reddit $currentImageUrl")
        val chooser = Intent.createChooser(intent , "Share this meme using")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}