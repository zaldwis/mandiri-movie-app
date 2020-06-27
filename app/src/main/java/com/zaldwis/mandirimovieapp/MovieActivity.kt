package com.zaldwis.mandirimovieapp

import MyPagerAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.zaldwis.androidfundamental1.Services.ApiMain
import com.zaldwis.mandirimovieapp.helpers.MethodHelper
import com.zaldwis.mandirimovieapp.models.ResponseMovie
import kotlinx.android.synthetic.main.activity_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieActivity : AppCompatActivity() {
    private lateinit var loading: AlertDialog

    companion object {
        var MOVIE_ID = 0
        const val EXTRA_ID = "movie_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        MOVIE_ID = intent.getIntExtra(EXTRA_ID, 0)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        tabLayoutInit()

        backButton.setOnClickListener{
            onBackPressed()
        }

        getMovieDetail()
    }

    private fun tabLayoutInit() {
        viewPager?.adapter = MyPagerAdapter(supportFragmentManager)
        tabLayout?.setupWithViewPager(viewPager)

        val headerView = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.custom_tablayout, null, false)

        val linearLayoutOne = headerView.findViewById(R.id.linear1) as LinearLayout
        val linearLayout2 = headerView.findViewById(R.id.linear2) as LinearLayout
        val textReview = headerView.findViewById(R.id.textReview) as TextView
        val textMovie = headerView.findViewById(R.id.textMovie) as TextView
        val imageReview = headerView.findViewById(R.id.imageReview) as ImageView
        val imageMovie = headerView.findViewById(R.id.imageMovie) as ImageView

        tabLayout?.getTabAt(0)?.setCustomView(linearLayoutOne)
        tabLayout?.getTabAt(1)?.setCustomView(linearLayout2)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0){
                    textReview.setTextColor(resources.getColor(R.color.colorWhite))
                    imageReview.setImageResource(R.drawable.ic_baseline_rate_review_30)
                }else{
                    textMovie.setTextColor(resources.getColor(R.color.colorWhite))
                    imageMovie.setImageResource(R.drawable.ic_baseline_local_movies_30)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                if (tab.position == 0){
                    textReview.setTextColor(resources.getColor(R.color.colorGrey))
                    imageReview.setImageResource(R.drawable.ic_baseline_rate_review_30_grey)

                }else{
                    textMovie.setTextColor(resources.getColor(R.color.colorGrey))
                    imageMovie.setImageResource(R.drawable.ic_baseline_local_movies_30_grey)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
    }

    private fun getMovieDetail() {
        loadingDialog()
        ApiMain().services.getMovieDetail( MOVIE_ID,getString(R.string.token_api),"en-US")
            .enqueue(object : Callback<ResponseMovie> {
                override fun onResponse(
                    call: Call<ResponseMovie>,
                    response: Response<ResponseMovie>
                ) {
                    if (response.code() == 200) {
                        val bodyData = response.body()

                        val requestOptionBackdrop = RequestOptions()
                            .placeholder(R.drawable.ic_tmdblong)
                        imageBackdrop?.let {
                            Glide.with(this@MovieActivity)
                                .load("https://image.tmdb.org/t/p/original${bodyData?.backdrop_path}")
                                .thumbnail(
                                    Glide.with(this@MovieActivity)
                                    .load("https://image.tmdb.org/t/p/w500${bodyData?.backdrop_path}")
                                    .apply(requestOptionBackdrop))
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .apply(requestOptionBackdrop)
                                .fitCenter()
                                .into(it)
                        }

                        val requestOptionPoster = RequestOptions()
                            .placeholder(R.drawable.ic_tmdbshort)
                        imagePoster?.let {
                            Glide.with(this@MovieActivity)
                                .load("https://image.tmdb.org/t/p/original${bodyData?.poster_path}")
                                .thumbnail(
                                    Glide.with(this@MovieActivity)
                                        .load("https://image.tmdb.org/t/p/w500${bodyData?.poster_path}")
                                        .apply(requestOptionPoster))
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .apply(requestOptionPoster)
                                .into(it)
                        }

                        textTitle.text = bodyData?.title
                        textYearAndRuntime.text = "${MethodHelper.formatDate(bodyData?.release_date,"yyyy")}  |  ${bodyData?.runtime} Minutes"

                        var valueGenre = ""
                        for (i in 0..(bodyData?.genreData?.size?.minus(1)!!)){
                            if (i < bodyData.genreData.size.minus(1)){
                                valueGenre = valueGenre + bodyData.genreData[i].name + " · "
                            }else{
                                valueGenre += bodyData.genreData[i].name
                            }
                        }
                        textGenres.text = valueGenre

                        textOverview.text = bodyData.overview
                        
                        loading.dismiss()
                    } else loading.dismiss()
                }

                override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                    Toast.makeText(this@MovieActivity, t.message, Toast.LENGTH_LONG)
                }
            })
    }

    private fun loadingDialog() {
        val builder =
            AlertDialog.Builder(
                this,
                R.style.CustomAlertDialogLoading
            )
        val view1: View = layoutInflater.inflate(R.layout.layout_loading, null)
        builder.setCancelable(true)
        builder.setView(view1)
        loading = builder.create()
        loading.show()
    }
}