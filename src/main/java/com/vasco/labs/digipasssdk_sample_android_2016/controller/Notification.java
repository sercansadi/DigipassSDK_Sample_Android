package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.vasco.digipass.sdk.utils.notification.client.NotificationSDKClient;
import com.vasco.digipass.sdk.utils.notification.client.exceptions.NotificationSDKClientException;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Constants;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.VascoError;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ssadi on 02/03/18.
 * Add a file called "vasco-notifications.properties" to ../app/src/main/assets/
 * Containing:
 notificationMIMEType=application/vnd.com.vasco.notification.NOTIFICATION_ACTIVITY
 notificationIconName=ic_notification.png
 *
 * Put "ic_notification.png" into ../app/src/res/drawable
 *
 *
 * Add following in AndroidManifest.xml
 * to the main activity
 <intent-filter>
 <action android:name="android.intent.action.VIEW" />
 <category android:name="android.intent.category.DEFAULT" />
 <data android:mimeType="application/vnd.com.vasco.notification.NOTIFICATION_ACTIVITY"/>
 </intent-filter>
 *
 * At the end of the file before end of the application
 *
 <meta-data
 android:name= "com.google.android.gms.version"
 android:value= "@integer/google_play_services_version" />
 <receiver android:name="com.vasco.digipass.sdk.utils.notification.client.GcmBroadcastReceiver"
 android:permission= "com.google.android.c2dm.permission.SEND" > <intent-filter>
 <action android:name= "com.google.android.c2dm.intent.RECEIVE" /> <category
 android:name= "com.vasco.digipass.sdk.utils.notification.client" /> </intent-filter>
 </receiver>
 <service android:name="com.vasco.digipass.sdk.utils.notification.client.GcmIntentService" />
 *
 *
 * Add following to app.gradle
 compile 'com.google.android.gms:play-services-gcm:8.4.0'
 compile 'com.android.support:appcompat-v7:23.1.1'
 *
 */
public class Notification {

    public static String vascoNotificationIdentifier ="";
    public static VascoError vascoError = new VascoError();

    public static boolean getvascoNotificationIdentifier(Context context){
        // Register to Google Cloud Messaging by providing the PROJECT_NUMBER
        // and retrieving the VASCO Notification Identifier that must be provided
        // to the NotificationSDKServer library to send a notification to this particular device.

        boolean result=false;
        try {
            vascoNotificationIdentifier = NotificationSDKClient.registerNotificationService(context, Constants.PROJECT_NUMBER);
            result = true;

        } catch (NotificationSDKClientException e) {
            e.printStackTrace();
            vascoError.code = ""+e.getErrorCode();
            vascoError.message = e.getMessage();
        }
        return result;
    }

    private void registervascoNotificationIdentifier(){



    }

}
