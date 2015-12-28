package com.bobhenz.lightitup;

import android.app.Application;
import android.util.Log;

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

    DisplayBrightnessManager(Application app) {
        mApplication = app;
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

    public void SetLevel(BrightnessLevel level) {
        if (mCurrentLevel != level) {
            mCurrentLevel = level;
            Log.d("DisplayBrightness", "Would have set to level:" + level.toString() + " (if i knew how)");
            if (mCurrentLevel == BrightnessLevel.DARK) {
                mApplication.setTheme(R.style.Darkest);
            } else {
                mApplication.setTheme(R.style.Dim);
            }
        }
    }
}
