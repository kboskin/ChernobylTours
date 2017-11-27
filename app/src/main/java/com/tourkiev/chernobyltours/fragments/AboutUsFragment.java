package com.tourkiev.chernobyltours.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tourkiev.chernobyltours.R;


public class AboutUsFragment extends Fragment {
    private String curURL;

    public void init(String url) {
        curURL = url;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,

                             Bundle savedInstanceState) {

        View view = inflater
                .inflate(R.layout.fragment_about_us, container, false);

        if (curURL != null) {

            WebView webview = (WebView) view.findViewById(R.id.web_view);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.setWebViewClient(new webClient());
            webview.loadUrl(curURL);


        }

        return view;

    }

    @SuppressLint("SetJavaScriptEnabled")
    public void updateUrl(String url) {
        curURL = url;
        WebView webview = getView().findViewById(R.id.web_view);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new webClient());
        webview.loadUrl(url);

    }

    private class webClient extends WebViewClient {

        ProgressDialog pb = null;

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            // prepare for a progress bar dialog
            if (pb == null) {
                pb = new ProgressDialog(getActivity());
                pb.setCancelable(true);
                pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pb.setMessage(getString(R.string.loading));
                pb.show();

            }
        }

        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            pb.dismiss();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("resumed", String.valueOf(isResumed()));
    }
}