package com.mrk.karan.securityanalyzer;


import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Created by karan on 6/18/15.
 */
public class get_app_metadata {

        public static org.json.JSONObject getJsonObject(URL url) throws Exception
        {
            // to send the http request to the url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String str;
            StringBuffer stringBuffer = new StringBuffer();
            // to obtain the JSON response
            while ((str = bufferReader.readLine()) != null)
            {
                stringBuffer.append(str);
            }
            str = stringBuffer.toString();
            org.json.JSONObject obj = new org.json.JSONObject(str);
            return obj;
        }

        public void rating() throws Exception{
            String myUrl = "https://42matters.com/api/1/apps/lookup.json?p=com.whatsapp&access_token=e9ec1d32e7e8fffdfef4bd5ae9b45a06f2d4f9a2";

            URL url = new URL(myUrl);
            org.json.JSONObject obj = getJsonObject(url);

            String d = obj.getString("category");
            Double rating = obj.getDouble("rating");

            System.out.println(d);
            System.out.println(rating);

            Log.d("","category " +d+ " rating "+String.valueOf(rating));

        }
    }

