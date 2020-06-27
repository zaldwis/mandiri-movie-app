package com.zaldwis.mandirimovieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaldwis.androidfundamental1.Services.ApiMain
import com.zaldwis.mandirimovieapp.R
import com.zaldwis.mandirimovieapp.models.GenreData
import com.zaldwis.mandirimovieapp.models.ResponseData2
import kotlinx.android.synthetic.main.row_genre.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenreRecyclerAdapter(val items: ArrayList<GenreData>, val context: Context) : RecyclerView.Adapter<GenreRecyclerAdapter.ViewHolder>()  {
    private var itemsFiltered : ArrayList<GenreData> = items
    private lateinit var recyclerAdapter: MovieRecyclerAdapter



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_genre, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pageNumber = 1
        val itemPos = itemsFiltered[position]

        holder.textGenre?.text = itemPos.name
        getMovies(itemPos.id,pageNumber,holder)
    }

    override fun getItemCount(): Int {
        return itemsFiltered.size
    }

    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!){
        val textGenre = view?.textGenre
        val recyclerMovie = view?.recyclerMovie
    }

    fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    itemsFiltered = items
                } else {
                    val filteredList: ArrayList<GenreData> =
                        java.util.ArrayList<GenreData>()
                    for (row in items) {

                        if (row.name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    itemsFiltered = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = itemsFiltered
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                itemsFiltered = filterResults.values as ArrayList<GenreData>
                notifyDataSetChanged()
            }
        }
    }

    private fun getMovies(idGenre: Int, pageNumb: Int, holder: ViewHolder){
        ApiMain().services.getMoviebyGenre(context.getString(R.string.token_api), idGenre,pageNumb,"popularity.desc",false,true,"en-US")
            .enqueue(object : Callback<ResponseData2> {
                override fun onResponse(call: Call<ResponseData2>, response: Response<ResponseData2>) {
                    if (response.code() == 200) {
                        response.body()?.let {
                            holder.recyclerMovie?.setHasFixedSize(true)
                            holder.recyclerMovie?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                            recyclerAdapter =
                                MovieRecyclerAdapter(
                                    it,
                                    context,
                                    idGenre
                                )
                            holder.recyclerMovie?.adapter = recyclerAdapter
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseData2>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG)
                }
            })
    }
}


