package org.geeksolutions.daliely;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    private WebView theWebPage;
    private CustomSwipe swipeRefreshLayout;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        theWebPage = new WebView(this);
        theWebPage.getSettings().setJavaScriptEnabled(true);
        theWebPage.getSettings().setPluginState(WebSettings.PluginState.ON);
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (isNetworkAvailable()) {
                    swipeRefreshLayout.setRefreshing(true);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
                if (isNetworkAvailable()) imageView.setVisibility(View.GONE);
            }
        };
        theWebPage.setWebViewClient(webViewClient);
        theWebPage.loadUrl(getResources().getString(R.string.url));

        RelativeLayout rl = new RelativeLayout(this);
        rl.addView(theWebPage);
        theWebPage.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_error);
        imageView.setBackgroundColor(getResources().getColor(R.color.back_color));
        rl.addView(imageView);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setVisibility(View.GONE);

        swipeRefreshLayout = new CustomSwipe(this,theWebPage);
        swipeRefreshLayout.setColorScheme(R.color.blue, R.color.purple, R.color.green, R.color.orange);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.addView(rl);

        setContentView(swipeRefreshLayout);
    }

    @Override
    public void onBackPressed() {
        if (theWebPage.canGoBack()) {
            theWebPage.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRefresh() {
        theWebPage.reload();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)  getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}