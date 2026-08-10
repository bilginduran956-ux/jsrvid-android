package com.jarvis.assistant;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ana ekran
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(40, 40, 40, 40);
        root.setBackgroundColor(Color.rgb(5, 10, 18));

        // JARVIS başlığı
        TextView title = new TextView(this);
        title.setText("JARVIS");
        title.setTextColor(Color.rgb(0, 229, 255));
        title.setTextSize(42);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);

        // Durum
        TextView status = new TextView(this);
        status.setText("Sistem hazır");
        status.setTextColor(Color.WHITE);
        status.setTextSize(20);
        status.setGravity(Gravity.CENTER);
        status.setPadding(0, 30, 0, 10);

        // Açıklama
        TextView info = new TextView(this);
        info.setText("Kişisel yapay zekâ asistanı");
        info.setTextColor(Color.LTGRAY);
        info.setTextSize(16);
        info.setGravity(Gravity.CENTER);

        root.addView(title);
        root.addView(status);
        root.addView(info);

        setContentView(root);
    }
}
