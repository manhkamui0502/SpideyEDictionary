package com.example.SpideyEDictionary.AdvancedApp.models;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DictionaryUpgrade {
    public static String BASE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\AdvancedRes\\advancedDict.txt";
    private Vector<WordUpgrade> words;

    /**
     * get Word.
     */
    public Vector<WordUpgrade> getWords() {
        return words;
    }

    /**
     * loadData.
     */
    public void loadDataFromFile() {
        try {
            Path path = Paths.get(BASE_PATH);
            words = new Vector<>();
            List<String> word_list = Files.readAllLines(path);
            ListIterator<String> iterator = word_list.listIterator();
            WordUpgrade word;
            int word_num = 0;
            while (iterator.hasNext()) {
                String str = iterator.next();
                if (Objects.equals(str, "\n") || Objects.equals(str, "")) continue;
                if (str.trim().startsWith("@")) {
                    word = new WordUpgrade();
                    word_num++;
                    String[] phonetic_part = str.split("/", 2);

                    String s2 = phonetic_part[0].substring(1).trim();
                    if (s2.startsWith("(") || s2.startsWith("'") || s2.startsWith("-")) {
                        s2 = s2.substring(1, s2.length());
                    }
                    word.setTarget(s2);
                    if (phonetic_part.length < 2) {
                        word.setPhonetics("");
                    } else {
                        word.setPhonetics("/" + phonetic_part[1]);
                    }
                    while (iterator.hasNext()) {
                        String explain = iterator.next();
                        if (!explain.startsWith("@")) {
                            word.addWordExplain(explain);
                        } else {
                            words.add(word);
                            iterator.previous();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportDataToFile() throws IOException {
        Path path = Paths.get(BASE_PATH);
        Path temp_path = Paths.get("src/main/resources/AdvancedRes/advancedDictBackup.txt");
        if (Files.exists(temp_path)) {
            Files.delete(temp_path);
        }
        Files.copy(path, temp_path);
        PrintWriter pw = new PrintWriter(BASE_PATH);
        for (WordUpgrade word : getWords()) {
            pw.println("@" + word.getTarget() + " " + word.getPhonetics());
            pw.println(word.getWordExplain());
            pw.flush();
        }
        pw.close();
    }

    /**
     * Search word.
     */
    public int Search_Word(String target) {
        WordUpgrade wordSearch = new WordUpgrade(target.trim());
        return Collections.binarySearch(words, wordSearch);
    }

    /**
     * Sort wordlist.
     */
    public void Sort_Word() {
        Collections.sort(words);
    }

    public void removeLines() {
        try {
            File inFile = new File(BASE_PATH);
            if (!inFile.isFile()) {
                return;
            }
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(BASE_PATH));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }
            if (!tempFile.renameTo(inFile)) {
                System.out.println("Could not rename file");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
