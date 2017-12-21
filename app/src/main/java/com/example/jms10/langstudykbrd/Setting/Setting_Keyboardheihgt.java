package com.example.jms10.langstudykbrd.Setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.jms10.langstudykbrd.R;
import com.example.jms10.langstudykbrd.SharedPreferenceUtil;

public class Setting_Keyboardheihgt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferenceUtil sp = new SharedPreferenceUtil(getApplicationContext());
        setContentView(R.layout.activity_setting__keyboardheihgt);
        final SeekBar seekbar = (SeekBar)findViewById(R.id.keyboardseek);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView tv = (TextView)findViewById(R.id.keyboardseektv);
                int val = seekbar.getProgress();
                val += 10;
                Log.d("hihi", String.valueOf(val));
                tv.setText(val/10+"."+val%10+"배");
                sp.setKeyboardHeight(val);
                Log.d("hihi", "second : "+String.valueOf(sp.getKeyboardHegiht()));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        Log.d("hihi", String.valueOf(sp.getKeyboardHegiht()));
        TextView tv = (TextView)findViewById(R.id.keyboardseektv);
        if(sp.getKeyboardHegiht() !=10) {
            seekbar.setProgress(sp.getKeyboardHegiht()-10);
            Log.d("hihi","hoho");
        }
        else{
            seekbar.setProgress(0);
        }
        int val = seekbar.getProgress();
        val += 10;
        tv.setText(val/10+"."+val%10+"배");
    }
}
