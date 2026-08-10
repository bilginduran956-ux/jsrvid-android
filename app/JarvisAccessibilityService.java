package com.jarvis.assistant;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class JarvisAccessibilityService extends AccessibilityService {
    @Override public void onAccessibilityEvent(AccessibilityEvent event) {
        // Kullanıcı erişilebilirlik izni verdiğinde ekran içeriğine erişim burada genişletilebilir.
    }
    @Override public void onInterrupt() {}
}
