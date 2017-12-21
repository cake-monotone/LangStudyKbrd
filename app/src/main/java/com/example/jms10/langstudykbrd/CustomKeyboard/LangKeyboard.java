package com.example.jms10.langstudykbrd.CustomKeyboard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;

/**
 * Created by Sojeong Jin on 30/11/2017.
 */

public class LangKeyboard extends Keyboard {

    private Key mSpaceKey;
    private Key mModeChangeKey;
    private Key mSavedModeChangeKey;

    public LangKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public LangKeyboard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
        Key key = new LangKey(res, parent, x, y, parser);
        if(key.codes[0] == ' '){
            mSpaceKey = key;
        } else if(key.codes[0] == Keyboard.KEYCODE_MODE_CHANGE){
            mModeChangeKey = key;
            mSavedModeChangeKey = new LangKey(res, parent, x, y, parser);
        }
        return key;
    }




    static class LangKey extends Keyboard.Key{

        public LangKey(Resources res, Keyboard.Row parent, int x, int y,
                       XmlResourceParser parser) {
            super(res, parent, x, y, parser);
        }
    }

    @Override
    public int getHeight() {
        return getKeyHeight()*4;
    }

    public void changeKeyHeight(double height_modifier, boolean flag){
        int height = 0;
        int[] arr = {0, 156, 312, 468};
        int cnt =0;
        int idx=0;

        for(Keyboard.Key key : getKeys()) {
            key.height = 156;
            key.height *= height_modifier;
            key.y = arr[idx];
            key.y *= height_modifier;
            height = key.height;
            if(flag){if(cnt == 9 || cnt == 18|| cnt == 27)idx++;}
            else {if(cnt==9||cnt==19||cnt==28)idx++;}
            cnt++;
        }
        setKeyHeight(height);
        getNearestKeys(0, 0);
    }
}
