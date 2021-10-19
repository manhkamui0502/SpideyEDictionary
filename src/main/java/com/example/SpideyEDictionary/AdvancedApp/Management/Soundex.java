package com.example.SpideyEDictionary.AdvancedApp.Management;

public class Soundex {
    public static String soundex(String s) {
        char[] chars = s.toUpperCase().toCharArray();
        char firstChar = chars[0];
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case 'B', 'F', 'P', 'V' -> chars[i] = '1';
                case 'C', 'G', 'J', 'K', 'Q', 'S', 'X', 'Z' -> chars[i] = '2';
                case 'D', 'T' -> chars[i] = '3';
                case 'L' -> chars[i] = '4';
                case 'M', 'N' -> chars[i] = '5';
                case 'R' -> chars[i] = '6';
                default -> chars[i] = '0';
            }
        }
        String output = "" + firstChar;
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] != chars[i - 1] && chars[i] != '0') {
                output += chars[i];
            }
        }
        output = output + "0000";
        return output.substring(0, 4);
    }
}
