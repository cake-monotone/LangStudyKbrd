package com.example.jms10.langstudykbrd.Notification;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.jms10.langstudykbrd.BaseLibrary.DataFromSQL.PushNotiData.PushNotiData;
import com.example.jms10.langstudykbrd.BaseLibrary.DataFromSQL.PushNotiData.SQLPushNotiHelper;
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
                h.sendEmptyMessageDelayed(0, 1000);


                SQLPushNotiHelper helper = new SQLPushNotiHelper(context);
                helper.open();
                List<PushNotiData> list = helper.getCurretDateNotices(Calendar.getInstance().getTime());


                if(list.size() == 0){
                    helper.close();continue;
                }

                for(PushNotiData a : list){

                    //NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

                    int icon = R.drawable.language_800;
                    CharSequence notiText = "NotoManager";

                    Notification notification = new  NotificationCompat.Builder(context)
                            .setContentTitle(a.getWord())
                            .setContentText(a.getMeaning())
                            .setSmallIcon(icon)
                            .build();

                       /*Intent notificationIntent = new Intent(context, NotificationService.class);
                       PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);*/
                       synchronized (notification) {

                           notification.notify();
                       }

                }
                helper.close();
            }
        }});
}
