package com.zaldwis.mandirimovieapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.zaldwis.androidfundamental1.Services.ApiMain
import com.zaldwis.mandirimovieapp.MovieActivity
import com.zaldwis.mandirimovieapp.R
import com.zaldwis.mandirimovieapp.models.ResponseData2
import kotlinx.android.synthetic.main.row_movie.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieRecyclerAdapter(val items: ResponseData2, val context: Context, val genreID: Int) : RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>()  {
    val itemsData = items.resultMoviebyGenre
    val totalpage = items.total_pages
    private var pageNumb = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_movie, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPos = itemsData[position]

        val requestOption = RequestOptions()
            .placeholder(R.drawable.ic_tmdbshort)
        holder.imageMovie?.let {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/original${itemPos.poster_path}")
                .thumbnail(Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500${itemPos.poster_path}")
                    .apply(requestOption))
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(requestOption)
                .into(it)
        }

        if (position == itemCount - 3){
            if (pageNumb < totalpage){
                pageNumb += 1
                getMovies(genreID,pageNumb)
            }
        }

        holder.imageMovie?.setOnClickListener{
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra(MovieActivity.EXTRA_ID, itemPos.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemsData.size
    }

    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!){
        val imageMovie = view?.imageMovie
    }

    private fun getMovies(idGenre: Int, pageNumb: Int){
        ApiMain().services.getMoviebyGenre(context.getString(R.string.token_api), idGenre,pageNumb,"popularity.desc",false,true,"en-US")
            .enqueue(object : Callback<ResponseData2> {
                override fun onResponse(call: Call<ResponseData2>, response: Response<ResponseData2>) {
                    if (response.code() == 200) {
                        if (response.body()?.resultMoviebyGenre?.size!! > 0){
                            itemsData.addAll(response.body()?.resultMoviebyGenre!!)
                            notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseData2>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG)
                }
            })
    }
}


