package org.geeksolutions.daliely;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CustomSwipe extends SwipeRefreshLayout {

    private WebView webView;

    public CustomSwipe(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSwipe(@NonNull Context context,WebView webView){
        super(context);
        this.webView = webView;
    }


    @Override
    public boolean canChildScrollUp() {
        if (webView != null) return webView.canScrollVertically(-1);
        return false;
    }
}
