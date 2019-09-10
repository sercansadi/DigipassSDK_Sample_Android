package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import com.vasco.dsapp.client.DSAPPClient;
import com.vasco.dsapp.client.exceptions.DSAPPException;
import com.vasco.dsapp.client.responses.GenerateSessionKeyResponse;
import com.vasco.dsapp.client.responses.InitializeProtocolResponse;
import com.vasco.dsapp.client.responses.SRPClientEphemeralKeyResponse;
import com.vasco.dsapp.client.responses.SRPSessionKeyResponse;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.VascoError;

/**
 * Created by ssadi on 04/03/18.
 */
public class DSAPP {
    public static InitializeProtocolResponse initializeProtocolResponse;
    public static GenerateSessionKeyResponse generateSessionKeyResponse;
    public static SRPClientEphemeralKeyResponse srpClientEphemeralKeyResponse;
    public static SRPSessionKeyResponse srpSessionKeyResponse;
    public static byte[] decryptedData = null;
    public static VascoError vascoError = new VascoError();
    public static String userIdentity = "";


    public static boolean validateSRPUserPasswordChecksum(String userPassword,String userIdentityImp){
        boolean result = false;
        userIdentity = userIdentityImp;

        try {
            DSAPPClient.validateSRPUserPasswordChecksum(userPassword);
            result = true;
        } catch (DSAPPException e) {
            e.printStackTrace();
            vascoError.code = e.getErrorCode()+"";
            vascoError.message = e.getMessage();
        }


        return result;
    }

    public static boolean generateSRPClientEphemeralKeyAndSessionKey(String userPassword,String serverEphemeralPublicKey, String salt){
        boolean result = false;
        String clientPubKey = "";
        String vMsg = "";
        try {
            srpClientEphemeralKeyResponse = DSAPPClient.generateSRPClientEphemeralKey();
            clientPubKey = srpClientEphemeralKeyResponse.getClientEphemeralPublicKey();
            srpSessionKeyResponse = DSAPPClient.generateSRPSessionKey(srpClientEphemeralKeyResponse.getClientEphemeralPublicKey(),
                    srpClientEphemeralKeyResponse.getClientEphemeralPrivateKey(), serverEphemeralPublicKey, userIdentity, userPassword, salt);
            vMsg = srpSessionKeyResponse.getClientEvidenceMessage();
            result = true;
        } catch (DSAPPException e) {
            e.printStackTrace();
            vascoError.code = e.getErrorCode()+"";
            vascoError.message = e.getMessage();
        }


        return result;
    }

    public static boolean verifySRPServerEvidenceMessageAndDecryptSRPData(String serverEvidenceMessage,String encryptedData, String encryptionCounter, String mac){
        boolean result = false;

        try {
            DSAPPClient.verifySRPServerEvidenceMessage(srpClientEphemeralKeyResponse.getClientEphemeralPublicKey(),srpSessionKeyResponse.getClientEvidenceMessage(),
                   serverEvidenceMessage,srpSessionKeyResponse.getSessionKey());
            decryptedData = DSAPPClient.decryptSRPData(srpSessionKeyResponse.getSessionKey(),encryptedData,encryptionCounter,mac);

            result = true;
        } catch (DSAPPException e) {
            e.printStackTrace();
            vascoError.code = e.getErrorCode()+"";
            vascoError.message = e.getMessage();
        }


        return result;
    }
}
