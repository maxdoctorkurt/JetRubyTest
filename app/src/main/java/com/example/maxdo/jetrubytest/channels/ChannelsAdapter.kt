package com.example.maxdo.jetrubytest.channels

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maxdo.jetrubytest.core.entities.Source
import android.view.LayoutInflater
import android.widget.TextView
import com.example.maxdo.jetrubytest.R
import io.reactivex.subjects.PublishSubject

class ChannelsAdapter(private val context: Context,  val clickSubject: PublishSubject<Source>) : RecyclerView.Adapter<ChannelsAdapter.ViewHolder>() {

    private var data: MutableList<Source> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.channel_row, parent, false)
        return ViewHolder(view)
    }

    fun setData(data: List<Source>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val source = data[position]
        holder.channelName.text = source.name
        holder.description.text = source.description
        holder.clickablePlace.setOnClickListener {
            clickSubject.onNext(source)
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var description: TextView = view.findViewById(R.id.channelDescription)
        var channelName: TextView = view.findViewById(R.id.channelName)
        var clickablePlace: View = view.findViewById(R.id.clickablePlace)
    }
}