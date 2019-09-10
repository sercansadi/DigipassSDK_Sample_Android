package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import com.vasco.digipass.sdk.DigipassSDK;

/**
 * Created by ssadi on 07/03/18.
 */
public class TimeSync {

    public static long calculate(long serverTime){
        /**
         * Compute the client server time shift
         */
        return DigipassSDK.computeClientServerTimeShiftFromServerTime(serverTime);


    }
}
