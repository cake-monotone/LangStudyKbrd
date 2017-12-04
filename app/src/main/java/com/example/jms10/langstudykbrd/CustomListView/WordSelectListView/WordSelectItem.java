package com.example.jms10.langstudykbrd.CustomListView.WordSelectListView;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by jms10 on 2017-12-02.
 */

public class WordSelectItem {
    private String wordString;
    private Bitmap wordBitmap;

    public  WordSelectItem(String wordString, Bitmap wordBitmap) {
        this.wordString = wordString;
        this.wordBitmap = wordBitmap;
    }

    // Getter
    public String getWordString() {
        return this.wordString;
    }

    public Bitmap getWordBitmap() {
        return this.wordBitmap;
    }

    // Setter

    public void setWordString(String wordString) {
        this.wordString = wordString;
    }

    public void setWordBitmap(Bitmap wordBitmap) {
        this.wordBitmap = wordBitmap;
    }

}
