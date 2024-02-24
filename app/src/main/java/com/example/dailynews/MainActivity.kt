package com.example.dailynews

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity() {

    lateinit var madapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchData()
        madapter = NewsAdapter()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val recyclerView = findViewById<RecyclerView>(R.id.newsList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = madapter
    }

    private fun fetchData () {
        val url =
            "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=f50aa88de84745b1b08fc3e73a4f0c96"

        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val newsArticles = response.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsArticles.length()) {
                    val newsItem = newsArticles.getJSONObject(i)
                    val news = News(
                        newsItem.getString("title"),
                        newsItem.getString("author"),
                        newsItem.getString("url"),
                        newsItem.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                Log.d("news array size", newsArray.size.toString())
                madapter.updateNews(newsArray)
            },
            Response.ErrorListener { error ->
                error.localizedMessage?.let { Log.e("Api error", it) }
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"]="Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}