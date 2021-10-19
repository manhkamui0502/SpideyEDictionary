package com.example.SpideyEDictionary.AdvancedApp.Management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GoogleTranslateAPI {

    public static void main(String[] args) throws IOException {
        String text = "Hello world!";
        System.out.println("Translated text: " + TranslatedText("en", "vi", text));
    }

    /**
     * Translate using API.
     */
    public static String TranslatedText(String toTranslate, String translated, String text) throws IOException {
        String urlDirectory = "https://script.google.com/macros/s/AKfycbw2qKkvobro8WLNZUKi2kGwGwEO4W8cBavcKqcuCIGhGBBtVts/exec"
                + "?q=" + URLEncoder.encode(text, "UTF-8")
                + "&target=" + translated
                + "&source=" + toTranslate;
        URL url = new URL(urlDirectory);
        StringBuilder processed = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader temp = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String str;
        while ((str = temp.readLine()) != null) {
            processed.append(str);
        }
        temp.close();
        return processed.toString();
    }
}