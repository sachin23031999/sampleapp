package com.example.sample.fragments

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sample.INTERVAL
import com.example.sample.R
import com.example.sample.viewModels.MainViewModel
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import kotlinx.android.synthetic.main.custom_controller.*
import kotlinx.android.synthetic.main.fragment_video.*

@Suppress("DEPRECATION")
class VideoFragment : Fragment() {

    private val args: VideoFragmentArgs by navArgs()
    lateinit var viewModel: MainViewModel
    private var simpleExoPlayer: SimpleExoPlayer? = null
    private var orientationFlag = false


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setupToolbar()
        val view = inflater.inflate(R.layout.fragment_video, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPlayer()
        addListener()
        screenChangeListener()
        controlButtonsClick()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupToolbar() {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.hide()
        activity?.window!!.statusBarColor = Color.BLACK
    }

    override fun onPause() {
        super.onPause()

        simpleExoPlayer!!.playWhenReady = false
        simpleExoPlayer!!.playbackState
        exo_play_pause.setImageDrawable(resources.getDrawable(R.drawable.play))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResume() {
        super.onResume()

        val currentOrientation = resources.configuration.orientation
        if(currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            setupToolbar()
            setFullScreen()
        }

        exo_play_pause.setImageDrawable(resources.getDrawable(R.drawable.pause))
        simpleExoPlayer!!.playWhenReady = true
        simpleExoPlayer!!.playbackState
    }

    private fun setFullScreen() {
        activity?.window?.decorView?.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    private fun addListener() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer!!.addListener(object : Player.EventListener {
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    super.onPlayerStateChanged(playWhenReady, playbackState)

                    if (playbackState == Player.STATE_BUFFERING) {
                        if(controller_buttons != null) controller_buttons.visibility = View.INVISIBLE
                        if(progress_bar != null) progress_bar.visibility = View.VISIBLE

                    } else if (playbackState == Player.STATE_READY) {
                        if(progress_bar != null) progress_bar.visibility = View.GONE
                        if(controller_buttons != null) controller_buttons.visibility = View.VISIBLE
                    }
                }
            })
        }
    }

    private fun screenChangeListener() {

        screen_view.setOnClickListener {
            if (orientationFlag) {
                screen_view.setImageDrawable(resources.getDrawable(R.drawable.fullscreen))
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                orientationFlag = false
            } else {
                screen_view.setImageDrawable(resources.getDrawable(R.drawable.full_screen_exit))
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                orientationFlag = true
            }
        }
    }

    private fun initPlayer() {

        //simpleExoPlayer = SimpleExoPlayer.Builder(this).build()

        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()

        simpleExoPlayer = SimpleExoPlayer.Builder(requireContext()).setTrackSelector(trackSelector)
            .setLoadControl(loadControl)
            .build()

//        val list = args.videoList.filter { it.quality == "sd" }
//        val VIDEO_URL = list[0].link
          val VIDEO_URL = args.videoUrl

        playerView.player = simpleExoPlayer
        val mediaItem = MediaItem.fromUri(VIDEO_URL)
        simpleExoPlayer!!.addMediaItem(mediaItem)
        simpleExoPlayer!!.prepare()
        simpleExoPlayer!!.play()
    }

    private fun controlButtonsClick() {

        exo_play_pause.setOnClickListener {

            if (simpleExoPlayer!!.playWhenReady) {
                simpleExoPlayer!!.playWhenReady = false
                exo_play_pause.setImageDrawable(resources.getDrawable(R.drawable.play))

            } else {
                simpleExoPlayer!!.playWhenReady = true
                exo_play_pause.setImageDrawable(resources.getDrawable(R.drawable.pause))
            }
        }

        exo_forward.setOnClickListener {
            simpleExoPlayer!!.seekTo(simpleExoPlayer!!.currentPosition + INTERVAL)
        }
        exo_rewind.setOnClickListener {
            simpleExoPlayer!!.seekTo(simpleExoPlayer!!.currentPosition - INTERVAL)
        }

        close_button.setOnClickListener {
            findNavController().navigate(VideoFragmentDirections.actionVideoFragmentToMainFragment())
        }
    }

//    @Suppress("DEPRECATION")
//    private fun hideSystemUI() {
//
//        playerView.systemUiVisibility = (
//                View.SYSTEM_UI_FLAG_LOW_PROFILE or
//                        View.SYSTEM_UI_FLAG_FULLSCREEN or
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
//                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                )
//    }

    //    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//
//        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
//            || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//
//              //val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
//            actionBar?.hide()
//        }
//    }
}
