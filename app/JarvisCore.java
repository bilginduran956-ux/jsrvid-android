package com.jarvis.assistant;

import android.content.*;
import android.media.AudioManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.provider.Settings;
import java.util.*;
import java.util.concurrent.*;
import android.content.pm.PackageManager;

public class JarvisCore {
    private final Context c;
    private final SpeechToTextManager stt;
    private final TextToSpeechManager tts;
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public JarvisCore(Context c) {
        this.c = c.getApplicationContext();
        stt = new SpeechToTextManager(c);
        tts = new TextToSpeechManager(c);
    }

    public void listen(SpeechToTextManager.Listener l) { stt.listen(l); }
    public void speak(String s) { tts.speak(s); }

    public void handle(String raw, java.util.function.Consumer<String> cb) {
        String q = raw.trim();
        String s = q.toLowerCase(new Locale("tr","TR"));

        if (s.contains("fener")) { DeviceActions.flashlight(c, true); cb.accept("Feneri açtım."); return; }
        if (s.contains("feneri kapat")) { DeviceActions.flashlight(c, false); cb.accept("Feneri kapattım."); return; }
        if (s.contains("sesi aç")) { DeviceActions.volume(c, 1); cb.accept("Sesi artırdım."); return; }
        if (s.contains("sesi kıs")) { DeviceActions.volume(c, -1); cb.accept("Sesi azalttım."); return; }
        if (s.contains("pil") || s.contains("batarya")) { cb.accept(DeviceActions.battery(c)); return; }
        if (s.startsWith("ara ") || s.startsWith("google ")) {
            String x = q.replaceFirst("(?i)^(ara|google)\\s+", "");
            DeviceActions.web(c, x); cb.accept("Web'de arıyorum: " + x); return;
        }
        if (s.contains("ayarlar")) { DeviceActions.settings(c); cb.accept("Ayarları açıyorum."); return; }
        if (s.contains("kamera")) { DeviceActions.camera(c); cb.accept("Kamerayı açıyorum."); return; }
        if (s.contains("youtube")) { DeviceActions.openPackage(c, "com.google.android.youtube"); cb.accept("YouTube'u açıyorum."); return; }
        if (s.contains("whatsapp")) { DeviceActions.openPackage(c, "com.whatsapp"); cb.accept("WhatsApp'ı açıyorum."); return; }
        if (s.contains("telegram")) { DeviceActions.openPackage(c, "org.telegram.messenger"); cb.accept("Telegram'ı açıyorum."); return; }
        if (s.contains("saat kaç")) { cb.accept("Saat " + new java.text.SimpleDateFormat("HH:mm", new Locale("tr","TR")).format(new Date()) + "."); return; }

        String key = c.getSharedPreferences("jarvis", 0).getString("api_key", "");
        String endpoint = c.getSharedPreferences("jarvis", 0).getString("api_url", "");
        String model = c.getSharedPreferences("jarvis", 0).getString("model", "gpt-4o-mini");
        if (!key.isEmpty() && !endpoint.isEmpty()) {
            pool.execute(() -> {
                String ans = OpenAICompatibleClient.ask(endpoint, key, model, q);
                cb.accept(ans == null ? "Şu anda yapay zekâ servisine ulaşamıyorum." : ans);
            });
        } else {
            cb.accept("Bu komutu yerel olarak tanımadım. Ayarlardan uyumlu yapay zekâ API adresi ve anahtarı ekleyebilirsin.");
        }
    }
}
