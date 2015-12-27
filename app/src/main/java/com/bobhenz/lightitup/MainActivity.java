package com.bobhenz.lightitup;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    private TorchManager torchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        torchManager = new TorchManager();
        //StrobeFlashRunnable strobeFlashRunnable = StrobeFlashRunnable.getInstance();

        //Thread thread = new Thread(strobeFlashRunnable);
        //thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        torchManager.TurnOff();
    }

    @Override
    protected void onResume() {
        super.onResume();
        torchManager.TurnOn();
    }
}
