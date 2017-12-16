package com.example.jms10.langstudykbrd.CustomListView.WordSelectListView;

import android.graphics.Bitmap;

import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData.NetWordData;

/**
 * Created by jms10 on 2017-12-02.
 */

public class WordSelectItem {
    private String wordString;
    private Bitmap wordBitmap;
    private int wordPOS;

    public  WordSelectItem(String wordString, Bitmap wordBitmap, int wordPOS) {
        this.wordString = wordString;
        this.wordBitmap = wordBitmap;
        this.wordPOS = wordPOS;
    }

    // Getter
    public String getWordString() {
        return this.wordString;
    }

    public Bitmap getWordBitmap() {
        return this.wordBitmap;
    }

    public int getWordPOS() {
        return wordPOS;
    }

    // Setter
    public void setWordString(String wordString) {
        this.wordString = wordString;
    }

    public void setWordBitmap(Bitmap wordBitmap) {
        this.wordBitmap = wordBitmap;
    }

    public void setWordPOS(int wordPOS) {
        this.wordPOS= wordPOS;
    }
}
