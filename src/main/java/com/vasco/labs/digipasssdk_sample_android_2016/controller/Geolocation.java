package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import android.content.Context;

import com.vasco.digipass.sdk.utils.geolocation.GeolocationSDK;
import com.vasco.digipass.sdk.utils.geolocation.GeolocationSDKArea;
import com.vasco.digipass.sdk.utils.geolocation.GeolocationSDKException;
import com.vasco.digipass.sdk.utils.geolocation.GeolocationSDKLocation;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Constants;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.VascoError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssadi on 28/02/18.
 * add <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/> in AndroidManifest.xml
 */

public class Geolocation {
    public static VascoError vascoError = new VascoError();
    public static Boolean isLocationAuthorized = false;
    public static String coordinates = "";
    public static double accuracy = 0;

    public static Boolean locate(Context context){
        Boolean result = false;
        isLocationAuthorized = false;
        try {

            /**
             * Check if location is enabled
             */
            boolean locationEnabled = GeolocationSDK.isLocationServiceEnabled(context);

            /**
             * Calculate the location the device and retract the coördinates and chekc the accuracy
             */
            GeolocationSDKLocation location = GeolocationSDK.getLocation(Constants.GEOLOCATION_TIMEOUT, context, Constants.GEOLOCATION_ACCURATIE);
            coordinates = "Latidude: "+location.getLatitude();
            coordinates +="\nLongitude: "+location.getLongitude();
            accuracy = location.getAccuracy();

            /**
             * Check if the user is in an Authorized location
             */
            List<GeolocationSDKArea>  areaList = new ArrayList<GeolocationSDKArea>();
            GeolocationSDKArea geolocationSDKArea1 = new GeolocationSDKArea(50.938576, 4.353761,50.977428, 4.403685);
            GeolocationSDKArea geolocationSDKArea2 = new GeolocationSDKArea(50.936978, 4.332822,50.937384, 4.333198);
            areaList.add(geolocationSDKArea1);
            areaList.add(geolocationSDKArea2);
            isLocationAuthorized = GeolocationSDK.isLocationAuthorized(location,areaList);
            result = true;
        } catch (GeolocationSDKException e) {
            e.printStackTrace();
            vascoError.code = e.getErrorCode()+"";
            vascoError.message = e.getMessage();
        }
        return result;
    }
}
