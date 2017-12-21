package com.example.jms10.langstudykbrd;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * Created by Sojeong Jin on 04/12/2017.
 */

public class SharedPreferenceUtil {

    public static final String APP_SHARED_PREFS = "thisApp.SharedPreference";

    private SharedPreferences sharedPreferences;
    private Editor editor;

    public SharedPreferenceUtil(Context context){
        this.sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }
    public void setPicturePresent(boolean bool){
        editor.putBoolean("PicturePresent", bool);
        editor.commit();
    }

    public boolean getPecturePresent(){
        return sharedPreferences.getBoolean("PicturePresent", true);
    }

    public void setTextSize(int size){
        editor.putInt("TextSize", size);
        editor.commit();
    }

    public int getTextSize(){
        return sharedPreferences.getInt("TextSize", 999);
    }

    public void setDicWaitingTime(int time){
        editor.putInt("DicWaitingTime", time);
        editor.commit();
    }

    public int getDicWaitingTime(){
        return sharedPreferences.getInt("DicWaitingTime", 0);
    }


    public long getPrevTime(){
        return sharedPreferences.getLong("DicPrevTime", 0);
    }

    public void setPrevTime(long time){
        editor.putLong("DicPrevTime", time);
        editor.commit();
    }
    public void setKeyboardHeight(int height){
        editor.putInt("KeyboardHeight", height);
        editor.commit();
    }

    public int getKeyboardHegiht(){
        return sharedPreferences.getInt("KeyboardHeight", 10);
    }

    public void setKeySound(boolean bool){
        editor.putBoolean("Keysound", bool);
        editor.commit();
    }

    public boolean getKeySound(){
        return sharedPreferences.getBoolean("Keysound", true);
    }

    public void setKeyVibration(boolean bool){
        editor.putBoolean("KeyVibration", bool);
        editor.commit();
    }

    public boolean getKeyVibration(){
        return sharedPreferences.getBoolean("KeyVibration", true);
    }

    public void setVibrartionLength(int len){
        editor.putInt("VibrationLength", len);
        editor.commit();
    }

    public int getVibrationLength(){
        return sharedPreferences.getInt("VibrationHeight", 1);
    }


    public void setMemwordreview(boolean bool){
        editor.putBoolean("Memwordreview", bool);
        editor.commit();
    }

    public boolean getMemwordreview(){
        return sharedPreferences.getBoolean("Memwordriew", true);
    }

    public void setKeyboardColour(int colour){
        editor.putInt("KeyboardColour", colour);
        editor.commit();
    }

    public int getKeyboardColour(){
        return sharedPreferences.getInt("KeyboardColour", 0);
    }

    public void setConvertedWord(String word){
        editor.putString("ConvertedWord", word);
        editor.commit();
    }

    public String getConvertedWord(){
        return sharedPreferences.getString("ConvertedWord", "");
    }
}
