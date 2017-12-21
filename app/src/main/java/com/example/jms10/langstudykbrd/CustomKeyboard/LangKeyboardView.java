package com.example.jms10.langstudykbrd.CustomKeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import com.example.jms10.langstudykbrd.SharedPreferenceUtil;

/**
 * Created by Sojeong Jin on 30/11/2017.
 */

public class LangKeyboardView extends KeyboardView {

    Context context;

    public LangKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LangKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        SharedPreferenceUtil util = new SharedPreferenceUtil(getContext());

        int RGBint = util.getKeyboardColour();

        int Blue =  RGBint & 255;
        int  Green = (RGBint >> 8) & 255;
        int Red =   (RGBint >> 16) & 255;

        canvas.drawARGB(40, Red, Green, Blue);//키보드 전체 색 설정. a = 33
    }
}
