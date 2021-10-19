package com.example.SpideyEDictionary.AdvancedApp.Search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Node {
    public LinkedList<Node> children;
    public Node parent;
    public char data;
    public boolean completeWord;

    public Node(char data) {
        this.data = data;
        this.children = new LinkedList<Node>();
        this.completeWord = false;
    }

    protected List<String> getWords() {
        List<String> list = new ArrayList<String>();
        if (completeWord) {
            list.add(toString());
        }
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                if (children.get(i) != null) {
                    list.addAll(children.get(i).getWords());
                }
            }
        }
        return list;
    }

    public Node getChild(char data) {
        if (children != null)
            for (Node child : children) {
                if (child.data == data) {
                    return child;
                }

            }
        return null;
    }

    @Override
    public String toString() {
        if (parent == null) {
            return "";
        } else {
            return parent.toString() + String.valueOf(data);
        }
    }
}
