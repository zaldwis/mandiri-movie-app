package com.zaldwis.mandirimovieapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaldwis.androidfundamental1.Services.ApiMain
import com.zaldwis.mandirimovieapp.adapter.ReviewRecyclerAdapter
import com.zaldwis.mandirimovieapp.models.ResponseReview
import kotlinx.android.synthetic.main.fragment_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReviewFragment : Fragment() {
    private lateinit var loading: AlertDialog
    private lateinit var recyclerAdapter: ReviewRecyclerAdapter
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getAllReview(currentPage)

        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    private fun getAllReview(page : Int) {
        ApiMain().services.getReview(MovieActivity.MOVIE_ID, getString(R.string.token_api), "en-US",page)
            .enqueue(object : Callback<ResponseReview> {
                override fun onResponse(
                    call: Call<ResponseReview>,
                    response: Response<ResponseReview>
                ) {
                    if (response.code() == 200) {
                        response.body()?.let {
                            recyclerReview?.layoutManager = LinearLayoutManager(activity)
                            recyclerAdapter =
                                ReviewRecyclerAdapter(
                                    it,
                                    activity,
                                    MovieActivity.MOVIE_ID
                                )
                            recyclerReview?.adapter = recyclerAdapter
                            progress_circular.visibility = View.GONE
                        }
                    }else progress_circular.visibility = View.GONE
                }

                override fun onFailure(call: Call<ResponseReview>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_LONG)
                    progress_circular.visibility = View.GONE
                }
            })
    }
}