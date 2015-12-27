/*
 * Original work Copyright (c) 2011 Simon Walker
 * Modified work Copyright 2015 Mathieu Souchaud
 * Modified work Copyright 2015 Bob Henz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.bobhenz.lightitup;


import android.hardware.Camera;

public class StrobeFlashRunnable implements Runnable {
    public static StrobeFlashRunnable getInstance()
    {
        return instance == null ? instance = new StrobeFlashRunnable() : instance;
    }

    private static StrobeFlashRunnable instance;


    public volatile boolean requestStop = false;
    private boolean isRunning = false;
    private double delayOn = 10;
    private double delayOff = 990;

    @Override
    public void run() {
        if(isRunning)
            return;

        requestStop = false;
        isRunning = true;

        Camera cam = Camera.open();

        Camera.Parameters pon = cam.getParameters(), poff = cam.getParameters();

        pon.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        poff.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

        while(!requestStop) {
            try {
                if (delayOn > 0) {
                    cam.setParameters(pon);
                    Thread.sleep(Math.round(delayOn));
                }

                if (delayOff > 0) {
                    cam.setParameters(poff);
                    Thread.sleep(Math.round(delayOff));
                }
            }
            catch(InterruptedException ex)
            {}
            catch(RuntimeException ex)
            {
                requestStop = true;
            }
        }
        cam.setParameters(poff);

        cam.release();

        isRunning = false;
        requestStop = false;
    }
}
