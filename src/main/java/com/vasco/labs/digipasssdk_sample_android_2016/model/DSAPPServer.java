package com.vasco.labs.digipasssdk_sample_android_2016.model;

/**
 * Created by ssadi on 06/03/18.
 */
public class DSAPPServer {
    //OLD
    public String encryptedLicenseActivationMessage="";
    public String licenseActivationMessageIV="";
    public String encryptedServerPublicKey="";
    public String encryptedNonces="";
    public String generateSessionKeyIV="";

    //NEW SRP
    public String userPassword="";
    public String userIdentity="";
    public String serverEphemeralPublicKey="";
    public String salt="";
    public String serverEvidenceMessage="";
    public String encryptedActivationData="";
    public String encryptionCounter="";
    public String mac="";
}
