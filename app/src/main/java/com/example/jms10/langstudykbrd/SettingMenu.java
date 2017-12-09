package com.example.jms10.langstudykbrd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SettingMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_menu);
    }
    public void onDicClicked (View v) {
        Intent myIntent = new Intent(getApplicationContext(), Setting_Dic.class);
        startActivity(myIntent);
    }
    public void onKbrClicked (View v) {
        Intent myIntent = new Intent(this, SettingkbrActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myIntent);
    }
    public void onWordClicked (View v) {
        Intent myIntent = new Intent(getApplicationContext(), Search_Word.class);
        startActivity(myIntent);
    }
    public void onPushClicked (View v) {
        Intent myIntent = new Intent(getApplicationContext(), Setting_Push.class);
        startActivity(myIntent);
    }
}
