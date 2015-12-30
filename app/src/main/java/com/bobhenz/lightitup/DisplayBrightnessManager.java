package com.bobhenz.lightitup;

import android.app.Activity;
import android.app.Application;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class DisplayBrightnessManager {
    public static enum BrightnessLevel {
        DARK,
        DIM,
        MODERATE,
        BRIGHT;
        public BrightnessLevel next() {
            if (this == BRIGHT) {
                return BRIGHT;
            } else {
                return BrightnessLevel.values()[this.ordinal() + 1];
            }
        }
        public BrightnessLevel previous() {
            if (this == DARK) {
                return DARK;
            } else {
                return BrightnessLevel.values()[this.ordinal() - 1];
            }
        }
    };

    private BrightnessLevel mCurrentLevel;
    private Application mApplication;
    private Activity mActivity;

    DisplayBrightnessManager(Application app, Activity activity) {
        mApplication = app;
        mActivity = activity;
    }

    public void IncreaseLevel() {
        if (mCurrentLevel != null)
            SetLevel(mCurrentLevel.next());
        else
            SetLevel(BrightnessLevel.BRIGHT);
    }

    public void DecreaseLevel() {
        if (mCurrentLevel != null)
            SetLevel(mCurrentLevel.previous());
        else
            SetLevel(BrightnessLevel.DARK);
    }

    private void SetScreenBrightness(float value) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.screenBrightness = value;
        mActivity.getWindow().setAttributes(lp);
    }

    public void SetLevel(BrightnessLevel level) {
        if (mCurrentLevel != level) {
            mCurrentLevel = level;
            if (mCurrentLevel == BrightnessLevel.DARK) {
                SetScreenBrightness(0f);
                Window window = mActivity.getWindow();
                window.setBackgroundDrawableResource(R.color.dark_background);
                TextView text = (TextView)mActivity.findViewById(R.id.text_instructions);
                int color = ContextCompat.getColor(mActivity, R.color.dark_text);
                text.setTextColor(color);
            } else if (mCurrentLevel == BrightnessLevel.DIM) {
                SetScreenBrightness(0.5f);
                Window window = mActivity.getWindow();
                window.setBackgroundDrawableResource(R.color.dim_background);
                TextView text = (TextView)mActivity.findViewById(R.id.text_instructions);
                int color = ContextCompat.getColor(mActivity, R.color.dim_text);
                text.setTextColor(color);
            } else {
                SetScreenBrightness(1f);
                Window window = mActivity.getWindow();
                window.setBackgroundDrawableResource(R.color.bright_background);
                TextView text = (TextView)mActivity.findViewById(R.id.text_instructions);
                int color = ContextCompat.getColor(mActivity, R.color.bright_text);
                text.setTextColor(color);
            }
        }
    }
}
