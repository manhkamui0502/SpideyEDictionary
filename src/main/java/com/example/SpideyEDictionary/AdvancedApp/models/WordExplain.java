package com.example.SpideyEDictionary.AdvancedApp.models;

public class WordExplain {
    private final StringBuilder stringBuilder;

    /**
     * Constructor1.
     */
    public WordExplain() {
        this.stringBuilder = new StringBuilder("");
    }

    /**
     * Constructor1.
     */
    public WordExplain(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    @Override
    public String toString() {
        return stringBuilder + "";
    }

    /**
     * Add.
     */
    public void Add(String s) {
        stringBuilder.append(s).append("\n");
    }
}
