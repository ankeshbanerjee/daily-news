package com.example.dailynews

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var newsItems = ArrayList<News>()
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         lateinit var titleTextView: TextView
         lateinit var imageView: ImageView
         lateinit var authorTextView: TextView
         lateinit var container: ConstraintLayout

        init {
            titleTextView = view.findViewById(R.id.newsTitle)
            imageView = view.findViewById(R.id.news_image)
            authorTextView = view.findViewById(R.id.newsAuthor)
            container = view.findViewById(R.id.container)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_news_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.itemView.context).load(newsItems[position].urlToImage).into(viewHolder.imageView);
        viewHolder.titleTextView.text = newsItems[position].title
        viewHolder.authorTextView.text = newsItems[position].author

        viewHolder.container.setOnClickListener{
            val intent = CustomTabsIntent.Builder()
                .build()
            intent.launchUrl(viewHolder.itemView.context, Uri.parse(newsItems[position].url))
        }
    }

    fun updateNews(updatedNews: ArrayList<News>){
        newsItems.clear()
        newsItems.addAll(updatedNews)
        notifyDataSetChanged()
    }

    override fun getItemCount() = newsItems.size
}