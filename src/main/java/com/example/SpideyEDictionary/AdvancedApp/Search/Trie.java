package com.example.SpideyEDictionary.AdvancedApp.Search;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    private final Node root;

    public Trie() {
        this.root = new Node(' ');
    }

    public void insert(String word) {
        if (search(word)) {
            return;
        }
        Node current = root;
        Node pre;
        for (char c : word.toCharArray()) {
            pre = current;
            Node child = current.getChild(c);
            if (child != null) {
                current = child;
                child.parent = pre;
            } else {
                current.children.add(new Node(c));
                current = current.getChild(c);
                current.parent = pre;
            }
        }
        current.completeWord = true;
    }

    public boolean search(String word) {
        Node current = root;
        for (char c : word.toCharArray()) {
            if (current.getChild(c) == null)
                return false;
            else {
                current = current.getChild(c);
            }
        }
        return current.completeWord;
    }

    public List<String> wordPreview(String prefix) {
        Node lastNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            lastNode = lastNode.getChild(prefix.charAt(i));
            if (lastNode == null)
                return new ArrayList<String>();
        }
        return lastNode.getWords();
    }
}
