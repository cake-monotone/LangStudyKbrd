package com.example.jms10.langstudykbrd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData.NetWordBundle;
import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData.NetWordData;
import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData.NetWordDictionary;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NetWordDictionary dictionary = new NetWordDictionary();
        dictionary.setTargetWord("live");
        dictionary.startFindWordOnNet();
        Thread thread = new Thread() {
            public void run() {
                Log.d("DEBUGINGDD", "시작");
                NetWordBundle bundle = dictionary.getResult();
                Log.d("DEBUGINGDD", "완성");
                for (NetWordData data : bundle.getWordDatas()) {
                    for (String meanings : data.getMeanings())
                        Log.d("DEBUGINGDD", meanings);
                }
            }
        };

        thread.start();
    }
}
