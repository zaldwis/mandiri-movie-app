package com.zaldwis.mandirimovieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.zaldwis.androidfundamental1.Services.ApiMain
import com.zaldwis.mandirimovieapp.MovieActivity
import com.zaldwis.mandirimovieapp.R
import com.zaldwis.mandirimovieapp.models.ResponseReview
import kotlinx.android.synthetic.main.row_review.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewRecyclerAdapter(val items: ResponseReview, val activity: FragmentActivity?, val movieID : Int) : RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder>()  {
    val itemsData = items.resultReview
    val totalpage = items.total_pages
    private var pageNumb = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.row_review, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPos = itemsData[position]

        holder.textAuthor?.text = itemPos.author
        holder.textContent?.text = itemPos.content

        if (position == itemCount - 3){
            if (pageNumb < totalpage){
                pageNumb += 1
                getAllReview(pageNumb)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemsData.size
    }

    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!){
        val textAuthor = view?.textAuthor
        val textContent = view?.textContent
    }

    private fun getAllReview(page : Int) {
        ApiMain().services.getReview(MovieActivity.MOVIE_ID, activity?.resources?.getString(R.string.token_api), "en-US",page)
            .enqueue(object : Callback<ResponseReview> {
                override fun onResponse(
                    call: Call<ResponseReview>,
                    response: Response<ResponseReview>
                ) {
                    if (response.code() == 200) {
                        if (response.body()?.resultReview?.size!! > 0){
                            itemsData.addAll(response.body()?.resultReview!!)
                            notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseReview>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_LONG)
                }
            })
    }
}