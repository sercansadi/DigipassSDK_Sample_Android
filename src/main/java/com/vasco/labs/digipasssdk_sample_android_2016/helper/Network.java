package com.vasco.labs.digipasssdk_sample_android_2016.helper;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ssadi on 03/03/18.
 */
public class Network{


    public static InputStream performNetworkRequest(String callUrl) throws IOException {
        InputStream result = null;

        URL url = new URL(callUrl);
        if(callUrl.toLowerCase().contains("https://")) {
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(15000);

            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = in;
            }catch(Exception e){
                InputStream error =  new BufferedInputStream(urlConnection.getErrorStream());

            }
            finally {
                urlConnection.disconnect();
            }
        }else{
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(15000);
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = in;
            }catch(Exception e){
                InputStream error =  new BufferedInputStream(urlConnection.getErrorStream());

            }
            finally {
                urlConnection.disconnect();
            }
        }

            return result;
    }


    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }


}
