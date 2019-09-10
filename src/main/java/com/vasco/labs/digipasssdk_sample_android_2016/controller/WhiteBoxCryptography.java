package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import com.vasco.digipass.sdk.utils.wbc.WBCSDKTablesImpl;
import com.vasco.digipass.sdk.utils.wbc.WBCSDK;
import com.vasco.digipass.sdk.utils.wbc.WBCSDKConstants;
import com.vasco.digipass.sdk.utils.wbc.WBCSDKException;
import com.vasco.digipass.sdk.utils.wbc.WBCSDKTables;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.VascoError;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Created by ssadi on 23/03/18.
 */
public class WhiteBoxCryptography {
    //Create an initialization vector
    private static final byte[] initializationVector = { (byte) 0xA4, (byte) 0x45, (byte) 0x10, (byte) 0xA5, (byte) 0x57,
            (byte) 0xC4, (byte) 0x74, (byte) 0xB5, (byte) 0xE5, (byte) 0x65, (byte) 0xA7, (byte) 0x74,
            (byte) 0xF5, (byte) 0xA7, (byte) 0x74, (byte) 0xF5 };
    private static byte[] input;

    public static byte[] encrypted, decrypted;
    public static String encryptedString="", decryptedString="";
    public static VascoError vascoError = new VascoError();


    public static boolean encrypt(byte[] inputByte){
        boolean result = false;
        try {
        //Create a new WBCSDKTables object that will access your tables.
        //The WBCSDKTablesImpl file must be generated with the WBCSDKTableGenerator.exe
        WBCSDKTables tables = (WBCSDKTables) new WBCSDKTablesImpl();


            encrypted =   WBCSDK.encrypt(WBCSDKConstants.CRYPTO_MECHANISM_AES, WBCSDKConstants.CRYPTO_MODE_CTR, (WBCSDKTables) tables, initializationVector, inputByte);

                encryptedString = new String(encrypted,"UTF-8");

            result = true;
        } catch (WBCSDKException e) {
            e.printStackTrace();
            vascoError.code = e.getErrorCode()+"";
            vascoError.message = e.getMessage();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean encrypt(String inputString){
        input = inputString.getBytes();
        return encrypt(input);
    }

    public static boolean decrypt(byte[] inputByte){
        boolean result = false;
        try {
            //Create a new WBCSDKTables object that will access your tables.
            //The WBCSDKTablesImpl file must be generated with the WBCSDKTableGenerator.exe
            WBCSDKTables tables = (WBCSDKTables) new WBCSDKTablesImpl();

            decrypted =   WBCSDK.decrypt(WBCSDKConstants.CRYPTO_MECHANISM_AES, WBCSDKConstants.CRYPTO_MODE_CTR, (WBCSDKTables) tables, initializationVector, inputByte);
            decryptedString = new String(decrypted,"UTF-8");
            result = true;

        } catch (WBCSDKException e) {
            e.printStackTrace();
            vascoError.code = e.getErrorCode()+"";
            vascoError.message = e.getMessage();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean decrypt(String inputString){
        input = inputString.getBytes();
        return decrypt(input);
    }
}
