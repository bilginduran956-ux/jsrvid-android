package com.jarvis.assistant;

import java.io.*;
import java.net.*;
import org.json.*;

public class OpenAICompatibleClient {
    public static String ask(String endpoint, String key, String model, String prompt) {
        try {
            URL u = new URL(endpoint);
            HttpURLConnection c = (HttpURLConnection)u.openConnection();
            c.setRequestMethod("POST");
            c.setConnectTimeout(15000);
            c.setReadTimeout(30000);
            c.setDoOutput(true);
            c.setRequestProperty("Content-Type", "application/json");
            c.setRequestProperty("Authorization", "Bearer " + key);
            JSONObject body = new JSONObject();
            body.put("model", model);
            JSONArray messages = new JSONArray();
            JSONObject sys = new JSONObject();
            sys.put("role", "system");
            sys.put("content", "Sen JARVIS'sin. Türkçe konuş. Kısa, doğal ve dürüst cevap ver.");
            messages.put(sys);
            JSONObject user = new JSONObject();
            user.put("role", "user");
            user.put("content", prompt);
            messages.put(user);
            body.put("messages", messages);
            OutputStream os = c.getOutputStream();
            os.write(body.toString().getBytes("UTF-8"));
            os.close();
            InputStream in = c.getResponseCode() >= 400 ? c.getErrorStream() : c.getInputStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) sb.append(line);
            JSONObject out = new JSONObject(sb.toString());
            return out.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        } catch (Exception e) {
            return null;
        }
    }
}
