package com.bobhenz.lightitup;

/**
 * Since android.hardware.Camera is deprecated, and the new interface ("android.hardware.camera2")
 * is significantly different. Therefore, wrap this functionality in a simple object to get the
 * trivial functionality we want. (i.e. to turn on/off the LED.)
 */

import android.hardware.Camera;

public class TorchManager {
    private boolean isTorchOn;
    private Camera camera;
    private Camera.Parameters camParamsLedOn;
    private Camera.Parameters camParamsLedOff;

    TorchManager()
    {
        camera = Camera.open();

        camParamsLedOn = camera.getParameters();
        camParamsLedOn.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

        camParamsLedOff = camera.getParameters();
        camParamsLedOff.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

        isTorchOn = false;
    }

    void TurnOn()
    {
        camera.setParameters(camParamsLedOn);
        isTorchOn = true;
    }

    void TurnOff() {
        camera.setParameters(camParamsLedOff);
        isTorchOn = false;
    }

    void Toggle() {
        if (isTorchOn) {
            TurnOff();
        } else {
            TurnOn();
        }
    }

    void Close() {
        camera.release();
    }
}
