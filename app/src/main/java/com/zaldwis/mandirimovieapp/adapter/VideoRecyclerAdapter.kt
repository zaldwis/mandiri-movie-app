package com.zaldwis.mandirimovieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.zaldwis.mandirimovieapp.R
import com.zaldwis.mandirimovieapp.models.VideoData
import kotlinx.android.synthetic.main.row_youtube.view.*


class VideoRecyclerAdapter(val items: ArrayList<VideoData>?, val activity: FragmentActivity?) : RecyclerView.Adapter<VideoRecyclerAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.row_youtube, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPos = items?.get(position)
        val requestOption = RequestOptions()
            .placeholder(R.drawable.ic_tmdb_alt_long)
        holder.imageThumbnail?.let {
            if (activity != null) {
                Glide.with(activity)
                    .load("https://img.youtube.com/vi/${itemPos?.key}/0.jpg")
                    .thumbnail(
                        Glide.with(activity)
                            .load("https://img.youtube.com/vi/${itemPos?.key}/0.jpg")
                            .apply(requestOption))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(requestOption)
                    .into(it)
            }
        }

        holder.textTitle?.text = itemPos?.name
        holder.textType?.text = itemPos?.type

        holder.imageThumbnail?.setOnClickListener{
            playVideo(itemPos?.key!!)
        }
    }

    override fun getItemCount(): Int {
        return items?.size!!
    }

    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!){
        val textTitle = view?.textTitle
        val textType = view?.textType
        val imageThumbnail = view?.imageThumbnail
    }

    private fun playVideo(key : String){
        var dialog : AlertDialog
        val builder =
            activity?.let {
                AlertDialog.Builder(
                    it,
                    R.style.CustomAlertDialogVideo
                )
            }
        val view1: View? = activity?.layoutInflater?.inflate(R.layout.layout_video_player, null)

        builder?.setCancelable(true)
        builder?.setView(view1)
        dialog = builder?.create()!!
        val youTubePlayerView: YouTubePlayerView = view1?.findViewById(R.id.youtube_player_view)!!

        activity?.lifecycle?.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(key, 0f)
            }
        })

        if (dialog.window != null) dialog.window?.attributes?.windowAnimations =
            R.style.FadeInOutDialog
        dialog.show()
    }

}