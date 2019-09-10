package com.vasco.labs.digipasssdk_sample_android_2016.helper;

/**
 * Created by ssadi on 08/03/18.
 */
public class VascoError {
    public static String code = "";
    public static String message = "";

    @Override
    public String toString(){
        return code + ": "+message;
    }
}
