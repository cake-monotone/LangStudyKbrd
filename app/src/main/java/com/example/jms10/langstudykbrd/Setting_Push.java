package com.example.jms10.langstudykbrd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Setting_Push extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting__push);
    }
    public void onSAClicked (View v) {
        Intent myIntent = new Intent(getApplicationContext(),StudyagainActivity.class);
        startActivity(myIntent);
    }
}
