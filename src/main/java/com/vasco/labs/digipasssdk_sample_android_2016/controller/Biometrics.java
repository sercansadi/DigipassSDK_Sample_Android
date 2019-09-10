package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Debug;
import android.util.Log;

import com.vasco.digipass.sdk.utils.biometricsensor.BiometricSensorSDK;
import com.vasco.digipass.sdk.utils.biometricsensor.BiometricSensorSDKDialogParams;
import com.vasco.digipass.sdk.utils.biometricsensor.BiometricSensorSDKException;
import com.vasco.digipass.sdk.utils.biometricsensor.BiometricSensorSDKListener;
import com.vasco.labs.digipasssdk_sample_android_2016.MainActivity;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.VascoError;

/**
 * Created by vanroth1 on 02/11/17.
 */

public class Biometrics  {


    public static VascoError vascoError = new VascoError();

    /**
     * Is the fingerprint supported on the platform you are trying to use it
     * @param context
     * @return boolean
     */
    public static boolean isFingerprintSupported(Context context){

        /**
         * Set the default response result to false.
         */
        boolean result = false;

        try {
            // Based on the output of the biometrics SDK the response will be true/false
            result = BiometricSensorSDK.isUserFingerprintSupportedByPlatform(context);
        } catch (BiometricSensorSDKException e) {
            e.printStackTrace();
            vascoError.code = ""+e.getErrorCode();
            vascoError.message = e.getMessage();
            return result;
        }

        return result;
    }

    /**
     * Is the fingerprint usable, is it setup on the device.
     * @param context
     * @return boolean
     */
    public static boolean isFingerprintUsable(Context context){
        /**
         * Set the default response result to false.
         */
        boolean result = false;

        try {
            // Based on the output of the biometrics SDK the response will be true/false
            result = BiometricSensorSDK.isUserFingerprintUsable(context);
        } catch (BiometricSensorSDKException e) {
            e.printStackTrace();
            vascoError.code = ""+e.getErrorCode();
            vascoError.message = e.getMessage();
            return result;
        }

        return result;
    }

    /**
     * Actual verification of the fingerprint. Note that a fingerprint in Android has to be setup.
     * @param context
     * @return
     */
    public static boolean verifyFingerprint(Activity activity){

        /**
         * Set the default response result to false.
         */
        boolean result = false;

        try {

            // setup dialog params
            BiometricSensorSDKDialogParams.Builder dialogParamsBuilder = new BiometricSensorSDKDialogParams.Builder(activity);



            // Based on the output of the biometrics SDK the response will be true/false
            BiometricSensorSDK.verifyUserFingerprint(new BiometricsListener(),activity, dialogParamsBuilder.create());

            result = true;
        } catch (BiometricSensorSDKException e) {
            e.printStackTrace();
            vascoError.code = ""+e.getErrorCode();
            vascoError.message = e.getMessage();
            return result;
        }

        return result;


    }

}
