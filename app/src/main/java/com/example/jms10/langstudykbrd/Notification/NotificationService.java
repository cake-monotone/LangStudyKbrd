package com.example.jms10.langstudykbrd.Notification;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * Created by Sojeong Jin on 11/12/2017.
 */

public class NotificationService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void Notify(Context context){
        Thread t1 = new Thread(new Runnable() {
            public void run()
            {
               while(true){
                   h.sendEmptyMessageDelayed(0, 1000*60*30);
                   cheking();
               }
            }});
        t1.start();

    }

    Handler h = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
        }
    }
}
