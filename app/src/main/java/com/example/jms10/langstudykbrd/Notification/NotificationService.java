package com.example.jms10.langstudykbrd.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.jms10.langstudykbrd.BaseLibrary.DataFromSQL.PushNotiData.PushNotiData;
import com.example.jms10.langstudykbrd.BaseLibrary.DataFromSQL.PushNotiData.SQLPushNotiHelper;
import com.example.jms10.langstudykbrd.DictionaryActivity;
import com.example.jms10.langstudykbrd.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Sojeong Jin on 11/12/2017.
 */

public class NotificationService extends Service {
    Context context = this;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Notify();
        return START_STICKY;
    }

    public void Notify(){
        if (!mNotiThread.isAlive())
            mNotiThread.start();
        return;
    }

    Handler h = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    Thread mNotiThread = new Thread(new Runnable() {
        public void run()
        {
            while(true){
                //h.sendEmptyMessageDelayed(0, 1000*60*30);

                try {
                    Thread.sleep(5000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                SQLPushNotiHelper helper = new SQLPushNotiHelper(context);
                helper.open();
                List<PushNotiData> list = helper.getCurretDateNotices(Calendar.getInstance().getTime());


                if(list.size() == 0){
                    helper.close();continue;
                }

                for(PushNotiData a : list){
                    notifyThis("오늘의 단어", a.getWord());
                }
                helper.deleteDateNotices(list);
                helper.close();
            }
        }});

    public void notifyThis(String title, String message) {
        Intent notificationIntent = new Intent(context, DictionaryActivity.class);
        notificationIntent.putExtra("WORD", message);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder b = new NotificationCompat.Builder(this.getBaseContext());
        b.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.language_800)
                .setTicker("{your tiny message}")
                .setContentTitle(title)
                .setContentText(message)
                .setContentInfo("INFO")
                .setContentIntent(contentIntent);



        NotificationManager nm = (NotificationManager) this.getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, b.build());
    }
}
