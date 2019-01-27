package com.example.maxdo.jetrubytest.tabs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.maxdo.jetrubytest.R
import com.example.maxdo.jetrubytest.core.entities.Article
import com.squareup.picasso.Picasso

class NewsAdapter(private val context: Context) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var data: MutableList<Article> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.article_row, parent, false)
        return ViewHolder(view)
    }

    fun setData(data: List<Article>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = data[position]
        holder.title.text = article.title
        holder.content.text = article.content

        if(article.urlToImage != null && article.urlToImage.isNotEmpty())
        Picasso.get().load(article.urlToImage).into(holder.image)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.newsTitle)
        var content: TextView = view.findViewById(R.id.newsDescription)
        var image: ImageView = view.findViewById(R.id.newsImage)
    }
}