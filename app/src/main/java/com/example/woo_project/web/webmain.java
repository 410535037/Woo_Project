package com.example.woo_project.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.example.woo_project.R;

public class webmain extends Fragment {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.webmain, container, false);

        WebView web = view.findViewById(R.id.webmain);
        web.getSettings().setJavaScriptEnabled(true);

        web.loadUrl("http://134.208.97.191:8080/html/WooWeb/traceability_index.html");
        return view;
    }

}
