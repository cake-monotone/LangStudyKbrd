package com.example.jms10.langstudykbrd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Setting_Dic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting__dic);
    }
    public void onPicClicked (View v) {
        Intent myIntent = new Intent(this, PicActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myIntent);
    }
    public void onCoolTimeClicked (View v) {
        Intent myIntent = new Intent(this, DictimeActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myIntent);
    }
    public void onFontClicked (View v) {
        Intent myIntent = new Intent(getApplicationContext(), Setting_Font.class);
        startActivity(myIntent);
    }
}
