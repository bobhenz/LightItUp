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
            mDisplayBrightnessManager.SetLevel(DisplayBrightnessManager.BrightnessLevel.DARK);
            mTorchManager.Toggle();
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
            }
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTorchManager = new TorchManager();
        mDisplayBrightnessManager = new DisplayBrightnessManager(getApplication(), this);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        mDisplayBrightnessManager.SetLevel(DisplayBrightnessManager.BrightnessLevel.DARK);
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
