package com.example.jms10.langstudykbrd.BaseLibrary.DataFromSQL.DictionaryData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jms10 on 2017-12-08.
 */
// TODO Database 업데이트, ~하다를 비롯한 이상한 단어 제외
public class SQLWordDictionary {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;

    public void open() {
        this.db = openHelper.getReadableDatabase();
    }

    public void close() {
        if (this.db != null)
            this.db.close();
    }

    /**
     * 주어진 단어를 포함하는 단어들을 반환합니다.
     * @param queryWord 쿼리 문자열
     * @return 검색된 단어들의 리스트
     */
    @Nullable
    public List<String> getWordList(String queryWord) {
        if (queryWord == null)
            return null;

        List<String> list = new ArrayList<>();
        Cursor cursor = this.db.rawQuery(String.format("SELECT word FROM engkor WHERE word LIKE '%s%%' OR " +
                "'%%%s' OR '%%%s%%'", queryWord, queryWord, queryWord), null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }

    /**
     * 단어의 뜻을 String으로 반환합니다.
     * @param word 검색할 단어입니다.
     * @return 뜻 String
     */
    @Nullable
    public String getWordMeaning(String word) {
        if (word == null)
            return null;

        String meaning;
        Cursor cursor = this.db.rawQuery(String.format("SELECT description FROM engkor WHERE word = '%s'", word), null);
        cursor.moveToFirst();
        meaning = cursor.getString(0);
        cursor.close();

        return meaning;
    }

    // Singletone
    private static SQLWordDictionary instance;

    private SQLWordDictionary(Context context) {
        this.openHelper = new DictionaryDBOpenHelper(context);
    }

    public static SQLWordDictionary getInstance(Context context) {
        if (instance == null)
            instance = new SQLWordDictionary(context);
        return instance;
    }
}
