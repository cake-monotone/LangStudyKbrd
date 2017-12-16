package com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.ImageData;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by jms10 on 2017-12-01.
 */

public class NetImageGetter {
    private final String QueryURL =
            "https://www.googleapis.com/customsearch/v1?" +
                    "cx=009472879444312224916:xw-7x6dt3fk&key=AIzaSyBdhxwYSsSWPmNagyHwdm4yEkPyBTUHb4M&" +
                    "imgSize=small&searchType=image&num=1&q=";
    private String targetWord;
    private Bitmap result;


    public NetImageGetter(String targetWord) {
        this.targetWord = targetWord;
    }

    // 쓰레드 관련 작업 스레드
    public void startGetting() {
        this.taskThread.start();
    }

    /**
     * 스레드를 기다려서 결과가 나오면, 반환합니다.
     * @return 결과 비트맵
     */
    @Nullable
    public Bitmap getResult() {
        try {
            this.taskThread.join();
        } catch (InterruptedException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    // 이미지 얻어오는 쓰레드
    private Thread taskThread = new Thread() {
        @Override
        public void run() {
            try {
                HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(QueryURL + URLEncoder.encode(targetWord, "UTF-8")).openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String tmpStr;
                while ((tmpStr = bufferedReader.readLine())!= null)
                    stringBuilder.append(tmpStr);

                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                String resurl = jsonObject.getJSONArray("items").getJSONObject(0).getString("link");

                URL imgURL = new URL(resurl);
                InputStream imageStream = imgURL.openStream();
                result = BitmapFactory.decodeStream(imageStream);
            } catch (MalformedURLException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
    };
}
