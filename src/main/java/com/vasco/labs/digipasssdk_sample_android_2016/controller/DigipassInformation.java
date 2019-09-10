package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import android.content.Context;

import com.vasco.digipass.sdk.DigipassSDK;
import com.vasco.digipass.sdk.responses.DigipassPropertiesResponse;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.VascoError;

/**
 * Created by ssadi on 06/03/18.
 */
public class DigipassInformation {
    public static String serialNumber="";
    public static int sequenceNumber=0;
    public static byte status=0;
    public static DigipassPropertiesResponse.Application[] applications = new DigipassPropertiesResponse.Application[0];
    public static VascoError vascoError = new VascoError();

    public static boolean get(Context context){
        Boolean result = false;

        /**
         * get the Static Vector and Dynamic Vector from the Secure storage
         */

        byte[] staticVector = null;
        byte[] dynamicVector = null;
        if(SecureStorage.readStaticVector(context)) {
            staticVector = SecureStorage.staticVector;
        }else{
            vascoError = SecureStorage.vascoError;
            return result;
        }
        if(SecureStorage.readDynamicVector(context)) {
            dynamicVector = SecureStorage.dynamicVector;
        }else{
            vascoError = SecureStorage.vascoError;
            return result;
        }

        /**
         * Get the Digipass information
         */
        DigipassPropertiesResponse digipassPropertiesResponse = DigipassSDK.getDigipassProperties(staticVector, dynamicVector);

        /**
         * Check the return code
         */
        if(digipassPropertiesResponse.getReturnCode()==0){

            /**
             * Get all wanted information
             */
            serialNumber = digipassPropertiesResponse.getSerialNumber();
            sequenceNumber = digipassPropertiesResponse.getSequenceNumber();
            status=digipassPropertiesResponse.getStatus();
            applications=digipassPropertiesResponse.getApplications();
            for(DigipassPropertiesResponse.Application application : applications){

            }
            result = true;
        }else{
            vascoError.code = ""+digipassPropertiesResponse.getReturnCode();
            vascoError.message = DigipassSDK.getMessageForReturnCode(digipassPropertiesResponse.getReturnCode());
        }

        return result;
    }
}
