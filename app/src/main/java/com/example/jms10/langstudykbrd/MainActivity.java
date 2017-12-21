package com.example.jms10.langstudykbrd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.ImageData.NetImageGetter;
import com.example.jms10.langstudykbrd.BaseLibrary.DataFromSQL.PushNotiData.PushNotiData;
import com.example.jms10.langstudykbrd.BaseLibrary.DataFromSQL.PushNotiData.SQLPushNotiHelper;
import com.example.jms10.langstudykbrd.Notification.NotificationService;

import java.util.Calendar;

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

        editText = (EditText) findViewById(R.id.editText);
    }
    public void onprefClicked(View v) {
        Intent intent = new Intent(this, SettingmenuActivity.class);
        startActivity(intent);
    }

    public void clk1(View v) {
        Intent intent = new Intent(this, DictionaryActivity.class);
        startActivity(intent);
    }

    public void clk2(View v) {
        Intent intent = new Intent(this, WordSelectActivity.class);
        intent.putExtra(WordSelectActivity.EXTRA_WORD_STRING_KEY, editText.getText().toString());
        startActivity(intent);
    }

    public void clk3(View v) {
        SQLPushNotiHelper helper = new SQLPushNotiHelper(this);
        helper.open();
        helper.pushNotice(new PushNotiData(editText.getText().toString(),"Dfafd", Calendar.getInstance().getTime()), 0);
        helper.close();

        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
    }
}
