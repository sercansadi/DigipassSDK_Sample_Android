package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import android.util.Log;

import com.vasco.digipass.sdk.utils.biometricsensor.BiometricSensorSDKListener;

/**
 * Created by vanroth1 on 02/11/17.
 */

public class BiometricsListener implements BiometricSensorSDKListener {

    @Override
    public void onFingerprintScanFailed(int i, String s) {

        Log.d("BioMetrics", "Biometrics scan failed");
    }

    @Override
    public void onFingerprintScanError(int i, String s) {

        Log.d("BioMetrics", "Biometrics scan error");
    }

    @Override
    public void onFingerprintScanSucceeded() {
        Log.d("BioMetrics", "Biometrics Succes");
    }

    @Override
    public void onFingerprintScanCancelled() {

        Log.d("BioMetrics", "Biometrics cancelled");

    }

    @Override
    public void onFingerprintFallbackCalled() {

        Log.d("BioMetrics", "Biometrics fallback called");
    }
}
