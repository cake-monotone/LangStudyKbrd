package com.example.jms10.langstudykbrd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.ImageData.NetImageGetter;
import com.example.jms10.langstudykbrd.BaseLibrary.DataFromSQL.DictionaryData.SQLWordDictionary;

import java.util.ArrayList;
import java.util.List;

// 디버깅용 액티비티
public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private NetImageGetter imageGetter;
    private ListView listView;
    private EditText editText;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listView = (ListView) findViewById(R.id.listView);
        this.editText = (EditText) findViewById(R.id.editText);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        this.listView.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                SQLWordDictionary dictionary = SQLWordDictionary.getInstance(getApplicationContext());
                dictionary.open();
                List<String> words = dictionary.getWordList(editable.toString());
                dictionary.close();

                adapter.clear();
                if (words != null && words.size() >= 1)
                    adapter.addAll(words);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void clk1(View v) {
        Intent intent = new Intent(this, DictionaryActivity.class);
        startActivity(intent);
    }

}
