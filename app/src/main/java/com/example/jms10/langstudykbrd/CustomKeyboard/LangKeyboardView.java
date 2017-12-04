package com.example.jms10.langstudykbrd.CustomKeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

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

        canvas.drawARGB(40, 0, 0, 0);//키보드 전체 색 설정. a = 33
    }
}
