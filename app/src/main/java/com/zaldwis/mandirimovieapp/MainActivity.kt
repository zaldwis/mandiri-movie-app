package com.zaldwis.mandirimovieapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaldwis.androidfundamental1.Services.ApiMain
import com.zaldwis.mandirimovieapp.adapter.GenrePickerRecyclerAdapter
import com.zaldwis.mandirimovieapp.adapter.GenreRecyclerAdapter
import com.zaldwis.mandirimovieapp.models.GenreData
import com.zaldwis.mandirimovieapp.models.ResponseData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerAdapter: GenreRecyclerAdapter
    private lateinit var pickerAdapter: GenrePickerRecyclerAdapter
    private lateinit var loading: AlertDialog
    private lateinit var dialog: AlertDialog
    private lateinit var genre: ArrayList<GenreData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAllGenres()

        textGenre.setOnClickListener {
            setGenreDialog()
        }
    }

    private fun getAllGenres() {
        loadingDialog()
        ApiMain().services.getAllGenres(getString(R.string.token_api), "en-US")
            .enqueue(object : Callback<ResponseData> {
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    if (response.code() == 200) {
                        response.body()?.genreData?.let {
                            recyclerGenre?.layoutManager = LinearLayoutManager(this@MainActivity)
                            recyclerAdapter =
                                GenreRecyclerAdapter(
                                    it,
                                    this@MainActivity
                                )
                            recyclerGenre?.adapter = recyclerAdapter
                        }
                        genre = response.body()?.genreData!!
                        loading.dismiss()
                    } else loading.dismiss()
                }

                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG)
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

    private fun setGenreDialog() {
        val builder =
            AlertDialog.Builder(
                this,
                R.style.myFullscreenAlertDialogStyle
            )
        val view1: View = layoutInflater.inflate(R.layout.genre_dialog, null)

        builder.setCancelable(true)
        builder.setView(view1)
        dialog = builder.create()
        val textAllGenre =
            view1.findViewById<TextView>(R.id.textAllGenre)
        val recyclerView: RecyclerView = view1.findViewById(R.id.recyclerPickGenre)

        textAllGenre.setOnClickListener {
            textGenre.text = "All Genres"
            recyclerAdapter.getFilter()?.filter("")
            dialog.dismiss()
        }

        genre.let {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            pickerAdapter =
                GenrePickerRecyclerAdapter(
                    it,
                    this@MainActivity
                )
            recyclerView.adapter = pickerAdapter
        }


        if (dialog.window != null) dialog.window?.attributes?.windowAnimations =
            R.style.FadeInOutDialog
        dialog.show()
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            mMessageReceiver,
            IntentFilter("genre_broadcast")
        )
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            var genre = intent.getStringExtra("genre")
            if (genre!=null){
                textGenre.text = genre
                recyclerAdapter.getFilter()?.filter(genre)
                dialog.dismiss()
            }
        }
    }
}