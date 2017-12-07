package com.example.jms10.langstudykbrd.CustomKeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jms10.langstudykbrd.R;
import com.example.jms10.langstudykbrd.SharedPreferenceUtil;
import com.example.jms10.langstudykbrd.Translation.Translate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sojeong Jin on 30/11/2017.
 */

public class SoftKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener{

    private LinearLayout fullKeyboard;
    private LangKeyboardView mInputView;
    private EditText edit_k;
    private  int prevtextlength = 0;
    private int prevCurrentlength=0;

    private LangKeyboard QwertyKeyboard;
    private LangKeyboard SymbolKeyboard;
    private LangKeyboard t_SymbolKeyboard;
    private LangKeyboard SymbolShiftKeyboard;
    private LangKeyboard t_SymbolShiftKeyboard;
    private LangKeyboard TranslationKeyboard;

    private final int[] ExceptedKeyArray = {};
    /*private final HashSet<Integer> ExceptedKeySet = new HashSet<>() {
        {
            for (int key : ExceptedKeyArray)
                add(key);
        }
    };*/
    private boolean caps = false;
    private boolean trans_flag = false;

    private final int KEYCODE_DICTIONARY = -3;
    private final int KEYCODE_TRANSLATION = -11;
    private final int KEYCODE_SYMBOL = -2;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onInitializeInterface() {
        QwertyKeyboard = new LangKeyboard(this, R.xml.qwerty);
        SymbolKeyboard = new LangKeyboard(this, R.xml.symbols);
        SymbolShiftKeyboard = new LangKeyboard(this, R.xml.symbols_shift);
        TranslationKeyboard = new LangKeyboard(this, R.xml.translation);
        t_SymbolKeyboard = new LangKeyboard(this, R.xml.symbols_t);
        t_SymbolShiftKeyboard = new LangKeyboard(this, R.xml.symbols_shift_t);
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
        getCurrentInputConnection().setSelection(0, prevCurrentlength);
        Keyboard current;
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE :
                if(edit_k.length() == 0) {
                    ic = getCurrentInputConnection();
                }
                ic.deleteSurroundingText(1, 0);
                if(mInputView.getKeyboard() == TranslationKeyboard){
                    if(mInputView.getKeyboard() == TranslationKeyboard) {
                        prevtextlength = edit_k.getText().length();
                        String bt = edit_k.getText().toString();
                        Translate.Translation t = new Translate.Translation();
                        try {
                            String res = t.execute(bt).get();
                            getCurrentInputConnection().commitText(res.toString(), 1);
                            getCurrentInputConnection().setSelection(0, res.length());
                            prevCurrentlength = res.length();
                        }
                        catch(InterruptedException e){
                            e.printStackTrace();
                        }
                        catch(ExecutionException e){
                            e.printStackTrace();
                        }
                        //t.handle
                    }
                }
                break;
            case Keyboard.KEYCODE_SHIFT:
                current = mInputView.getKeyboard();
                if(current == QwertyKeyboard|| current == TranslationKeyboard) {
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
                SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());

                break;
            case KEYCODE_TRANSLATION:
                trans_flag = !trans_flag;
                if(!trans_flag){

                   mInputView.setKeyboard(QwertyKeyboard);
                }
                else{
                    mInputView.setKeyboard(TranslationKeyboard);
                }

                break;
            case Keyboard.KEYCODE_MODE_CHANGE:
                current = mInputView.getKeyboard();
                if(current == TranslationKeyboard || current == t_SymbolKeyboard){
                    mInputView.setKeyboard(t_SymbolShiftKeyboard);
                }
                else if(current == t_SymbolShiftKeyboard){
                    mInputView.setKeyboard(t_SymbolKeyboard);
                }
                else if(current == QwertyKeyboard || current == SymbolKeyboard){
                    mInputView.setKeyboard(SymbolShiftKeyboard);
                }
                else{
                    mInputView.setKeyboard(SymbolKeyboard);
                }

                break;
            default:
                char code = (char)primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);

                if(mInputView.getKeyboard() == TranslationKeyboard) {
                    prevtextlength = edit_k.getText().length();
                    String bt = edit_k.getText().toString();
                    Translate.Translation t = new Translate.Translation();
                    try {
                        String res = t.execute(bt).get();
                        getCurrentInputConnection().commitText(res.toString(), 1);
                        getCurrentInputConnection().setSelection(0, res.length());
                        prevCurrentlength = res.length();
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    catch(ExecutionException e){
                        e.printStackTrace();
                    }
                    //t.handle
                }
        }

    }

    private void handleShift(){
        caps = !caps;
        if(mInputView == null){
            return ;
        }

        Keyboard currentKeyboard = mInputView.getKeyboard();
        if(currentKeyboard == QwertyKeyboard||currentKeyboard == TranslationKeyboard){
            mInputView.setShifted(true);
        }
    }

    private class handletranslation extends AsyncTask<String,  Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            Log.d("test", "show");
            String result = "";
            String clientId = "2qfcuefDmoUdMafl8I8W";
            String clientSecret = "";//이용시 secret 입력.
            try {
                Log.d("test", "I tried");
                String text = URLEncoder.encode(strings[0], "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/language/translate";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                // post request
                String postParams = "source=en&target=ko&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                result += response.toString() + "\n";

                Log.d("test", response.toString());
                //System.out.println(response.toString());
            } catch (Exception e) {
                Log.d("test", e.toString());
                Log.d("test", "failed");
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
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
