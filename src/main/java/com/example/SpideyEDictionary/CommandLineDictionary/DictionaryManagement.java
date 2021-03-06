package com.example.SpideyEDictionary.CommandLineDictionary;

import com.example.SpideyEDictionary.models.Dictionary;
import com.example.SpideyEDictionary.models.Word;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {

    protected static final String FILE_DIR = "\\src\\main\\resources\\dictionaries.txt";

    public Dictionary dictionary;

    public Scanner scanner;


    /**
     * Contructor.
     *
     * @param dictionary Dictionary.
     */
    public DictionaryManagement(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.scanner = new Scanner(System.in);
    }

    public DictionaryManagement() {

    }

    /**
     * Import word from file.
     *
     * @return ArrayList<Word>.
     */
    public static ArrayList<Word> loadDataFromFile() {
        ArrayList<Word> list = new ArrayList<>();
        try {
            File file = new File(System.getProperty("user.dir") + FILE_DIR);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String str = sc.nextLine();
                String[] strTemp = str.split("\t");
                if (strTemp.length == 2) {
                    list.add(new Word(strTemp[0], strTemp[1]));
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
        return list;
    }

    /**
     * Write to file.
     *
     * @param content String.
     * @return boolean.
     */
    public static boolean writeToFile(String content) {
        FileOutputStream fos = null;
        File file;
        try {
            file = new File(System.getProperty("user.dir") + FILE_DIR);
            fos = new FileOutputStream(file.getAbsoluteFile());
            if (!file.exists()) {
                file.createNewFile();
            }
            content.toString();
            byte[] bytesArray = content.getBytes();
            fos.write(bytesArray);
            fos.flush();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error in closing the Stream");
            }
        }
        return false;
    }

    /**
     * Input data.
     */
    public void insertFromCommandline() {
        System.out.println("Th??m t??? m???i v??o t??? ??i???n Anh - Vi???t:");
        System.out.println("Nh???p v??o s??? t??? th??m :");
        int num = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < num; i++) {
            System.out.println("Nh???p t??? ti???ng anh:");
            String word_target = scanner.nextLine();
            System.out.println("Nh???p ngh??a ti???ng vi??t:");
            String word_explain = scanner.nextLine();
            this.dictionary.add(word_target, word_explain);
        }
    }

    /**
     * Insert from file.
     */
    public void insertFromFile() {
        this.dictionary.addAll(loadDataFromFile());
    }

    /**
     * Export to file.
     */
    public void dictionaryExportToFile() {
        StringBuilder content = new StringBuilder();
        for (Word word : dictionary.getWord_list()) {
            content.append("\n").append(word.toString());
        }
        boolean success = writeToFile(content.toString());
        if (success) {
            System.out.println("Succeed in exporting to file!");
        } else {
            System.out.println("Fail in exporting to file!");
        }
    }

    /**
     * Lookup.
     */
    public void dictionaryLookup() {
        System.out.println("Tra t??? ??i???n Anh - Viet :");
        while (true) {
            System.out.println("\nNh???p t??? c???n tra (Nh???p esc ????? tho??t): ");
            System.out.println("VD: spiderman ");
            String target = scanner.nextLine();
            if (target.equalsIgnoreCase("/esc")) {
                break;
            }
            for (Word word : dictionary.getWord_list()) {
                if (word.getWord_target().equalsIgnoreCase(target)) {
                    System.out.println("K???t qu???: ");
                    System.out.println("- " + word.getWord_target() + ": " + word.getWord_explain() + "\t\t\n");
                    break;
                }
            }
        }
        System.out.println("Kh??ng t??m ???????c k???t qu??? n??o ph?? h???p!");
    }

    /**
     * Add word.
     */
    public void add() {
        System.out.println("Th??m t??? m???i v??o t??? ??i???n.");
        System.out.println("Nh???p t??? ti???ng anh:");
        String target = scanner.nextLine();
        for (Word word : dictionary.getWord_list()) {
            if (word.getWord_target().equalsIgnoreCase(target)) {
                System.out.println("T??? n??y ???? c?? trong t??? ??i???n.");
                System.out.println("- " + word.getWord_target() + ": " + word.getWord_explain() + "\t\t\n");
                return;
            }
        }

        System.out.println("Nh???p ngh??a ti???ng vi??t:");
        String explain = scanner.nextLine();
        this.dictionary.add(target, explain);
        System.out.println("Th??m t??? m???i th??nh c??ng:");
        System.out.println("- " + target + ":  " + explain);
    }

    /**
     * Edit.
     */
    public void edit() {
        System.out.println("S???a t??? c?? trong t??? ??i???n.");
        System.out.println("Nh???p t??? ti???ng anh c???n s???a:");
        String target = scanner.nextLine();
        Word wordEdited = null;
        int indexEdit = 0;
        for (Word word : dictionary.getWord_list()) {
            if (word.getWord_target().equalsIgnoreCase(target)) {
                System.out.println("- " + word.getWord_target() + ": " + word.getWord_explain() + "\t\t\n");
                wordEdited = word;
                break;
            } else {
                indexEdit++;
            }
        }

        if (wordEdited != null) {
            System.out.println("Nh???p ngh??a ti???ng vi??t m???i:");
            String explain = scanner.nextLine();
            this.dictionary.update(indexEdit, new Word(target, explain));
            System.out.println("S???a th??nh c??ng");
        } else {
            System.out.println("Kh??ng t???n t???i t??? n??y trong t??? ??i???n!");
        }
    }

    /**
     * Delete.
     */
    public void delete() {
        System.out.println("X??a t??? c?? trong t??? ??i???n.");
        System.out.println("Nh???p t??? ti???ng anh c???n x??a:");
        String target = scanner.nextLine();
        for (Word word : dictionary.getWord_list()) {
            if (word.getWord_target().equalsIgnoreCase(target)) {
                this.dictionary.remove(word);
                System.out.println("X??a th??nh c??ng");
                break;
            }
        }
    }
}
