package com.example.jms10.langstudykbrd.Translation;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Sojeong Jin on 06/12/2017.
 */

public class Translate extends AppCompatActivity {

    public static class Translation extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... contents) {

            Log.d("test", "show");
            String result = "";
            String clientId = "2qfcuefDmoUdMafl8I8W";
            String clientSecret = "";//이용시 secret 입력.
            try {
                Log.d("test", "I tried");
                String text = URLEncoder.encode(contents[0], "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/language/translate";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                // post request
                String postParams = "source=en&target=ko&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                result += response.toString() + "\n";

                Log.d("test", response.toString());
                //System.out.println(response.toString());
            } catch (Exception e) {
                Log.d("test", e.toString());
                Log.d("test", "failed");
            }

            String res="";
            try {
                JSONObject resj = new JSONObject(result);
                res = resj.get("message").toString();
                resj = new JSONObject(res);
                res = resj.get("result").toString();
                resj = new JSONObject(res);
                res = resj.get("translatedText").toString();
            }catch(JSONException e){
                e.printStackTrace();
            }

            return res;
        }

        @Override
        protected void onProgressUpdate(Void... values) {


            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            handleVal(s);
        }

        private String handleVal(String s){
            return s;
        }
    }



}
