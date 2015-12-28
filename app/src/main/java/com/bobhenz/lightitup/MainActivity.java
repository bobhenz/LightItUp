package com.bobhenz.lightitup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MainActivity extends Activity {
    private TorchManager mTorchManager;
    private GestureDetectorCompat mDetector;
    private DisplayBrightnessManager mDisplayBrightnessManager;
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap (MotionEvent event) {
            mTorchManager.Toggle();
            //getApplication().setTheme(R.style.Darkest);
            //setContentView(R.layout.activity_main);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                /* We are looking at a horizontal swipe. */
                mTorchManager.TurnOff();
                if (velocityX > 0) {
                    /* swipe from left to right */
                    mDisplayBrightnessManager.DecreaseLevel();
                } else {
                    /* swipe from right to left */
                    mDisplayBrightnessManager.IncreaseLevel();
                }
                Log.d("swipe", "Horiz swipe detected: " + Float.toString(velocityX));
            }
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.Darkest);
        setContentView(R.layout.activity_main);
        mTorchManager = new TorchManager();
        mDisplayBrightnessManager = new DisplayBrightnessManager(getApplication());
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        mDisplayBrightnessManager.SetLevel(DisplayBrightnessManager.BrightnessLevel.DARK);
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
