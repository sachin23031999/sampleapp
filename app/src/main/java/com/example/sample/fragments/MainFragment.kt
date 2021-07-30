package com.example.sample.fragments

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sample.R
import com.example.sample.adapters.MainAdapter
import com.example.sample.adapters.RvInterface
import com.example.sample.data.VideoList
import com.example.sample.data.Video_files
import com.example.sample.models.Details
import com.example.sample.utils.ErrorListener
import com.example.sample.viewModels.MainViewModel
import com.sachin.milkdistributor.room.RoomDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment(), RvInterface, ErrorListener {

    private val mainAdapter = MainAdapter()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setToolbar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        view.main_rv.layoutManager = LinearLayoutManager(context)
        view.main_rv.adapter = mainAdapter
        view.main_rv.setHasFixedSize(true)

        mainAdapter.listener = this

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // viewModel.errorListener = this

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataToRV()
        refreshRV()

    }

    private fun setToolbar() {
        Log.d("toolbarar", "Toolbar Set")
        //setHasOptionsMenu(true)
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "Nature"
        actionBar?.setDisplayHomeAsUpEnabled(false)
       // activity?.window!!.statusBarColor = Color.parseColor(R.color.primaryVarient.toString())
    }

    private fun setDataToRV() {

        viewModel.getDetails().observe(viewLifecycleOwner, {
            mainAdapter.setAdapterData(it)
        })
    }

    private fun refreshRV() {
        swipeRefresh.setOnRefreshListener {
            setDataToRV()
            swipeRefresh.isRefreshing = false
        }
    }

    override fun onImageClickListener(video_url: String) {

        val directions = MainFragmentDirections.actionMainFragmentToVideoFragment(video_url)
        findNavController().navigate(directions)

    }

    override fun onUserInfoCLickListener(user_url: String) {

        val directions = MainFragmentDirections.actionMainFragmentToUserInfoFragment(user_url)
        findNavController().navigate(directions)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResume() {

        activity?.window!!.statusBarColor = Color.parseColor("#44671B")
        //setToolbar()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onResume()
    }

    override fun errorOccurred(error: String) {
        Toast.makeText(context, "Some error occurred", Toast.LENGTH_SHORT).show()
    }

}