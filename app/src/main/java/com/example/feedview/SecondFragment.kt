package com.example.feedview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class SecondFragment: Fragment() {

    lateinit var url: String
    lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = requireArguments().getString("url").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sec_fragment, container, false)
        webView = view.findViewById<WebView>(R.id.frag2_browser)
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
////                //progDailog.show()
                webView.loadUrl(url)
//                webView.loadUrl("javascript:(function() { " +
//                "document.getElementsByClassName('Header')[0].style.display='none'; " +
//                "document.getElementsByClassName('Container--shift')[0].style.display='none'; " +
//                "document.getElementsByClassName('Footer')[0].style.display='none'; " +
//                "document.getElementsByClassName('News-list')[0].style.display='none'; " +
//                "})()");
                return true
            }

//            override fun onPageFinished(view: WebView, url: String) {
//                webView.loadUrl("javascript:(function() { " +
//                        "document.getElementsByClassName('Header')[0].style.display='none'; " +
//                        "document.getElementsByClassName('Container--shift')[0].style.display='none'; " +
//                        "document.getElementsByClassName('Footer')[0].style.display='none'; " +
//                        "document.getElementsByClassName('News-list')[0].style.display='none'; " +
//                        "})()");
//            }
       }

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("onViewCreated", url)
        webView.loadUrl(url)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}