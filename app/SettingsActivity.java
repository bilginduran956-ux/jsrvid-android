package com.jarvis.assistant;

import android.app.*;
import android.os.*;
import android.graphics.Color;
import android.view.*;
import android.widget.*;

public class SettingsActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        android.content.SharedPreferences p = getSharedPreferences("jarvis", 0);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(28,28,28,28);
        root.setBackgroundColor(Color.rgb(5,10,18));

        TextView title = new TextView(this);
        title.setText("JARVIS AYARLARI");
        title.setTextColor(Color.CYAN);
        title.setTextSize(28);
        root.addView(title);

        EditText url = field("API adresi", p.getString("api_url","https://api.openai.com/v1/chat/completions"));
        EditText key = field("API anahtarı", p.getString("api_key",""));
        key.setInputType(0x81);
        EditText model = field("Model", p.getString("model","gpt-4o-mini"));

        root.addView(url); root.addView(key); root.addView(model);

        Button save = new Button(this);
        save.setText("KAYDET");
        root.addView(save);
        save.setOnClickListener(v -> {
            p.edit().putString("api_url",url.getText().toString().trim())
                    .putString("api_key",key.getText().toString().trim())
                    .putString("model",model.getText().toString().trim()).apply();
            Toast.makeText(this,"Ayarlar kaydedildi.",Toast.LENGTH_SHORT).show();
        });
        setContentView(root);
    }
    private EditText field(String hint,String value) {
        EditText e=new EditText(this); e.setHint(hint); e.setText(value);
        e.setTextColor(Color.WHITE); e.setHintTextColor(Color.GRAY);
        e.setSingleLine(true); return e;
    }
}
