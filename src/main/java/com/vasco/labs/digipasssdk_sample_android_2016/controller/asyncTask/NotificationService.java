package com.vasco.labs.digipasssdk_sample_android_2016.controller.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.vasco.labs.digipasssdk_sample_android_2016.controller.Notification;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Constants;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Network;

import java.io.IOException;

/**
 * Created by ssadi on 09/03/18.
 */
public class NotificationService extends AsyncTask<String, String, String> {
    public Context context;
    String serverRegistration = "";
    @Override
    protected String doInBackground(String... params) {
        serverRegistration = params[0];


        if(Notification.getvascoNotificationIdentifier(context)){
            try {
                Network.performNetworkRequest(Constants.URL + "?action=" + Constants.ACTION_REGISTER_NOTIFICATION
                        +"&NotificationIdentifier="+Notification.vascoNotificationIdentifier+"&serverRegistration="+serverRegistration);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
