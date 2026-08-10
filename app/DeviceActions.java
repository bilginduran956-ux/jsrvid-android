package com.jarvis.assistant;

import android.content.*;
import android.hardware.camera2.*;
import android.media.*;
import android.net.Uri;
import android.provider.Settings;
import java.util.*;

public class DeviceActions {
    public static void web(Context c, String q) {
        c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + Uri.encode(q))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    public static void settings(Context c) {
        c.startActivity(new Intent(Settings.ACTION_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    public static void camera(Context c) {
        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        c.startActivity(i);
    }
    public static void volume(Context c, int direction) {
        AudioManager a = (AudioManager)c.getSystemService(Context.AUDIO_SERVICE);
        a.adjustVolume(direction > 0 ? AudioManager.ADJUST_RAISE : AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
    }
    public static String battery(Context c) {
        android.os.BatteryManager b = (android.os.BatteryManager)c.getSystemService(Context.BATTERY_SERVICE);
        int level = b.getIntProperty(android.os.BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return "Pil seviyesi yüzde " + level + ".";
    }
    public static void flashlight(Context c, boolean on) {
        try {
            CameraManager cm = (CameraManager)c.getSystemService(Context.CAMERA_SERVICE);
            String id = cm.getCameraIdList()[0];
            cm.setTorchMode(id, on);
        } catch (Exception ignored) {}
    }
    public static void openPackage(Context c, String pkg) {
        Intent i = c.getPackageManager().getLaunchIntentForPackage(pkg);
        if (i != null) {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(i);
        }
    }
}
