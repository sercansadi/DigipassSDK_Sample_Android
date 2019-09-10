package com.vasco.labs.digipasssdk_sample_android_2016.helper;

import com.vasco.labs.digipasssdk_sample_android_2016.controller.WhiteBoxCryptography;

/**
 * Created by ssadi on 25/03/18.
 */
public class Salt {

    public static VascoError vascoError = new VascoError();
    public static String digipass_salt = "";
    public static String storage_salt = "";

    public static boolean getDigipassSalt(){
        Boolean result = false;

        if(Constants.USE_WBC){
            if(WhiteBoxCryptography.decrypt(Constants.DIGIPASS_ACTION_SALT_BYTE)){
                digipass_salt=WhiteBoxCryptography.decryptedString;
            }else{
                vascoError = WhiteBoxCryptography.vascoError;
                return result;
            }
        }else {
            digipass_salt=Constants.DIGIPASS_ACTION_SALT;
        }
        result = true;

        return result;
    }

    public static boolean getSecStorSalt(){
        Boolean result = false;

        if(Constants.USE_WBC){
            if(WhiteBoxCryptography.decrypt(Constants.SECURE_STORAGE_ACTION_SALT_BYTE)){
                storage_salt=WhiteBoxCryptography.decryptedString;
            }else{
                vascoError = WhiteBoxCryptography.vascoError;
                return result;
            }
        }else {
            storage_salt=Constants.SECURE_STORAGE_ACTION_SALT;
        }
        result = true;

        return result;
    }
}
