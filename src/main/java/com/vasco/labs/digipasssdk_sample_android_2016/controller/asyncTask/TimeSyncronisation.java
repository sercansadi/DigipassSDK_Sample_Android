package com.vasco.labs.digipasssdk_sample_android_2016.controller.asyncTask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.vasco.labs.digipasssdk_sample_android_2016.R;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.SecureStorage;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.TimeSync;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Constants;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.Network;
import com.vasco.labs.digipasssdk_sample_android_2016.helper.xmlParsers;
import com.vasco.labs.digipasssdk_sample_android_2016.model.DSAPPServer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ssadi on 06/03/18.
 */
public class TimeSyncronisation extends AsyncTask<String, String, String> {
    public Context context = null;
    public Activity activity = null;
    @Override
    protected String doInBackground(String... params) {

        InputStream inputStream = null;
        try {
            inputStream = Network.performNetworkRequest(Constants.URL + "?action=" + Constants.ACTION_GETSERVER_TIME);
            long serverTime = xmlParsers.parseServerTime(inputStream);

            System.out.println("serverTime= "+serverTime+"");
            if(serverTime!=0) {
                long timeShift = TimeSync.calculate(serverTime);
                System.out.println("timeShift= "+timeShift+"");
                publishProgress(timeShift + "");
                SecureStorage.writeTimeShift(timeShift, context);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(String... text) {
        final TextView txtTimeShift = (TextView)activity.findViewById(R.id.txtTimeShift);
        txtTimeShift.setText(text[0]);
        // Things to be done while execution of long running operation is in
        // progress. For example updating ProgessDialog
    }
}
