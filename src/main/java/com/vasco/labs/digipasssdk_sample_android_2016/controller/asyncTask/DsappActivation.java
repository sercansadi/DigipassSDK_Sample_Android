package com.vasco.labs.digipasssdk_sample_android_2016.controller.asyncTask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.vasco.labs.digipasssdk_sample_android_2016.ActivateActivity;
import com.vasco.labs.digipasssdk_sample_android_2016.R;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.Activate;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.DSAPP;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Constants;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Network;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.xmlParsers;
import com.vasco.labs.digipasssdk_sample_android_2016.model.DSAPPServer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ssadi on 06/03/18.
 */
public class DsappActivation extends AsyncTask<String, String, String> {
    public Context context = null;
    public Activity activity = null;
    String registrationIdentifier = "";
    String userIdentifier = "";
    String authorizationCode="";
    String userPassword = "";
    String password="";
    String initialVector="";
    String encryptedClientPublicKeyAndNonce="";
    String serial="";


    @Override
    protected String doInBackground(String... params) {
        publishProgress("Starting activation");
        userIdentifier = params[0];
        userPassword = params[1];
        password = params[2];

        try {
            if(DSAPP.validateSRPUserPasswordChecksum(userPassword,userIdentifier)) {
                InputStream inputStream = Network.performNetworkRequest(Constants.URL + "?action=" + Constants.ACTION_SRP_DSAPP_VALIDATE + "&userIdentity=" + userIdentifier);

                DSAPPServer dsappServer = xmlParsers.parseDSAPP(inputStream);

                DSAPP.generateSRPClientEphemeralKeyAndSessionKey(userPassword, dsappServer.serverEphemeralPublicKey, dsappServer.salt);

                inputStream = Network.performNetworkRequest(Constants.URL + "?action=" + Constants.ACTION_SRP_ACTIVATE_LICENSE_DSAPP + "&userIdentity=" + userIdentifier +
                        "&clientEmpPubKey=" + DSAPP.srpClientEphemeralKeyResponse.getClientEphemeralPublicKey() + "&clientEvidenceMessage=" +
                        DSAPP.srpSessionKeyResponse.getClientEvidenceMessage());

                dsappServer = xmlParsers.parseDSAPP(inputStream);

                if (DSAPP.verifySRPServerEvidenceMessageAndDecryptSRPData(dsappServer.serverEvidenceMessage, dsappServer.encryptedActivationData,
                        dsappServer.encryptionCounter, dsappServer.mac)) {
                    byte[] activationMessage1 = DSAPP.decryptedData;
                    Activate.activateLicence(new String(activationMessage1), context);
                    publishProgress("License activated");

                    InputStream inputStream2 = Network.performNetworkRequest(Constants.URL + "?action=" + Constants.ACTION_INSTANCE + "&userIdentity=" + userIdentifier + "&deviceCode=" + Activate.deviceCode + "&serialNumber=" + Activate.serial);
                    String instanceMessage = xmlParsers.parseInstance(inputStream2);
                    Boolean Activated = Activate.activateInstance(instanceMessage, password, context);
                    publishProgress("DIGIPASS activated");
                    //Toast.makeText(context,"DIGIPASS activated",Toast.LENGTH_LONG).show();
                } else {
                    publishProgress("error occured");
                    //Toast.makeText(context,DSAPP.vascoError.toString(),Toast.LENGTH_LONG).show();
                }
            }else{
                publishProgress("Password validation failed");
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String result) {

    }

    @Override
    protected void onProgressUpdate(String... text) {
        final TextView lblProgress = (TextView)activity.findViewById(R.id.lblProgress);
        lblProgress.setText(text[0]);
        // Things to be done while execution of long running operation is in
        // progress. For example updating ProgessDialog
    }
}
