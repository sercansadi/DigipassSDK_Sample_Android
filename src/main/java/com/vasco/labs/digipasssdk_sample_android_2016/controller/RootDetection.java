package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import android.content.Context;
import android.content.res.AssetManager;

import com.vasco.g;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.VascoError;
import com.vasco.o;
import com.vasco.p;
import com.vasco.s;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ssadi on 28/02/18.
 */
public class RootDetection {
    public static VascoError vascoError = new VascoError();

    public static boolean isRooted(Context context){
        //true means the device is rooted;
        boolean result = true;
        String signFile = readSignatureFile(context);

        s rootDetectionResponse = p.s(signFile);

        int retCode = rootDetectionResponse.r();
        if (retCode == o.n)
        {
            result = false;
        }
        else if (retCode == o.r)
        {
            result = true;
        }
        else
        {

            vascoError.code=retCode+"";
            vascoError.message = "Root detection error";
        }

        return result;
    }

    public static String getVersion(Context context){
        String result = "";
        String signFile = readSignatureFile(context);
        // Get version of the signature file
        g respVersion = p.g(signFile);

        // Check if the version has been successfully retrieved
        if (respVersion.r() == o.n)
        {
            // Display it
            System.out.println("version " +respVersion.v());
            result = respVersion.v();
        }
        else
        {
            // Display error
//            txtSignatureResult.setText("Error while retrieving the version: "
//                    + getMsgFromReturnCode(returnCodeSign) + "\n" + "Error code: " + returnCodeSign);
        }

        return result;
    }


    private static String readSignatureFile(Context context){
        String result = "";

        try {
            InputStream inputStream = context.getAssets().open("root_detection/signature");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String read;
            while ((read = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(read);
            }
            inputStream.close();

            result = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;


    }

}
