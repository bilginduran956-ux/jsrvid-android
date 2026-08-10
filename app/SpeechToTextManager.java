package com.jarvis.assistant;

import android.content.*;
import android.speech.*;
import java.util.*;

public class SpeechToTextManager {
    public interface Listener { void onText(String text); }
    private final Context c;
    public SpeechToTextManager(Context c) { this.c = c; }

    public void listen(Listener listener) {
        final SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(c);
        sr.setRecognitionListener(new RecognitionListener() {
            public void onReadyForSpeech(Bundle p) {}
            public void onBeginningOfSpeech() {}
            public void onRmsChanged(float r) {}
            public void onBufferReceived(byte[] b) {}
            public void onEndOfSpeech() {}
            public void onError(int e) { sr.destroy(); }
            public void onResults(Bundle b) {
                ArrayList<String> r = b.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (r != null && !r.isEmpty()) listener.onText(r.get(0));
                sr.destroy();
            }
            public void onPartialResults(Bundle b) {}
            public void onEvent(int a, Bundle b) {}
        });
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "tr-TR");
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "tr-TR");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "JARVIS dinliyor...");
        sr.startListening(i);
    }
}
