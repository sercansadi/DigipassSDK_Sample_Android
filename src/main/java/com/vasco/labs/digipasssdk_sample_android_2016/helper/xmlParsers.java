package com.vasco.labs.digipasssdk_sample_android_2016.helper;

import com.vasco.labs.digipasssdk_sample_android_2016.model.DSAPPServer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

/**
 * Created by ssadi on 06/03/18.
 */
public class xmlParsers {

    private static XmlPullParser prepareParse(InputStream is){
        XmlPullParserFactory xmlFactoryObject;
        XmlPullParser result = null;
        try {
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myparser = xmlFactoryObject.newPullParser();

            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(is, null);

            result  = myparser;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return result;
    }
    public static DSAPPServer parseDSAPP(InputStream is){
        XmlPullParser myParser = prepareParse(is);
        DSAPPServer dsappServer = new DSAPPServer();

        int event;
        String text=null;

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();

                switch (event){
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        //OLD
                        if(name.equals("LicenseActivation")){

                            dsappServer.encryptedLicenseActivationMessage = myParser.getAttributeValue(null,"encryptedLicenseActivationMessage");
                            dsappServer.licenseActivationMessageIV = myParser.getAttributeValue(null,"licenseActivationMessageIV");
                            dsappServer.encryptedServerPublicKey = myParser.getAttributeValue(null,"encryptedServerPublicKey");
                            dsappServer.encryptedNonces = myParser.getAttributeValue(null,"encryptedNonces");
                            dsappServer.generateSessionKeyIV = myParser.getAttributeValue(null,"generateSessionKeyIV");
                        }

                        //NEW SRP
                        if(name.equals("SRPregistrationResponse")){
                            dsappServer.serverEphemeralPublicKey = myParser.getAttributeValue(null,"serverEphPublicKey");
                            dsappServer.salt = myParser.getAttributeValue(null,"salt");
                        }
                        if(name.equals("SRPactivationResponse")){
                            dsappServer.serverEvidenceMessage = myParser.getAttributeValue(null,"serverEvidenceMessage");
                            dsappServer.encryptedActivationData = myParser.getAttributeValue(null,"encryptedActivationData");
                            dsappServer.encryptionCounter = myParser.getAttributeValue(null,"encryptionCounter");
                            dsappServer.mac = myParser.getAttributeValue(null,"mac");
                        }


                        break;
                }
                event = myParser.next();
            }
            //parsingComplete = false;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return dsappServer;

    }

    public static long parseServerTime(InputStream is){
        XmlPullParser myParser = prepareParse(is);
        long result=0;
        int event;
        String text=null;

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();

                switch (event){
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals("DP4Mobile")){

                            text = myParser.getAttributeValue(null,"serverTime");
                            result = Long.parseLong(text);
                        }
                        break;
                }
                event = myParser.next();
            }
//            parsingComplete = false;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String parseInstance(InputStream is){
        XmlPullParser myParser = prepareParse(is);
        String result = "";

        int event;
        String text=null;

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();

                switch (event){
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals("InstanceActivation")){

                            result = myParser.getAttributeValue(null,"instanceActivationMessage");

                        }


                        break;
                }
                event = myParser.next();
            }
            //parsingComplete = false;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}


