package com.example.jms10.langstudykbrd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData.NetWordBundle;
import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData.NetWordData;
import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData.NetWordDictionary;
import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.ImageData.NetImageGetter;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private NetImageGetter imageGetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);
        imageGetter = new NetImageGetter("안녕");
        imageGetter.startGetting();;
    }

    class taskCl extends AsyncTask<Void, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Void... voids) {
            return imageGetter.getResult();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

}
