package com.example.jms10.langstudykbrd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData.NetWordData;

import java.util.ArrayList;

public class SelectTransformActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transform);

        Intent intent = getIntent();
        int pos = intent.getExtras().getInt("POS");
        String word = intent.getExtras().getString("WORD");

        listView = (ListView) findViewById(R.id.select_transform_listView);
        ArrayList<String> list = new ArrayList<String>();

        if (pos == NetWordData.NOUN) {
            String[] postpor = {"은", "는", "이", "가", "에", "에서", "을", "으로", "과", "의"};

            for (String pp : postpor) {
                list.add(word + pp);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l_pos) {
                String result = ((AppCompatTextView) view).getText().toString();

                Intent intent = new Intent();
                intent.putExtra("RESULT", result);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
