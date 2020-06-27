package com.zaldwis.mandirimovieapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaldwis.androidfundamental1.Services.ApiMain
import com.zaldwis.mandirimovieapp.adapter.VideoRecyclerAdapter
import com.zaldwis.mandirimovieapp.models.ResponseVideo
import kotlinx.android.synthetic.main.fragment_review.progress_circular
import kotlinx.android.synthetic.main.fragment_video.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoFragment : Fragment() {
    private lateinit var recyclerAdapter: VideoRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getVideos()

        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    private fun getVideos() {
        ApiMain().services.getVideos(MovieActivity.MOVIE_ID, getString(R.string.token_api), "en-US")
            .enqueue(object : Callback<ResponseVideo> {
                override fun onResponse(
                    call: Call<ResponseVideo>,
                    response: Response<ResponseVideo>
                ) {
                    if (response.code() == 200) {
                        response.body()?.resultVideo.let {
                            recyclerVideo?.layoutManager = LinearLayoutManager(activity)
                            recyclerAdapter =
                                VideoRecyclerAdapter(
                                    it,
                                    activity
                                )
                            recyclerVideo?.adapter = recyclerAdapter
                            progress_circular.visibility = View.GONE
                        }
                    }else progress_circular.visibility = View.GONE
                }

                override fun onFailure(call: Call<ResponseVideo>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_LONG)
                    progress_circular.visibility = View.GONE
                }
            })
    }
}