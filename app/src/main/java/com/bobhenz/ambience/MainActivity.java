package com.bobhenz.ambience;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrobeFlashRunnable strobeFlashRunnable = StrobeFlashRunnable.getInstance();

        Thread thread = new Thread(strobeFlashRunnable);
        thread.start();
    }
}
