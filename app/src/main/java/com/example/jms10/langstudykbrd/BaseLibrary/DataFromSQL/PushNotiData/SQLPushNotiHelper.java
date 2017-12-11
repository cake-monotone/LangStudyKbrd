package com.example.jms10.langstudykbrd.BaseLibrary.DataFromSQL.PushNotiData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jms10 on 2017-12-09.
 */

public class SQLPushNotiHelper extends SQLiteOpenHelper {
    private SQLiteDatabase DB;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final int PendingDelayDay = 1;

    public SQLPushNotiHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE NOTI_DATA( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "word TEXT, " +
                "meaning TEXT, " +
                "date TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void open() {
        DB = getWritableDatabase();
    }
    public void close() {
        DB.close();
    }

    public List<PushNotiData> getCurretDateNotices(Date date) {
        List<PushNotiData> notices = new ArrayList<PushNotiData>();

        Cursor cursor = DB.rawQuery(
                String.format("SELECT * FROM NOTI_DATA WHERE date = date('%s');",
                        (dateFormat.format(date))), null);
        cursor.moveToFirst();

        try {
            while (cursor.isLast()) {
                notices.add(new PushNotiData(
                        cursor.getString(0), cursor.getString(1), dateFormat.parse(cursor.getString(2))
                ));

                cursor.moveToNext();
            }
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return notices;
    }

    public void deleteDateNotices(List<PushNotiData> notices) {
        if (notices.size() == 0)
            return;

        PushNotiData date = notices.get(0);
        DB.execSQL(String.format("DELETE FROM NOTI_DATA WHERE date = date(%s);", dateFormat.format(date.getDate())));
        return;
    }

    public void pushNotice(PushNotiData notice) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(notice.getDate());
        calendar.add(Calendar.DATE, PendingDelayDay);

        DB.execSQL(String.format("INSET INTO NOTI_DATA (word, meaning, date) " +
                "VALUES('%s', '%s', date('%s'));", dateFormat.format(calendar.getTime())));
    }
}
