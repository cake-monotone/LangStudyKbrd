package com.example.jms10.langstudykbrd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

public class Setting_Font extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferenceUtil sp = new SharedPreferenceUtil(getApplicationContext());
        setContentView(R.layout.activity_setting__font);
        SeekBar seekbar = (SeekBar)findViewById(R.id.fontseek);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView tv = (TextView)findViewById(R.id.seektv);
                switch(i){
                    case 0 :
                        tv.setText("아주 작게");
                        break;
                    case 1 :
                        tv.setText("작게");
                        break;
                    case 2 :
                        tv.setText("보통");
                        break;
                    case 3 :
                        tv.setText("크게");
                        break;
                    case 4 :
                        tv.setText("아주 크게");
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        Log.d("hihi", String.valueOf(sp.getTextSize()));
        TextView tv = (TextView)findViewById(R.id.seektv);
        if(sp.getTextSize() !=999) {
            seekbar.setProgress(sp.getTextSize());
            Log.d("hihi","hoho");
        }
        else{
            seekbar.setProgress(2);
        }

        int progressval = seekbar.getProgress();
        switch(progressval){
            case 0 :
                tv.setText("아주 작게");
                break;
            case 1 :
                tv.setText("작게");
                break;
            case 2 :
                tv.setText("보통");
                break;
            case 3 :
                tv.setText("크게");
                break;
            case 4 :
                tv.setText("아주 크게");
                break;
        }




    }
}
