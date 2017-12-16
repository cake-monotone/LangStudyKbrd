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

    private static final String DB_NAME = "NOTI_DB";
    private static final int DB_VER = 1;

    public SQLPushNotiHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
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
            while (!cursor.isAfterLast()) {
                notices.add(new PushNotiData(
                        cursor.getString(1), cursor.getString(2), dateFormat.parse(cursor.getString(3))
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

    public void pushNotice(PushNotiData notice, int pendingDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(notice.getDate());
        calendar.add(Calendar.DATE, pendingDate);
        DB.execSQL(String.format("INSERT INTO NOTI_DATA (word, meaning, date) " +
                "VALUES('%s', '%s', date('%s'));",
                notice.getWord(), notice.getMeaning(), dateFormat.format(calendar.getTime())));
    }

    public void pushNotice(PushNotiData notice) {
        pushNotice(notice, PendingDelayDay);
    }
}
