package com.jarvis.assistant;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.rgb(17, 24, 39));

        TextView title = new TextView(this);
        title.setText("JARVIS");
        title.setTextColor(Color.rgb(14, 165, 183));
        title.setTextSize(42);
        title.setGravity(Gravity.CENTER);

        TextView status = new TextView(this);
        status.setText("Sistem hazır");
        status.setTextColor(Color.WHITE);
        status.setTextSize(20);
        status.setGravity(Gravity.CENTER);
        status.setPadding(0, 30, 0, 0);

        layout.addView(title);
        layout.addView(status);

        setContentView(layout);
    }
}
