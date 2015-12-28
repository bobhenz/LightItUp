package com.bobhenz.lightitup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MainActivity extends Activity {
    private TorchManager mTorchManager;
    private GestureDetectorCompat mDetector;

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap (MotionEvent event) {
            mTorchManager.Toggle();
            //getApplication().setTheme(R.style.Darkest);
            //setContentView(R.layout.activity_main);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.Darkest);
        setContentView(R.layout.activity_main);
        mTorchManager = new TorchManager();
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        //StrobeFlashRunnable strobeFlashRunnable = StrobeFlashRunnable.getInstance();

        //Thread thread = new Thread(strobeFlashRunnable);
        //thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTorchManager.Close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTorchManager.Open();
    }
}
