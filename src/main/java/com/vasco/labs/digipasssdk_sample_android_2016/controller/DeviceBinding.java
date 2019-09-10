package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import android.content.Context;

import com.vasco.digipass.sdk.utils.devicebinding.DeviceBindingSDK;
import com.vasco.digipass.sdk.utils.devicebinding.DeviceBindingSDKException;


/**
 * Created by ssadi on 27/02/18.
 * add  <uses-permission android:name="android.permission.READ_PHONE_STATE"/> in AndroidManifest.xml
 */
public class DeviceBinding {
    public static String getFingerprint(String salt, Context context){
        try {
            /**
             * get the device fingerprint
             */
            return DeviceBindingSDK.getDeviceFingerprint(salt,context);
        } catch (DeviceBindingSDKException e) {
            e.printStackTrace();
        }
        return "";
    }
}
