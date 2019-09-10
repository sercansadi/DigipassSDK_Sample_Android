package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import android.content.Context;

import com.vasco.digipass.sdk.DigipassSDK;
import com.vasco.digipass.sdk.DigipassSDKConstants;
import com.vasco.digipass.sdk.models.SecureChannelMessage;
import com.vasco.digipass.sdk.responses.ActivationResponse;
import com.vasco.digipass.sdk.responses.DigipassPropertiesResponse;
import com.vasco.digipass.sdk.responses.GenerateDerivationCodeResponse;
import com.vasco.digipass.sdk.responses.MultiDeviceLicenseActivationResponse;
import com.vasco.digipass.sdk.responses.SecureChannelParseResponse;
import com.vasco.digipass.sdk.utils.securestorage.SecureStorageSDK;
import com.vasco.digipass.sdk.utils.securestorage.SecureStorageSDKException;
import com.vasco.labs.digipasssdk_sample_android_2016.goebl.david.Response;
import com.vasco.labs.digipasssdk_sample_android_2016.goebl.david.Webb;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Constants;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.NetworkUtility;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Salt;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.VascoError;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.vasco.labs.digipasssdk_sample_android_2016.controller.ChallengeResponse.response;

/**
 * Created by ssadi on 27/02/18.
 */
public class Activate {

    public static String deviceCode ="";
    public static String serial = "";
    public static VascoError vascoError = new VascoError();
    public static final String STAV = "380801070103564453021010101010101010100123456789ABCDEF03010104010405011006010107010108010309010F0A01010B01010C01000E01000F0100100101112F120100130101140101150C4170706C312020202020202016010117044080F8021801002901062A01062B01002C01021135120100130101140102150C4170706C32202020202020201601011704408BF8D21801011901062101062901062A01062B01002C0102115F120100130101140103150C4170706C33202020202020201601011704408BF8D21801081901011A01011B01011C01011D01011E01011F01012001012101102201102301102401102501102601102701102801102901062A01062B01002C0102";
    /////////////////////sadsadsadsads"38080072010346444F021019383194C1B5F4922A738534F846106B03010104010605010606010107010108010A0901010A01000B01010C01000E01000F0101100101380103112F120100130101140101150C4E4F5450202020202020202016010117040080D0021801002901062A01002B01002C0102"
    public static final String SERIAL = "1000039";
    public static final String ACODE = "081666212832552456825";
    public static final String APASS = "135791"; //"391314";//"131415";
    public static final String PASS = "28393"; //"b6da1aa";//"131415";
    public static final String XFAD = "38080072010346444F021019383194C1B5F4922A738534F846106B03010104010605010606010107010108010A0901010A01000B01010C01000E01000F0101100101380103112F120100130101140101150C4E4F5450202020202020202016010117040080D0021801002901062A01002B01002C01026127697013115163732449182059";//STAV+SERIAL+ACODE;
    public static final String XERC = null;
    public static final String NONCE = "654321"; //"9b99667e-ea74-4d81-9d03-13fa40f6f876";//"654321";
    protected static final Webb webb = Webb.create();

    private static class AlwaysTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) { }
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) { }
        public X509Certificate[] getAcceptedIssuers() { return null; }
    }

    public static boolean activateLicence(String activationMessage1,Context context){
        boolean result = false;
        deviceCode = "";
        serial="";

        long timeShift = 0;
        if(SecureStorage.readTimeShift(context)) {
            timeShift = SecureStorage.timeShift;
        }



        /**
         * Define the Static Vector
         */
        String staticVector = Constants.STATIC_VECTOR;

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
         * Parse the Activation message received from the server, and retract the SecureChannelMessage
         */
        SecureChannelParseResponse secureChannelParseResponse = DigipassSDK.parseSecureChannelMessage(activationMessage1);
        SecureChannelMessage secureChannelMessage = secureChannelParseResponse.getMessage();

        /**
         * Perform a License activation
         */
        MultiDeviceLicenseActivationResponse multiDeviceLicenseActivationResponse = DigipassSDK.multiDeviceActivateLicense(secureChannelMessage, staticVector, digipassFingerprint, DigipassSDKConstants.JAILBREAK_STATUS_NA, timeShift);

        /**
         * Check the response.
         * 0 = ok
         * get the deviceCode
         */
        if(multiDeviceLicenseActivationResponse.getReturnCode()==0){

            deviceCode =  multiDeviceLicenseActivationResponse.getDeviceCode();
            String s="";

            /**
             * Store the byte [] Static Vector & Dynamic Vector in the Secure Storage
             */
           if(SecureStorage.initWrite(multiDeviceLicenseActivationResponse.getStaticVector(), multiDeviceLicenseActivationResponse.getDynamicVector(),context)) {

               DigipassPropertiesResponse digipassPropertiesResponse = DigipassSDK.getDigipassProperties(multiDeviceLicenseActivationResponse.getStaticVector(), multiDeviceLicenseActivationResponse.getDynamicVector());
               serial = digipassPropertiesResponse.getSerialNumber();
               deviceCode = multiDeviceLicenseActivationResponse.getDeviceCode();
               result = true;
           }else{
               vascoError = SecureStorage.vascoError;
               return result;
           }
        }else{

            vascoError.code = ""+multiDeviceLicenseActivationResponse.getReturnCode();
            vascoError.message = DigipassSDK.getMessageForReturnCode(multiDeviceLicenseActivationResponse.getReturnCode());
        }


        return result;

    }

    public static boolean activateInstance(String activationMessage2, String password, Context context){
        boolean result = false;
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

        /**
         * Parse the Activation message2 received from the server, and retract the SecureChannelMessage
         */
        SecureChannelParseResponse secureChannelParseResponse = DigipassSDK.parseSecureChannelMessage(activationMessage2);
        SecureChannelMessage secureChannelMessage = secureChannelParseResponse.getMessage();

        /**
         * Perform a Instance activation
         */
        ActivationResponse activationResponse = DigipassSDK.multiDeviceActivateInstance(staticVector, dynamicVector, secureChannelMessage, password, digipassFingerprint);

        /**
         * get tge updated Dynamic Vector and store it in the Secure Storage
         */
        dynamicVector = activationResponse.getDynamicVector();
        SecureStorage.write(dynamicVector,context);

        /**
         * Check response
         * 0 = ok
         */
        if(activationResponse.getReturnCode()==0){
            result = true;
        }else{

            vascoError.code = ""+activationResponse.getReturnCode();
            vascoError.message = DigipassSDK.getMessageForReturnCode(activationResponse.getReturnCode());
        }
        return result;
    }

    public static boolean activateStandard(Context context){
        boolean result = false;
        deviceCode = "";
        serial="";
        byte[] staticVector = null;
        byte[] dynamicVector = null;
        long timeShift = 0;
        if(SecureStorage.readTimeShift(context)) {
            timeShift = SecureStorage.timeShift;
        }


        String digipassFingerprint ="";
        if(Salt.getDigipassSalt()){
            digipassFingerprint = DeviceBinding.getFingerprint(Salt.digipass_salt,context);
        }else{
            vascoError = Salt.vascoError;
            return result;
        }

        /*
        HttpGet httpGet = new HttpGet("https://deneme.com");
        HttpClient httpClient = NetworkUtility.getDefaultHttpClient(context, "https://deneme.com");
        try {
            HttpResponse response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }


        */
/*
        Webb.setGlobalHeader(Webb.HDR_USER_AGENT, Webb.DEFAULT_USER_AGENT);

        TrustManager[] trustAllCerts = new TrustManager[] { new AlwaysTrustManager() };
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        webb.setBaseUri(null);

        JSONObject payload = new JSONObject();
        try {
            payload.put("apass", APASS);
            payload.put("nonce", NONCE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Response<JSONObject> webResponse = webb
                .post("https://www.googleapis.com/oauth2/v1/certs")
                .ensureSuccess()
                .body(payload)
                .asJsonObject();
*/      //sadsadsadsads"38080072010346444F021019383194C1B5F4922A738534F846106B03010104010605010606010107010108010A0901010A01000B01010C01000E01000F0101100101380103112F120100130101140101150C4E4F5450202020202020202016010117040080D0021801002901062A01002B01002C0102"

        String xxFAD = "38080072010346444F021019383194C1B5F4922A738534F846106B03010104010605010606010107010108010A0901010A01000B01010C01000E01000F0101100101380103112F120100130101140101150C4E4F5450202020202020202016010117040080D0021801002901062A01002B01002C01026127697013115163732449182059";
        String xxERC = null;
        String xxNonce = "654321";//"9b99667e-ea74-4d81-9d03-13fa40f6f876";//null;
        //digipassFingerprint = "3352522BGYRDEZZZS";
/*
        JSONObject webResult = webResponse.getBody();
        try {
            xxFAD = webResult.getString("XFAD");
            xxERC = webResult.getString("XERC");
            xxNonce = webResult.getString("NONCE");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        //ActivationResponse response = DigipassSDK.activateOffline(STAV,SERIAL,ACODE,"123",APASS,PASS,dynamicVector);
        //ActivationResponse response = DigipassSDK.activateOnline(XFAD,XERC,APASS,NONCE,PASS,dynamicVector);
/*
        vascoError.code = "-4064";
        vascoError.message = DigipassSDK.getMessageForReturnCode((int)-4064);
*/
        ActivationResponse response = DigipassSDK.activateOnlineWithFingerprint(xxFAD,xxERC,APASS,xxNonce,PASS,digipassFingerprint,dynamicVector);
        if (response.getReturnCode()==0){
            if(SecureStorage.initWrite(response.getStaticVector(), response.getDynamicVector(),context)){
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

                vascoError.code = ""+response.getReturnCode();
                long serverTimeInSeconds = 63647644763L;
                long clientServerTimeShift = 0;
                clientServerTimeShift = DigipassSDK.computeClientServerTimeShiftFromServerTime(serverTimeInSeconds);
                GenerateDerivationCodeResponse derivationResponse = DigipassSDK.generateDerivationCode(
                        staticVector,
                        dynamicVector,
                        PASS,
                        0,
                        DigipassSDKConstants.CRYPTO_APPLICATION_INDEX_APP_1,
                        digipassFingerprint,
                        null);
                String test = derivationResponse.getDerivationCode();
            } else {
                vascoError.code = ""+response.getReturnCode();
                vascoError.message = DigipassSDK.getMessageForReturnCode(response.getReturnCode());
            }
        }else{

            vascoError.code = ""+response.getReturnCode();
            vascoError.message = DigipassSDK.getMessageForReturnCode(response.getReturnCode());
        }

        return result;
    }


    public static boolean activateLicenceNoSC(String activationMessage1,Context context){
        boolean result = false;
        deviceCode = "";
        serial="";

        long timeShift = 0;
        if(SecureStorage.readTimeShift(context)) {
            timeShift = SecureStorage.timeShift;
        }



        /**
         * Define the Static Vector
         */
        String staticVector = Constants.STATIC_VECTOR;

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
         * Parse the Activation message received from the server, and retract the SecureChannelMessage
         */
        SecureChannelParseResponse secureChannelParseResponse = DigipassSDK.parseSecureChannelMessage(activationMessage1);
        SecureChannelMessage secureChannelMessage = secureChannelParseResponse.getMessage();

        /**
         * Perform a License activation
         */
        MultiDeviceLicenseActivationResponse multiDeviceLicenseActivationResponse = DigipassSDK.multiDeviceActivateLicense(secureChannelMessage, staticVector, digipassFingerprint, DigipassSDKConstants.JAILBREAK_STATUS_NA, timeShift);

        /**
         * Check the response.
         * 0 = ok
         * get the deviceCode
         */
        if(multiDeviceLicenseActivationResponse.getReturnCode()==0){

            deviceCode =  multiDeviceLicenseActivationResponse.getDeviceCode();
            String s="";

            /**
             * Store the byte [] Static Vector & Dynamic Vector in the Secure Storage
             */
            if(SecureStorage.initWrite(multiDeviceLicenseActivationResponse.getStaticVector(), multiDeviceLicenseActivationResponse.getDynamicVector(),context)) {

                DigipassPropertiesResponse digipassPropertiesResponse = DigipassSDK.getDigipassProperties(multiDeviceLicenseActivationResponse.getStaticVector(), multiDeviceLicenseActivationResponse.getDynamicVector());
                serial = digipassPropertiesResponse.getSerialNumber();
                deviceCode = multiDeviceLicenseActivationResponse.getDeviceCode();
                result = true;
            }else{
                vascoError = SecureStorage.vascoError;
                return result;
            }
        }else{

            vascoError.code = ""+multiDeviceLicenseActivationResponse.getReturnCode();
            vascoError.message = DigipassSDK.getMessageForReturnCode(multiDeviceLicenseActivationResponse.getReturnCode());
        }


        return result;

    }


    public static boolean activateInstanceNoSC(String activationMessage2, String password, Context context){
        boolean result = false;
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

        /**
         * Parse the Activation message2 received from the server, and retract the SecureChannelMessage
         */
        SecureChannelParseResponse secureChannelParseResponse = DigipassSDK.parseSecureChannelMessage(activationMessage2);
        SecureChannelMessage secureChannelMessage = secureChannelParseResponse.getMessage();

        /**
         * Perform a Instance activation
         */
        ActivationResponse activationResponse = DigipassSDK.multiDeviceActivateInstance(staticVector, dynamicVector, secureChannelMessage, password, digipassFingerprint);

        /**
         * get tge updated Dynamic Vector and store it in the Secure Storage
         */
        dynamicVector = activationResponse.getDynamicVector();
        SecureStorage.write(dynamicVector,context);

        /**
         * Check response
         * 0 = ok
         */
        if(activationResponse.getReturnCode()==0){
            result = true;
        }else{

            vascoError.code = ""+activationResponse.getReturnCode();
            vascoError.message = DigipassSDK.getMessageForReturnCode(activationResponse.getReturnCode());
        }
        return result;
    }
}
