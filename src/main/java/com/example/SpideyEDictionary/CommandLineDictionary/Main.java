package com.example.SpideyEDictionary.CommandLineDictionary;

public class Main {
    public static String BASE_PATH;

    public static void main(String[] args) {

        BASE_PATH = System.getProperty("user.dir");
        DictionaryCommandline commandline = new DictionaryCommandline();

        // Basic
        //commandline.dictionaryBasic();

        // Advanced2.
        //commandline.dictionaryAdvanced();

        // Advanced3
        commandline.dictionarySuperAdvanced();
    }
}
