package com.jarvis.assistant;

import android.Manifest;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.*;
import android.provider.Settings;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    private JarvisCore core;
    private TextView status, conversation;
    private EditText command;
    private Button mic, send;

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        core = new JarvisCore(this);
        buildUi();
        requestPermissionsIfNeeded();
    }

    private void buildUi() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(28, 36, 28, 24);
        root.setBackgroundColor(Color.rgb(5,10,18));

        TextView title = new TextView(this);
        title.setText("JARVIS");
        title.setTextColor(Color.rgb(0,229,255));
        title.setTextSize(40);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(null, 1);
        root.addView(title, new LinearLayout.LayoutParams(-1, 80));

        status = new TextView(this);
        status.setText("● SİSTEM HAZIR");
        status.setTextColor(Color.rgb(0,229,255));
        status.setTextSize(16);
        status.setGravity(Gravity.CENTER);
        root.addView(status);

        conversation = new TextView(this);
        conversation.setText("Merhaba. Ben JARVIS.\nKomutunu yazabilir veya mikrofonu kullanabilirsin.");
        conversation.setTextColor(Color.WHITE);
        conversation.setTextSize(17);
        conversation.setPadding(12, 24, 12, 24);
        ScrollView scroll = new ScrollView(this);
        scroll.addView(conversation);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        LinearLayout row = new LinearLayout(this);
        command = new EditText(this);
        command.setHint("Komutunu yaz...");
        command.setSingleLine(true);
        command.setTextColor(Color.WHITE);
        command.setHintTextColor(Color.GRAY);
        row.addView(command, new LinearLayout.LayoutParams(0, 60, 1));

        mic = new Button(this);
        mic.setText("🎙");
        row.addView(mic, new LinearLayout.LayoutParams(70, 60));

        send = new Button(this);
        send.setText("GÖNDER");
        row.addView(send, new LinearLayout.LayoutParams(120, 60));
        root.addView(row);

        LinearLayout tools = new LinearLayout(this);
        Button settings = new Button(this);
        settings.setText("AYARLAR");
        Button listen = new Button(this);
        listen.setText("7/24 DİNLEME");
        Button access = new Button(this);
        access.setText("ERİŞİLEBİLİRLİK");
        tools.addView(settings, new LinearLayout.LayoutParams(0, 60, 1));
        tools.addView(listen, new LinearLayout.LayoutParams(0, 60, 1));
        tools.addView(access, new LinearLayout.LayoutParams(0, 60, 1));
        root.addView(tools);

        setContentView(root);

        send.setOnClickListener(v -> runCommand(command.getText().toString()));
        mic.setOnClickListener(v -> core.listen(text -> runCommand(text)));
        settings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        listen.setOnClickListener(v -> {
            Intent i = new Intent(this, JarvisForegroundService.class);
            if (Build.VERSION.SDK_INT >= 26) startForegroundService(i); else startService(i);
            status.setText("● ARKA PLAN DİNLEME AKTİF");
        });
        access.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)));
    }

    private void runCommand(String text) {
        if (text == null || text.trim().isEmpty()) return;
        command.setText("");
        append("SEN: " + text);
        status.setText("● İŞLENİYOR");
        core.handle(text, answer -> runOnUiThread(() -> {
            append("JARVIS: " + answer);
            status.setText("● HAZIR");
            core.speak(answer);
        }));
    }

    private void append(String s) {
        conversation.append("\n\n" + s);
    }

    private void requestPermissionsIfNeeded() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> p = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) p.add(Manifest.permission.RECORD_AUDIO);
            if (Build.VERSION.SDK_INT >= 33 && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) p.add(Manifest.permission.POST_NOTIFICATIONS);
            if (!p.isEmpty()) requestPermissions(p.toArray(new String[0]), 10);
        }
    }
}
