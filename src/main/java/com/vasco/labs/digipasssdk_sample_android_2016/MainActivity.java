package com.vasco.labs.digipasssdk_sample_android_2016;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import com.vasco.digipass.sdk.DigipassSDKConstants;
import com.vasco.digipass.sdk.utils.biometricsensor.BiometricSensorSDK;
import com.vasco.digipass.sdk.utils.wbc.WBCSDK;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.Activate;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.Biometrics;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.DSAPP;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.DigipassInformation;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.Geolocation;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.RootDetection;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.SecureStorage;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.WhiteBoxCryptography;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.asyncTask.TimeSyncronisation;


import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final TextView txtLocation = (TextView)findViewById(R.id.txtLocation);

        /**
         * Check permissions
         */
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE=-1;
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        /**
         * Step1: check if the DIGIPASS is activated
         */
        checkStatus();

        findViewById(R.id.btnActivateDIGIPASS).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivateActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnActivateDIGIPASS_2StepNoCroNoSC).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TwoStepNoCroNoSCActivateActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnActivateDIGIPASS_Standard).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StandardActivateActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnSecureChannelMessage).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecureMessagingActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnOneTimePassword).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OTPActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnChallengeResponse).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChallengeResponseActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnSignature).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignatureActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnNotification).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificiationActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.btnTimeSync).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TimeSyncronisation ts = new TimeSyncronisation();
                ts.context = MainActivity.this;
                ts.activity = MainActivity.this;
                ts.execute();

            }
        });

        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SecureStorage.clearSecureStorage(MainActivity.this);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        findViewById(R.id.btnGeo).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Geolocation.locate(MainActivity.this)){
                    txtLocation.setText(Geolocation.coordinates);
                }else{
                    Toast.makeText(MainActivity.this, Geolocation.vascoError.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnSecurity).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecurityActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnPlayGround).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                playGround();

            }
        });


    }
    @Override
    protected void onStart(){
        super.onStart();
        checkStatus();

    }

    private void checkStatus(){
        TextView txtStatus = (TextView)findViewById(R.id.txtStatus);
        TextView txtSerial = (TextView)findViewById(R.id.txtSerial);
        TextView txtTimeShift = (TextView)findViewById(R.id.txtTimeShift);
        TextView txtRooted = (TextView)findViewById(R.id.txtRooted);
        Button btnOneTimePassword = (Button)findViewById(R.id.btnOneTimePassword);
        Button btnChallengeResponse = (Button)findViewById(R.id.btnChallengeResponse);
        Button btnSecureChannelMessage = (Button)findViewById(R.id.btnSecureChannelMessage);
        Button btnSignature = (Button)findViewById(R.id.btnSignature);

        Boolean activated = false;

        try {
            if(DigipassInformation.get(this)) {
                String serial = DigipassInformation.serialNumber;
                int status = DigipassInformation.status;
                switch (status) {
                    case DigipassSDKConstants.STATUS_ACTIVATED:


                        txtStatus.setText("Activated");
                        txtSerial.setText(DigipassInformation.serialNumber + ":" + DigipassInformation.sequenceNumber);
                        activated = true;
                        break;
                    case DigipassSDKConstants.STATUS_GENERATE_INVALID_OTP:
                        txtStatus.setText(("Generating invalid OTP"));

                        break;
                    case DigipassSDKConstants.STATUS_LOCKED:
                        txtStatus.setText(("Locked"));
                        break;
                    case DigipassSDKConstants.STATUS_PRE_ACTIVATED:
                        txtStatus.setText(("Pre-Activated"));
                        break;

                    case DigipassSDKConstants.STATUS_NOT_ACTIVATED:
                        txtStatus.setText("Not Activated");
                        break;
                    default:
                        txtStatus.setText("");
                        txtSerial.setText("");
                        break;

                }
            }else {
                txtStatus.setText("Not Activated");
                txtSerial.setText("");
            }

        }catch(Exception e){
            e.printStackTrace();
            txtStatus.setText("Not Activated");
        }

        try{
            if(SecureStorage.readTimeShift(this)){
                txtTimeShift.setText(SecureStorage.timeShift + "");
            }
        }catch(Exception e){

        }

        if(RootDetection.isRooted(this)){
            txtRooted.setText("YES");
            txtRooted.setTextColor(Color.RED);
        }else{
            txtRooted.setText("No");
            txtRooted.setTextColor(Color.BLUE);
        }

        if(activated){
            btnOneTimePassword.setEnabled(true);
            btnChallengeResponse.setEnabled(true);
            btnSecureChannelMessage.setEnabled(true);
            btnSignature.setEnabled(true);

        }else{
            btnOneTimePassword.setEnabled(false);
            btnChallengeResponse.setEnabled(false);
            btnSecureChannelMessage.setEnabled(false);
            btnSignature.setEnabled(false);
        }
    }


    private void playGround(){
        /**
         * test
         */

        String s = "hallo";
        try {
            byte[] array = s.getBytes();
            if(WhiteBoxCryptography.encrypt(array)){
                if(WhiteBoxCryptography.decrypt(WhiteBoxCryptography.encrypted)){
                    array = WhiteBoxCryptography.decrypted;
                    String str = new String(array);
                }
            }
            s = "";

//            String userPassword = "";
//            String userIdentikey = "";
//            String salt = "";
//            String serverKey = "";
//            String servEvMsg = "";
//            String eData = "";
//            String eCounter = "";
//            String mac = "";
//            if (DSAPP.validateSRPUserPasswordChecksum(userPassword, userIdentikey)) {
//                boolean t = true;
//                if (DSAPP.generateSRPClientEphemeralKeyAndSessionKey(userPassword, serverKey, salt)) {
//                    t = true;
//
//                    DSAPP.verifySRPServerEvidenceMessageAndDecryptSRPData(servEvMsg, eData, eCounter, mac);
//                    String s = new String(DSAPP.decryptedData);
//                    Activate.activateLicence(new String(DSAPP.decryptedData), this);
//
//                    t = true;
//                }
//
//            }

            Toast.makeText(MainActivity.this, "playGround finished", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "playGround finished with exception", Toast.LENGTH_SHORT).show();
        }
    }
}
