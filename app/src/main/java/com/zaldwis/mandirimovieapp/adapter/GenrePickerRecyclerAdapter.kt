package com.zaldwis.mandirimovieapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.zaldwis.mandirimovieapp.R
import com.zaldwis.mandirimovieapp.models.GenreData
import kotlinx.android.synthetic.main.row_genre_picker.view.*

class GenrePickerRecyclerAdapter(val items: ArrayList<GenreData>, val context: Context) : RecyclerView.Adapter<GenrePickerRecyclerAdapter.ViewHolder>()  {
    private val broadcaster: LocalBroadcastManager = LocalBroadcastManager.getInstance(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_genre_picker, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPos = items[position]

        holder.textGenre?.text = itemPos.name
        holder.textGenre?.setOnClickListener{
            val i2 = Intent("genre_broadcast")
            i2.putExtra("genre", itemPos.name)
            broadcaster.sendBroadcast(i2)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!){
        val textGenre = view?.textGenrePicker
    }
}