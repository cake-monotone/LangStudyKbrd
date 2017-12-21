package com.example.jms10.langstudykbrd.ColorPicker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.jms10.langstudykbrd.R;
import com.example.jms10.langstudykbrd.SharedPreferenceUtil;

/**
 * Created by Sojeong Jin on 21/12/2017.
 */

public class ColorPickerActivity extends Activity implements ColorPicker.OnColorChangedListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_color_picker);
        new ColorPicker(this, ColorPickerActivity.this, Color.WHITE).show();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void colorChanged(int color) {
        SharedPreferenceUtil util = new SharedPreferenceUtil(getApplicationContext());
        util.setKeyboardColour(color);
        Log.d("color", String.valueOf(color));
        finish();
    }
}
