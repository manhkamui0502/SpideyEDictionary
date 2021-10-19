package com.example.SpideyEDictionary.models;

public class Word {

    /**
     * Tiếng anh.
     */
    public String word_target;

    /**
     * Nghĩa tiếng việt.
     */
    public String word_explain;

    /**
     * Constructor
     */
    public Word() {

    }

    /**
     * Constructor.
     *
     * @param word_target  String.
     * @param word_explain String.
     */
    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    /**
     * Get value word_target.
     *
     * @return String.
     */
    public String getWord_target() {
        return word_target;
    }

    /**
     * Set value word_target.
     *
     * @param word_target String.
     */
    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    /**
     * Get value word_explain.
     *
     * @return String.
     */
    public String getWord_explain() {
        return word_explain;
    }

    /**
     * Set value word_target.
     *
     * @param word_explain String.
     */
    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    @Override
    public String toString() {
        return word_target + "\t" + word_explain;
    }
}

