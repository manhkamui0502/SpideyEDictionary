package com.example.SpideyEDictionary.AdvancedApp.models;

import java.util.Objects;

public class WordUpgrade implements Comparable<WordUpgrade> {
    WordExplain wordExplain;
    String target;
    String phonetics;

    /**
     * Constructor1.
     */
    public WordUpgrade() {
        this.wordExplain = new WordExplain();
    }

    /**
     * Constructor2.
     */
    public WordUpgrade(String target) {
        this.wordExplain = new WordExplain();
        this.target = target;
    }

    /**
     * Constructor3.
     */
    public WordUpgrade(String target, WordExplain wordExplain, String phonetics) {
        this.target = target;
        this.wordExplain = wordExplain;
        this.phonetics = phonetics;
    }

    /**
     * getTarget.
     */
    public String getTarget() {
        return target;
    }

    /**
     * setTarget.
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * getExplain.
     */

    public String getWordExplain() {
        return wordExplain.toString();
    }

    /**
     * setExplain.
     */
    public void setWordExplain(WordExplain wordExplain) {
        this.wordExplain = new WordExplain();
    }

    /**
     * getPhonetics.
     */
    public String getPhonetics() {
        return phonetics;
    }

    /**
     * setPhonetics.
     */

    public void setPhonetics(String phonetics) {
        this.phonetics = phonetics;
    }

    /**
     * addExplain.
     */
    public void addWordExplain(String s) {
        this.wordExplain.Add(s);
    }

    @Override
    public int compareTo(WordUpgrade word) {
        return this.getTarget().compareTo(word.getTarget());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordUpgrade word)) return false;
        return Objects.equals(getTarget(), word.getTarget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTarget());
    }

    @Override
    public String toString() {
        return this.target;
    }
}
