package com.example.SpideyEDictionary.models;

import java.util.ArrayList;

public class Dictionary {

    /**
     * Mảng Word.
     */
    private ArrayList<Word> Word_list;

    /**
     * Constructor.
     */
    public Dictionary() {
        this.Word_list = new ArrayList<>();
    }

    /**
     * Get Array.
     *
     * @return ArrayList<Word>.
     */
    public ArrayList<Word> getWord_list() {
        return Word_list;
    }

    /**
     * Set ArrayList.
     *
     * @param Word_list ArrayList<Word>.
     */
    public void setWord_list(ArrayList<Word> Word_list) {
        this.Word_list = Word_list;
    }

    /**
     * Add new word.
     *
     * @param word_target  String.
     * @param word_explain String.
     */
    public void add(String word_target, String word_explain) {
        this.Word_list.add(new Word(word_target, word_explain));
    }

    /**
     * Add all;
     *
     * @param list ArrayList<Word>
     */
    public void addAll(ArrayList<Word> list) {
        this.Word_list.addAll(list);
    }

    /**
     * Update
     *
     * @param index .
     * @param word  .
     */
    public void update(int index, Word word) {
        this.Word_list.set(index, word);
    }

    /**
     * Delete.
     *
     * @param word Word.
     */
    public void remove(Word word) {
        this.Word_list.remove(word);
    }
}
