package com.example.jms10.langstudykbrd.BaseLibrary.DataFromSQL.DictionaryData;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by jms10 on 2017-12-08.
 */

public class DictionaryDBOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "dictionary.db";
    private static final int DATABASE_VERSION = 1;

    public DictionaryDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
