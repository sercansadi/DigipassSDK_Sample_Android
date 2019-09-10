package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import android.content.Context;

import com.vasco.digipass.sdk.DigipassSDK;
import com.vasco.digipass.sdk.DigipassSDKConstants;
import com.vasco.digipass.sdk.responses.GenerationResponse;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Constants;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Salt;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.VascoError;

/**
 * Created by ssadi on 27/02/18.
 */
public class OneTimePassword {
    public static String hostcode = "";
    public static String otp="";
    public static VascoError vascoError = new VascoError();


    public static Boolean generate(String password,Context context){
        Boolean result = false;
        hostcode="";
        otp="";


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
        long timeShift = 0;
        if(SecureStorage.readTimeShift(context)) {
            timeShift = SecureStorage.timeShift;
        }

        /**
         * Generate the fingerprint for the DIGIPASS actions
         */
        String digipassFingerprint ="";
        if(Salt.getDigipassSalt()){
            digipassFingerprint = DeviceBinding.getFingerprint(Salt.digipass_salt,context);
        }else{
            vascoError = Salt.vascoError;
            return result;
        }

        /**
         * generate a OTP
         */
        GenerationResponse generationResponse = DigipassSDK.generateResponseOnly(staticVector, dynamicVector, password, timeShift, DigipassSDKConstants.CRYPTO_APPLICATION_INDEX_APP_1, digipassFingerprint);

        /**
         * get tge updated Dynamic Vector and store it in the Secure Storage
         */
        SecureStorage.write(generationResponse.getDynamicVector(),context);

        /**
         * Check response
         * 0 = ok
         */
        if(generationResponse.getReturnCode()==0){
            result= true;
            otp = generationResponse.getResponse();
            hostcode = generationResponse.getHostCode();

        }else{
            vascoError.code = ""+generationResponse.getReturnCode();
            vascoError.message = DigipassSDK.getMessageForReturnCode(generationResponse.getReturnCode());
        }
        return  result;
    }

}
