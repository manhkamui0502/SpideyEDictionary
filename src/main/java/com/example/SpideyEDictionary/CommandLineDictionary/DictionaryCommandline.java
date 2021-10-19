package com.example.SpideyEDictionary.CommandLineDictionary;

import com.example.SpideyEDictionary.models.Dictionary;
import com.example.SpideyEDictionary.models.Word;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DictionaryCommandline {
    protected Scanner scanner;
    protected DictionaryManagement dictionaryManagement;
    protected Dictionary dictionary;

    public DictionaryCommandline() {
        this.dictionary = new Dictionary();
        this.dictionaryManagement = new DictionaryManagement(this.dictionary);
    }

    /**
     * Show all words.
     */
    public void showAllWords() {
        System.out.println("-------- DICTIONARY LIST WORDS --------");
        System.out.println("  No\t\t|English\t\t|Vietnamese\t\t");
        int i = 1;
        for (Word word : dictionary.getWord_list()) {
            System.out.println("  " + i + "\t\t|  " + word.getWord_target() + "\t\t|  " + word.getWord_explain() + "\t\t");
            i++;
        }
        System.out.println("-------- THE END --------");
    }

    /**
     * Dictionary Basic.
     */
    public void dictionaryBasic() {
        this.dictionaryManagement.insertFromCommandline();
        showAllWords();
    }

    /**
     * Dictionary Advanced.
     */
    public void dictionaryAdvanced() {
        dictionaryManagement.insertFromFile();
        showAllWords();
        dictionarySearcher();
        dictionaryManagement.dictionaryLookup();
    }

    /**
     * Searching in dictionary.
     */
    public void dictionarySearcher() {
        System.out.println("Tìm kiếm từ điển Anh - Viet :");
        System.out.println("Ví dụ nhập: tra\n" +
                "Kết quả: danh sách các từ bắt đầu bằng “tra”: transport, translate, " +
                "transform, transit, …");
        while (true) {
            System.out.println("\nNhập từ cần tìm: ");

            String target = dictionaryManagement.scanner.nextLine();
            if (target.equalsIgnoreCase("/esc")) {
                break;
            }
            Pattern pattern = Pattern.compile("^" + target.toLowerCase() + "[a-zA-Z]*$");
            ArrayList<Word> results = new ArrayList<>();
            for (Word word : dictionaryManagement.dictionary.getWord_list()) {
                if (pattern.matcher(word.getWord_target().toLowerCase()).matches()) {
                    results.add(word);
                }
            }
            System.out.println("Kết quả: ");
            if (results.size() > 0) {
                for (Word word : results) {
                    System.out.println("- " + word.getWord_target() + ": " + word.getWord_explain() + "\t\t");
                }
            } else {
                System.out.println("Không tìm được kết quả nào phù hợp!");
            }
        }
    }

    /**
     * Super Advanced.
     */
    public void dictionarySuperAdvanced() {
        dictionaryManagement.insertFromFile();
        //showAllWords();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tra từ điển Anh - Viet:");

        while (true) {
            System.out.println("\n----------------------------------------");
            System.out.println("Nhập mã lệnh 1 đến 7 để thực hiện:");
            System.out.println(" (VD: 1)");
            System.out.println("Để hủy lệnh, nhập /esc để thoát chức năng.");
            System.out.println("Để lưu dữ liệu, hãy Xuất nội dung từ điển sau khi thêm, sửa hoặc xóa.");
            System.out.println(
                    "\n1. Xem : Xem tất cả từ (Show all words) . " +
                            "\n2. Tra từ điển (Lookup). " +
                            "\n3. Tìm kiếm (Seacher). " +
                            "\n4. Thêm từ mới (Add). " +
                            "\n5. Sửa từ đang có (Edit). " +
                            "\n6. Xóa từ đang có (Delete)." +
                            "\n7. Xuất nội dung từ điển hiện tại ra file (Export to file)." +
                            "\n/esc: Đóng từ điển (Close)."
            );

            String action = scanner.nextLine();
            if (action.equalsIgnoreCase("/esc")) {
                System.out.println("Goodbye!");
                break;
            }

            switch (action) {
                case "1" -> showAllWords();
                case "2" -> dictionaryManagement.dictionaryLookup();
                case "3" -> dictionarySearcher();
                case "4" -> dictionaryManagement.add();
                case "5" -> dictionaryManagement.edit();
                case "6" -> dictionaryManagement.delete();
                case "7" -> dictionaryManagement.dictionaryExportToFile();
                default -> System.out.println("Mã lệnh không đúng!");
            }
        }

    }
}
