package com.jarvis.assistant;

import android.app.*;
import android.content.*;
import android.os.*;
import android.speech.*;
import java.util.*;

public class JarvisForegroundService extends Service {
    private static final int ID = 77;
    @Override public void onCreate() {
        super.onCreate();
        String channel = "jarvis_listener";
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel nc = new NotificationChannel(channel,"JARVIS dinleme",NotificationManager.IMPORTANCE_LOW);
            getSystemService(NotificationManager.class).createNotificationChannel(nc);
        }
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_IMMUTABLE);
        Notification.Builder b = Build.VERSION.SDK_INT >= 26 ? new Notification.Builder(this,channel) : new Notification.Builder(this);
        b.setContentTitle("JARVIS").setContentText("Sesli komut hizmeti çalışıyor").setSmallIcon(android.R.drawable.ic_btn_speak_now).setContentIntent(pi);
        startForeground(ID,b.build());
    }
    @Override public int onStartCommand(Intent i,int flags,int id) { return START_STICKY; }
    @Override public android.os.IBinder onBind(Intent i) { return null; }
}
