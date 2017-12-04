package com.example.jms10.langstudykbrd.CustomKeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jms10.langstudykbrd.R;

/**
 * Created by Sojeong Jin on 30/11/2017.
 */

public class SoftKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener{

    private LinearLayout fullKeyboard;
    private LangKeyboardView mInputView;
    private EditText edit_k;

    private LangKeyboard QwertyKeyboard;
    private LangKeyboard SymbolKeyboard;
    private LangKeyboard SymbolShiftKeyboard;

    private final int[] ExceptedKeyArray = {};
    /*private final HashSet<Integer> ExceptedKeySet = new HashSet<>() {
        {
            for (int key : ExceptedKeyArray)
                add(key);
        }
    };*/
    private boolean caps = false;

    private final int KEYCODE_DICTIONARY = -3;
    private final int KEYCODE_TRANSLATION = -11;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onInitializeInterface() {
        QwertyKeyboard = new LangKeyboard(this, R.xml.qwerty);
        SymbolKeyboard = new LangKeyboard(this, R.xml.symbols);
        SymbolShiftKeyboard = new LangKeyboard(this, R.xml.symbols_shift);
        super.onInitializeInterface();
    }

    @Override
    public View onCreateInputView() {
        fullKeyboard = (LinearLayout)getLayoutInflater().inflate(R.layout.input, null);
        mInputView = (LangKeyboardView)fullKeyboard.findViewById(R.id.keyboard);
        edit_k = (EditText)fullKeyboard.findViewById(R.id.edit_keyboard);
        mInputView.setKeyboard(QwertyKeyboard);
        mInputView.setPreviewEnabled(false);
        mInputView.setOnKeyboardActionListener(this);
        return fullKeyboard;
    }


    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        Log.d("Test", "KEYCODE: "+primaryCode);
        InputConnection ic = edit_k.onCreateInputConnection(new EditorInfo());
        Keyboard current;
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE :
                if(edit_k.length() == 0) {
                    ic = getCurrentInputConnection();
                }
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                current = mInputView.getKeyboard();
                if(current == QwertyKeyboard) {
                    caps = !caps;
                    mInputView.setShifted(caps);
                }
                else{
                    if(current == SymbolKeyboard){
                        mInputView.setKeyboard(SymbolShiftKeyboard);
                    }
                    else{
                        mInputView.setKeyboard(SymbolKeyboard);
                    }
                }
                /*Intent intent = new Intent(getApplicationContext(), TmpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/

                break;
            case Keyboard.KEYCODE_DONE:
                break;
            case KEYCODE_DICTIONARY:
                break;
            case KEYCODE_TRANSLATION:
                break;
            case Keyboard.KEYCODE_MODE_CHANGE:
                current = mInputView.getKeyboard();
                if(current == SymbolKeyboard || current == SymbolShiftKeyboard)
                {
                    mInputView.setKeyboard(QwertyKeyboard);
                }
                else
                {
                    mInputView.setKeyboard(SymbolKeyboard);
                    caps = false;
                }
                break;
            default:
                char code = (char)primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
        }

    }

    private void handleShift(){
        caps = !caps;
        if(mInputView == null){
            return ;
        }

        Keyboard currentKeyboard = mInputView.getKeyboard();
        if(currentKeyboard == QwertyKeyboard){
            mInputView.setShifted(true);
        }
    }

    @Override
    public void onPress(int primaryCode) {

    }


    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeUp() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void onRelease(int primaryCode) {

    }
}
