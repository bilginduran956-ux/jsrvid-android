package com.jarvis.assistant;

import android.content.*;
import android.speech.tts.TextToSpeech;
import java.util.*;

public class TextToSpeechManager {
    private final TextToSpeech tts;
    private boolean ready = false;
    public TextToSpeechManager(Context c) {
        tts = new TextToSpeech(c, status -> {
            if (status == TextToSpeech.SUCCESS) {
                ready = true;
                tts.setLanguage(new Locale("tr","TR"));
            }
        });
    }
    public void speak(String text) {
        if (ready) tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "jarvis");
    }
}
