package org.geeksolutions.daliely;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class ActivitySplashy extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashy);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }, 2000);
    }
}