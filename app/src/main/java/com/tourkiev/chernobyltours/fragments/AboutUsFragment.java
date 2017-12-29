package com.tourkiev.chernobyltours.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tourkiev.chernobyltours.R;


public class AboutUsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private String curURL;
    private SwipeRefreshLayout swipe;

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
                .inflate(R.layout.webview_fragments, container, false);

        if (curURL != null) {

            WebView webview = (WebView) view.findViewById(R.id.web_view);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.setWebViewClient(new WebClient(false)); // without swipe
            webview.loadUrl(curURL);
        }

        swipe = view.findViewById(R.id.swipe);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.com_facebook_blue),
                getResources().getColor(R.color.colorYellow));

        swipe.setOnRefreshListener(AboutUsFragment.this);

        return view;

    }

    @SuppressLint("SetJavaScriptEnabled")
    public void updateUrl(String url) {
        curURL = url;
        WebView webview = getView().findViewById(R.id.web_view);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebClient(true)); // refreshing with swipe
        webview.loadUrl(url);
    }

    @Override
    public void onRefresh() {
        updateUrl(curURL);
    }

    private class WebClient extends WebViewClient {

        ProgressDialog pb = null;
        private boolean withSwipe;

        WebClient(boolean withSwipe) {
            this.withSwipe = withSwipe;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            // prepare for a progress bar dialog
            if (withSwipe) {
                swipe.setRefreshing(true);
            } else {
                if (pb == null) {
                    pb = new ProgressDialog(getActivity());
                    pb.setCancelable(true);
                    pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pb.setMessage(getString(R.string.loading));
                    pb.show();

                }
            }
        }

        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            if (withSwipe) {
                swipe.setRefreshing(false);
            } else {
                if (pb != null)
                    pb.dismiss();
            }

        }

    }
}