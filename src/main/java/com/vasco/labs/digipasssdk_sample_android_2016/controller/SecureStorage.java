package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import android.content.Context;

import com.vasco.digipass.sdk.utils.securestorage.SecureStorageSDK;
import com.vasco.digipass.sdk.utils.securestorage.SecureStorageSDKException;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.ByteUtils;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Constants;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Salt;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.VascoError;

/**
 * Created by ssadi on 27/02/18.
 */
public class SecureStorage {
    public static VascoError vascoError = new VascoError();
    public static byte[] dynamicVector = null;
    public static byte[] staticVector = null;
    public static long timeShift = 0;

    public static boolean initWrite(byte[] staticVector, byte[] dynamicVector, Context context){
        boolean result=false;
        /**
         * Generate the fingerprint for the secure storage
         */
        String fingerPrintStorage ="";
        if(Salt.getSecStorSalt()){
            fingerPrintStorage = DeviceBinding.getFingerprint(Salt.storage_salt,context);
        }else{
            vascoError = Salt.vascoError;
            return result;
        }

        SecureStorageSDK secureStorageSDK = null;
        try {
            /**
             * Initiate the secure storage
             */
            secureStorageSDK = SecureStorageSDK.init(Constants.SECURE_STORAGE_NAME, fingerPrintStorage, Constants.ITERATION_NUMBER, context);

            /**
             * Put the bytes of Static Vector to the secure storage
             */
            secureStorageSDK.putBytes(Constants.STATIC_VECTOR_KEY, staticVector);
            /**
             * Put the bytes of Dynamic Vector to the secure storage
             */
            secureStorageSDK.putBytes(Constants.DYNAMIC_VECTOR_KEY, dynamicVector);

            /**
             * Write the secure storage
             */
            secureStorageSDK.write(fingerPrintStorage,Constants.ITERATION_NUMBER,context);
            result = true;
        } catch (SecureStorageSDKException e) {
            e.printStackTrace();
            vascoError.code = e.getErrorCode()+"";
            vascoError.message = e.getMessage();
        }
        return result;
    }

    public static boolean readDynamicVector(Context context){
        boolean result = false;
        /**
         * Generate the fingerprint for the secure storage
         */
        String fingerPrintStorage ="";
        if(Salt.getSecStorSalt()){
            fingerPrintStorage = DeviceBinding.getFingerprint(Salt.storage_salt,context);
        }else{
            vascoError = Salt.vascoError;
            return result;
        }
        SecureStorageSDK secureStorageSDK = null;
        try {
            /**
             * Initiate the secure storage
             */
            secureStorageSDK = SecureStorageSDK.init(Constants.SECURE_STORAGE_NAME, fingerPrintStorage, Constants.ITERATION_NUMBER, context);

            /**
             * Read the bytes of Dynamic Vector to the secure storage
             */
            dynamicVector = secureStorageSDK.getBytes(Constants.DYNAMIC_VECTOR_KEY);

            result = true;
        } catch (SecureStorageSDKException e) {
            e.printStackTrace();
            vascoError.code = ""+e.getErrorCode();
            vascoError.message = e.getMessage();
        }
        return result;
    }

    public static boolean readStaticVector(Context context){
        boolean result=false;
        /**
         * Generate the fingerprint for the secure storage
         */
        String fingerPrintStorage ="";
        if(Salt.getSecStorSalt()){
            fingerPrintStorage = DeviceBinding.getFingerprint(Salt.storage_salt,context);
        }else{
            vascoError = Salt.vascoError;
            return result;
        }

        SecureStorageSDK secureStorageSDK = null;
        try {
            /**
             * Initiate the secure storage
             */
            secureStorageSDK = SecureStorageSDK.init(Constants.SECURE_STORAGE_NAME, fingerPrintStorage, Constants.ITERATION_NUMBER, context);

            /**
             * Read the bytes of Static Vector to the secure storage
             */
            staticVector = secureStorageSDK.getBytes(Constants.STATIC_VECTOR_KEY);
            result = true;

        } catch (SecureStorageSDKException e) {
            e.printStackTrace();
            vascoError.code = ""+e.getErrorCode();
            vascoError.message = e.getMessage();
        }
        return result;
    }

    public static boolean readTimeShift(Context context){
        boolean result=false;
        byte[] timeShiftB = new byte[0];
        /**
         * Generate the fingerprint for the secure storage
         */
        String fingerPrintStorage ="";
        if(Salt.getSecStorSalt()){
            fingerPrintStorage = DeviceBinding.getFingerprint(Salt.storage_salt,context);
        }else{
            vascoError = Salt.vascoError;
            return result;
        }


        SecureStorageSDK secureStorageSDK = null;
        try {
            /**
             * Initiate the secure storage
             */
            secureStorageSDK = SecureStorageSDK.init(Constants.SECURE_STORAGE_NAME, fingerPrintStorage, Constants.ITERATION_NUMBER, context);

            /**
             * Get the bytes od the time shift from the secure storage
             */
            timeShiftB = secureStorageSDK.getBytes(Constants.TIME_SHIFT_KEY);

            /**
             * Convert byte to long
             */
            timeShift =  ByteUtils.bytesToLong(timeShiftB);
            result = true;
        } catch (SecureStorageSDKException e) {
            timeShift=0;
            e.printStackTrace();
            vascoError.code = ""+e.getErrorCode();
            vascoError.message = e.getMessage();
        }
        return result;
    }

    public static boolean write(byte[] dynamicVector, Context context){
        boolean result=false;
        /**
         * Generate the fingerprint for the secure storage
         */
        String fingerPrintStorage ="";
        if(Salt.getSecStorSalt()){
            fingerPrintStorage = DeviceBinding.getFingerprint(Salt.storage_salt,context);
        }else{
            vascoError = Salt.vascoError;
            return result;
        }

        SecureStorageSDK secureStorageSDK = null;
        try {
            /**
             * Initiate the secure storage
             */
            secureStorageSDK = SecureStorageSDK.init(Constants.SECURE_STORAGE_NAME, fingerPrintStorage, Constants.ITERATION_NUMBER, context);

            /**
             * Put the bytes of Dynamic Vector to the secure storage
             */
            secureStorageSDK.putBytes(Constants.DYNAMIC_VECTOR_KEY, dynamicVector);
            /**
             * Write the secure storage
             */
            secureStorageSDK.write(fingerPrintStorage, Constants.ITERATION_NUMBER, context);

            result = true;
        } catch (SecureStorageSDKException e) {
            e.printStackTrace();
            vascoError.code = ""+e.getErrorCode();
            vascoError.message = e.getMessage();
        }
        return result;
    }

    public static boolean writeTimeShift(long timeShift, Context context){
        boolean result=false;

        /**
         * Generate the fingerprint for the secure storage
         */
        String fingerPrintStorage ="";
        if(Salt.getSecStorSalt()){
            fingerPrintStorage = DeviceBinding.getFingerprint(Salt.storage_salt,context);
        }else{
            vascoError = Salt.vascoError;
            return result;
        }

        SecureStorageSDK secureStorageSDK = null;
        try {
            byte[] timeShiftB = ByteUtils.longToBytes(timeShift);
            /**
             * Initiate the secure storage
             */
            secureStorageSDK = SecureStorageSDK.init(Constants.SECURE_STORAGE_NAME, fingerPrintStorage, Constants.ITERATION_NUMBER, context);

            /**
             * Put the bytes of time shift to the secure storage
             */
            secureStorageSDK.putBytes(Constants.TIME_SHIFT_KEY, timeShiftB);

            /**
             * Write the secure storage
             */
            secureStorageSDK.write(fingerPrintStorage, Constants.ITERATION_NUMBER, context);
            result = true;
        } catch (SecureStorageSDKException e) {
            e.printStackTrace();
            vascoError.code = ""+e.getErrorCode();
            vascoError.message = e.getMessage();
        }

        return result;

    }

    public static boolean clearSecureStorage(Context context){
        boolean result = false;
        SecureStorageSDK secureStorageSDK = null;

        /**
         * Generate the fingerprint for the secure storage
         */
        String fingerPrintStorage ="";
        if(Salt.getSecStorSalt()){
            fingerPrintStorage = DeviceBinding.getFingerprint(Salt.storage_salt,context);
        }else{
            vascoError = Salt.vascoError;
            return result;
        }


        try {

            /**
             * Initiate the secure storage
             */
           secureStorageSDK = SecureStorageSDK.init(Constants.SECURE_STORAGE_NAME, fingerPrintStorage, Constants.ITERATION_NUMBER, context);

            /**
             * Clear the memory
             */
            secureStorageSDK.clear();

            /**
             * Write the secure storage
             */
            secureStorageSDK.write(fingerPrintStorage, Constants.ITERATION_NUMBER, context);
            result = true;
        } catch (SecureStorageSDKException e) {
            e.printStackTrace();
            vascoError.code = ""+e.getErrorCode();
            vascoError.message = e.getMessage();
        }

        return result;

    }
}
