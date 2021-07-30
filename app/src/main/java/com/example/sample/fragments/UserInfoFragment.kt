package com.example.sample.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.sample.R
import com.example.sample.viewModels.MainViewModel
import com.google.android.exoplayer2.text.webvtt.WebvttCssStyle
import kotlinx.android.synthetic.main.fragment_user_info.*

class UserInfoFragment : Fragment() {

    private val args: UserInfoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
    }

    private fun setToolbar() {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "Info"
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWebView()
    }


    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setWebView() {
        wb_webView.webViewClient = WebViewClient()
        wb_webView.apply {
            loadUrl(args.userUrl)
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }

//        wb_webView.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(
//                view: WebView?,
//                request: WebResourceRequest?
//            ): Boolean {
//                return true
//            }

//       wb_webView.canGoBack()
//        wb_webView.setOnKeyListener{ view, keyCode, keyEvent ->
//
//            keyCode == KeyEvent.KEYCODE_BACK
//                    && keyEvent.action == MotionEvent.ACTION_UP
//                    && wb_webView.canGoBack()
//
//        }

    }

}
