package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.vasco.digipass.sdk.DigipassSDK;
import com.vasco.digipass.sdk.utils.qrcodescanner.QRCodeScannerSDKActivity;
import com.vasco.digipass.sdk.utils.qrcodescanner.QRCodeScannerSDKConstants;
import com.vasco.digipass.sdk.utils.qrcodescanner.QRCodeScannerSDKErrorCodes;
import com.vasco.digipass.sdk.utils.qrcodescanner.QRCodeScannerSDKException;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.VascoError;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by ssadi on 29/02/18.
 * Add
 * <uses-permission android:name="android.permission.CAMERA"/>
 * <uses-permission android:name="android.permission.VIBRATE"/>
 * to AndroidManifest.xml
 * add the c libraries to ../app/src/main/jniLibs
 *
 */
public class QRCodeScanner {
    public static String message = "";
    public static VascoError vascoError = new VascoError();

    public static Intent StartScanner(Context context) {
        Intent intent = new Intent(context, QRCodeScannerSDKActivity.class);

        // Indicates that we want to be able to scan both QR and Cronto codes
        intent.putExtra(QRCodeScannerSDKConstants.EXTRA_CODE_TYPE, QRCodeScannerSDKConstants.QR_CODE
                + QRCodeScannerSDKConstants.CRONTO_CODE);

        // Indicates that the device must vibrate when a code is scanned
        intent.putExtra(QRCodeScannerSDKConstants.EXTRA_VIBRATE, true);

        return intent;

    }

    public static Boolean parseResult(int resultCode, Intent data){
        Boolean result=false;

        // Display the result
        switch (resultCode)
        {

            // In case of success
            case Activity.RESULT_OK:

                // Gets the scanned code
                message = data.getStringExtra(QRCodeScannerSDKConstants.OUTPUT_RESULT);

                result = true;

                break;

            // The end-used has pressed the back button
            case Activity.RESULT_CANCELED:
                // Nothing to do here
                break;

            // In case of error
            case QRCodeScannerSDKConstants.RESULT_ERROR:

                // Gets the raised exception
                QRCodeScannerSDKException exception = (QRCodeScannerSDKException) data
                        .getSerializableExtra(QRCodeScannerSDKConstants.OUTPUT_EXCEPTION);

                // Displays the error code
                int errorCode = exception.getErrorCode();
                vascoError.code = ""+exception.getErrorCode();
                vascoError.message = exception.getMessage();


                break;
        }

        return result;
    }
}
