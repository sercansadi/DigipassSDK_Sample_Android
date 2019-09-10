package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import android.content.Context;

import com.vasco.digipass.sdk.DigipassSDK;
import com.vasco.digipass.sdk.DigipassSDKConstants;
import com.vasco.digipass.sdk.models.SecureChannelMessage;
import com.vasco.digipass.sdk.responses.GenerationResponse;
import com.vasco.digipass.sdk.responses.SecureChannelDecryptionResponse;
import com.vasco.digipass.sdk.responses.SecureChannelParseResponse;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Constants;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Salt;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.VascoError;
import com.vasco.message.client.SecureMessagingSDKClient;
import com.vasco.message.client.TransactionData;
import com.vasco.message.constants.SecureMessagingSDKConstants;
import com.vasco.message.exception.SecureMessagingSDKException;

/**
 * Created by ssadi on 28/02/18.
 */
public class SecureChannelMsg {
    public static String hostcode ="";
    public static String signature = "";
    public static String messageContent ="";
    public static VascoError vascoError = new VascoError();


    public static Boolean getMessage(String message, Context context){
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
         * Generate the fingerprint for the DIGIPASS actions
         */
        String digipassFingerprint ="";
        if(Salt.getDigipassSalt()){
            digipassFingerprint = DeviceBinding.getFingerprint(Salt.digipass_salt,context);
        }else{
            vascoError = Salt.vascoError;
            return result;
        }

        SecureChannelParseResponse secureChannelParseResponse=DigipassSDK.parseSecureChannelMessage(message);
        if(secureChannelParseResponse.getReturnCode()==0){
            SecureChannelMessage secureChannelMessage = secureChannelParseResponse.getMessage();

            SecureChannelDecryptionResponse secureChannelDecryptionResponse = DigipassSDK.decryptSecureChannelMessageBody(staticVector, dynamicVector, secureChannelMessage, digipassFingerprint);

            if(secureChannelDecryptionResponse.getReturnCode()==0) {
                try {
                    String messageBody = secureChannelDecryptionResponse.getMessageBody();
                    if(messageBody!="") {
                        TransactionData transactionData = SecureMessagingSDKClient.parseBodyTransactionRequest(messageBody);


                        messageContent = transactionData.getFreeText().getClearText();
                    }
                    result = true;

                } catch (SecureMessagingSDKException e) {
                    e.printStackTrace();
                    vascoError.code = ""+e.getErrorCode();
                    vascoError.message = e.getMessage();
                }
            }else{
                vascoError.code = ""+secureChannelDecryptionResponse.getReturnCode();
                vascoError.message = DigipassSDK.getMessageForReturnCode(secureChannelDecryptionResponse.getReturnCode());
            }
        }else{

                vascoError.code = ""+secureChannelParseResponse.getReturnCode();
                vascoError.message = DigipassSDK.getMessageForReturnCode(secureChannelParseResponse.getReturnCode());

        }

        return result;

    }

    public  static Boolean signMessage(String message,String password, Context context){
        Boolean result = false;
        hostcode="";
        signature = "";

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
         * Parse the message received from the server, and retract the SecureChannelMessage
         */
        SecureChannelParseResponse secureChannelParseResponse=DigipassSDK.parseSecureChannelMessage(message);
        SecureChannelMessage secureChannelMessage = secureChannelParseResponse.getMessage();

        /**
         * generate a signature
         */
        GenerationResponse generationResponse = DigipassSDK.generateSignatureFromSecureChannelMessage(staticVector, dynamicVector, secureChannelMessage, password, timeShift, DigipassSDKConstants.CRYPTO_APPLICATION_INDEX_APP_4, digipassFingerprint);

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
            signature = generationResponse.getResponse();
            hostcode = generationResponse.getHostCode();

        }else{
            vascoError.code = ""+generationResponse.getReturnCode();
            vascoError.message = DigipassSDK.getMessageForReturnCode(generationResponse.getReturnCode());
        }
        return result;
    }

    public  static boolean deactivationMessage(String message, Context context){
        Boolean result = false;
        hostcode="";
        signature = "";
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

        String digipassFingerprint ="";
        if(Salt.getDigipassSalt()){
            digipassFingerprint = DeviceBinding.getFingerprint(Salt.digipass_salt,context);
        }else{
            vascoError = Salt.vascoError;
            return result;
        }

        SecureChannelParseResponse secureChannelParseResponse=DigipassSDK.parseSecureChannelMessage(message);
        SecureChannelMessage secureChannelMessage = secureChannelParseResponse.getMessage();
        SecureChannelDecryptionResponse secureChannelDecryptionResponse = DigipassSDK.decryptSecureChannelMessageBody(staticVector,dynamicVector,secureChannelMessage,digipassFingerprint);
        message = secureChannelDecryptionResponse.getMessageBody();
        result = true;
        try {
            SecureMessagingSDKClient.parseBodyDeactivateRequest(message);
        } catch (SecureMessagingSDKException e) {
            e.printStackTrace();
        }

        return result;
    }


}
