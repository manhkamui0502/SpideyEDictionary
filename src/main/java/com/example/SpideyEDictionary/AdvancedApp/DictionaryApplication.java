package com.example.SpideyEDictionary.AdvancedApp;

import com.example.SpideyEDictionary.AdvancedApp.Management.GoogleTranslateAPI;
import com.example.SpideyEDictionary.AdvancedApp.Management.Notification;
import com.example.SpideyEDictionary.AdvancedApp.Management.Soundex;
import com.example.SpideyEDictionary.AdvancedApp.Search.Trie;
import com.example.SpideyEDictionary.AdvancedApp.models.DictionaryUpgrade;
import com.example.SpideyEDictionary.AdvancedApp.models.WordExplain;
import com.example.SpideyEDictionary.AdvancedApp.models.WordUpgrade;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//bản chuẩn

public class DictionaryApplication extends Application {
    Button Search_btn = new Button();
    Button Voice_btn = new Button();
    Button Translate_btn = new Button();
    Button Add_btn = new Button();
    Button Delete_btn = new Button();
    Button Edit_btn = new Button();

    Stage currentStage;
    VBox mainPanel;
    TextField wordField;
    ListView<String> recommendWordsList;
    Label label;
    Trie trie = new Trie();
    List<String> wordList = new ArrayList<>();
    ObservableList<String> observableList;
    DictionaryUpgrade dictionaryUpgrade = new DictionaryUpgrade();
    VoiceManager voiceManager;
    Voice voice;
    ImageView image = new ImageView(new Image("Spiderman.jpg"));
    ImageView image1 = new ImageView(new Image("paneBackground.jpg"));

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        currentStage = primaryStage;
        dictionaryUpgrade = new DictionaryUpgrade();
        dictionaryUpgrade.loadDataFromFile();
        dictionaryUpgrade.Sort_Word();
        voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice("kevin16");
        voice.allocate();

        for (WordUpgrade word : dictionaryUpgrade.getWords()) {
            trie.insert(word.getTarget());
            wordList.add(word.getTarget());
        }

        wordField = new TextField();
        wordField.setPrefSize(210, 40);
        wordField.setPromptText("Nhập từ cần tra . . .");
        wordField.setOnKeyReleased(e -> textField());

        setButton(Search_btn, "Search", 60, 40);
        Search_btn.setOnAction(e -> Searcher());
        setButton(Voice_btn, "Voice", 60, 40);
        Voice_btn.setOnAction(event -> Speaker());
        setButton(Add_btn, "Add", 80, 50);
        Add_btn.setOnAction(event -> AddWord());
        setButton(Delete_btn, "Delete", 80, 50);
        Delete_btn.setOnAction(event -> DeleteWord());
        setButton(Edit_btn, "Edit", 80, 50);
        Edit_btn.setOnAction(event -> EditWord());
        setButton(Translate_btn, "     API" + "\nTranslator", 80, 50);
        Translate_btn.setOnAction(event -> {
            try {
                Translator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        VBox toolButton = new VBox(20);
        toolButton.setPadding(new Insets(60, 10, 0, 0));
        toolButton.getChildren().addAll(Add_btn, Delete_btn, Edit_btn, Translate_btn);
        HBox SearchBar = new HBox();
        SearchBar.getChildren().addAll(wordField, Search_btn);

        label = new Label("Nghĩa Tiếng Việt");
        label.setAlignment(Pos.CENTER);
        label.setMinWidth(380);

        Text information = new Text(25, 25, "||Definition");
        information.setFill(Color.CHOCOLATE);
        information.setStyle("-fx-font-weight: BOLD; -fx-font-size: 22");
        Effect glow0 = new Glow(1.5);
        information.setEffect(glow0);
        HBox definition = new HBox(270);
        definition.getChildren().addAll(information, Voice_btn);

        ScrollPane detailPane = new ScrollPane();
        detailPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        detailPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        detailPane.setPadding(new Insets(10,10,10,10));
        detailPane.setContent(label);
        detailPane.setPrefSize(380, 380);

        observableList = FXCollections.observableList(wordList);
        recommendWordsList = new ListView<String>(observableList);
        recommendWordsList.setPrefSize(270, 380);
        recommendWordsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String str = recommendWordsList.getSelectionModel().getSelectedItems().toString();
                str = str.substring(1, str.length() - 1);
                wordField.setText(str);
            }
        });
        VBox defineZone = new VBox();
        defineZone.getChildren().setAll(definition, detailPane);

        VBox searchZone = new VBox();
        searchZone.setPadding(new Insets(0, 0, 0, 20));
        searchZone.getChildren().addAll(SearchBar, recommendWordsList);
        HBox functionView = new HBox(20);
        functionView.getChildren().addAll(searchZone, defineZone);
        //Glowing
        Text dict_name = new Text(25, 25, "- - Spidey E-Dictionary - -");
        dict_name.setFill(Color.CHOCOLATE);
        dict_name.setFont(Font.font(java.awt.Font.SANS_SERIF, FontWeight.BOLD, 45));
        Effect glow = new Glow(1.5);
        dict_name.setEffect(glow);
        HBox dictName = new HBox();
        dictName.getChildren().add(dict_name);
        dictName.setAlignment(Pos.CENTER);

        mainPanel = new VBox(10);
        setBackground(mainPanel, image);
        HBox mainPane = new HBox(10);
        mainPane.getChildren().addAll(searchZone, functionView, toolButton);
        mainPanel.getChildren().addAll(dictName, mainPane);
        mainPane.setAlignment(Pos.CENTER);
        mainPanel.setPadding(new Insets(0,0,20,0));
        mainPanel.setAlignment(Pos.CENTER);

        primaryStage.widthProperty().addListener(e -> {
            if (primaryStage.getWidth() < 600) {
                mainPane.getChildren().clear();
                mainPanel.getChildren().clear();
                definition.setSpacing(150);
                label.setMinWidth(180);
                toolButton.setPadding(new Insets(0,20,20,0));
                mainPane.getChildren().addAll(searchZone, toolButton);
                functionView.setPadding(new Insets(0,20,0,20));
                dict_name.setStyle("-fx-font-size: 20");
                mainPanel.getChildren().addAll(dict_name, mainPane, functionView);
                mainPanel.setPadding(new Insets(10,10,10,10));
                mainPane.setAlignment(Pos.CENTER);
                mainPanel.setAlignment(Pos.CENTER);
            } else {
                mainPane.getChildren().clear();
                mainPanel.getChildren().clear();
                toolButton.setPadding(new Insets(60, 10, 0, 0));
                functionView.setPadding(new Insets(0,0,0,0));
                label.setMinWidth(380);
                definition.setSpacing(270);
                mainPane.getChildren().addAll(searchZone, functionView, toolButton);
                dict_name.setStyle("-fx-font-size: 40");
                mainPanel.getChildren().addAll(dict_name, mainPane);
                mainPane.setAlignment(Pos.CENTER);
            }
        });

        Scene scene = new Scene(mainPanel);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(400);
        primaryStage.setMaxHeight(600);
        primaryStage.setMaxWidth(900);
        primaryStage.setTitle("Spidey E-Dictionary");
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            CloseButton();
        });
        primaryStage.show();
    }

    private void setButton(Button button, String button_label, int w, int h) {
        button.setText(button_label);
        //Hover + Style + backGround
        button.getStylesheets().add("buttonStyle.css");
        button.setPrefSize(w, h);
    }

    public void setBackground(VBox box, ImageView image) {
        box.setBackground(new Background(new BackgroundImage(image.getImage(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1, 1, true, true, false, false))));
    }

    private void Searcher() {
        String target = wordField.getText();
        if (!target.equals("")) {
            int result = dictionaryUpgrade.Search_Word(target);
            if (result > 0) {
                label.setText(dictionaryUpgrade.getWords().get(result).getPhonetics() + "\n" + dictionaryUpgrade.getWords().get(result).getWordExplain());
            } else {
                List<String> wordExplain = new ArrayList<>();
                label.setText("Không tìm thấy tron từ điển, hãy sử dụng API Translator!");
                String sdx = Soundex.soundex(target);
                for (WordUpgrade word : dictionaryUpgrade.getWords()) {
                    if (word.getTarget().length() > 0)
                        if (Soundex.soundex(word.getTarget()).equals(sdx)) {
                            wordExplain.add(word.getTarget());
                        }
                }
                observableList.clear();
                if (wordExplain.size() > 15) {
                    observableList.addAll(wordExplain.subList(0, 15));
                } else observableList.addAll(wordExplain);
            }
        }
    }

    private void textField() {
        String s = wordField.getText();
        List<String> list = trie.wordPreview(s);
        observableList.clear();
        observableList.addAll(list);
    }

    private void Speaker() {
        voice.speak(wordField.getText());
    }

    private void Translator() throws IOException {
        String s = GoogleTranslateAPI.TranslatedText("en", "vi", wordField.getText());
        label.setText(s);
    }

    private void AddWord() {
        Stage stage = new Stage();
        Label inputWordLabel = new Label();
        TextField inputWord = new TextField();
        Label phonetic = new Label();
        TextField phoneticInput = new TextField();
        TextArea wordDetail = new TextArea();
        Button addConfirm = new Button();
        Label detailLabel = new Label();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Thêm từ mới");
        inputWordLabel.setText("Nhập từ: ");
        inputWord.setPromptText("Nhập từ Tiếng Anh mới ... ");
        phonetic.setText("Phiên âm");
        phoneticInput.setPromptText("Nhập cách phát âm ... ");
        detailLabel.setText("Nghĩa Tiếng Viêt");
        addConfirm.setText("Thêm");
        addConfirm.getStylesheets().add("buttonStyle.css");

        VBox addPane = new VBox(10);
        addPane.setPadding(new Insets(10, 10, 10, 10));
        addPane.getChildren().addAll(inputWordLabel, inputWord, phonetic, phoneticInput, detailLabel, wordDetail, addConfirm);
        addConfirm.setOnAction(event -> {
            String str = inputWord.getText();
             if (str.trim().equals("")) {
                Notification.NortificationBox("Không hợp lệ!", "");
            }else {
                int result = Collections.binarySearch(dictionaryUpgrade.getWords(), new WordUpgrade(str));
                if (result <= 0) {
                    StringBuilder str2 = new StringBuilder(wordDetail.getText());
                    String str3 = phoneticInput.getText();
                    WordUpgrade newWord = new WordUpgrade(str, new WordExplain(new StringBuilder("- " + str2 + "\n")), "/" + str3 + "/");
                    dictionaryUpgrade.getWords().add(newWord);
                    dictionaryUpgrade.Sort_Word();
                    Notification.NortificationBox("Thêm từ thành công!", "");
                    trie.insert(newWord.getTarget());
                } else {
                    Notification.NortificationBox("Từ đã tồn tại!", "");
                }
            }
        });
        setBackground(addPane, image1);
        Scene scene = new Scene(addPane, 300, 400);
        stage.setScene(scene);
        stage.setMinWidth(400);
        stage.setMinHeight(400);
        stage.show();
    }

    private void DeleteWord() {
        Stage stage = new Stage();
        Label input = new Label();
        Label output = new Label();
        Button delConfirm = new Button();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Xóa từ");
        TextField inputField = new TextField();
        input.setText("Nhập từ cần xóa: ");
        inputField.setPromptText("Nhập từ muốn xóa...");
        output.setText("Xóa thành công!");
        delConfirm.setText("Xóa");
        delConfirm.getStylesheets().add("buttonStyle.css");
        delConfirm.setOnAction(event -> {
            String s = inputField.getText();
            if (s.trim().equals("")) {
                Notification.NortificationBox("Không hợp lệ!", "");
            }else {
                int result = Collections.binarySearch(dictionaryUpgrade.getWords(), new WordUpgrade(s));
                if (result > 0) {
                    dictionaryUpgrade.getWords().remove(result);
                    Notification.NortificationBox("Xóa thành công! ", "");
                } else {
                    Notification.NortificationBox("Từ đã xóa khỏi từ điển hoặc không tồn tại!", "");
                }
            }
        });
        HBox hBox = new HBox();
        hBox.getChildren().addAll(delConfirm);
        VBox deletePane = new VBox(10);
        deletePane.setPadding(new Insets(10, 10, 10, 10));
        deletePane.getChildren().addAll(input, inputField, delConfirm);
        setBackground(deletePane, image1);
        Scene scene = new Scene(deletePane, 300, 300);
        stage.setScene(scene);
        stage.setMinWidth(400);
        stage.setMinHeight(400);
        stage.show();
    }

    private void EditWord() {
        Stage stage = new Stage();
        Label inputWordLabel = new Label();
        TextField inputWord = new TextField();
        Label inputWordLabel2 = new Label();
        TextField inputWord2 = new TextField();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Sửa từ");
        inputWordLabel.setText("Nhập từ cần sửa: ");
        inputWord.setPromptText("Nhập từ cần sửa ... ");
        inputWordLabel2.setText("Nhập từ sau khi sửa: ");
        inputWord2.setPromptText("Nhập từ sau khi sửa ...");


        Label phonetic = new Label();
        TextField phoneticInput = new TextField();
        TextArea wordDetail = new TextArea();
        Button editConfirm = new Button();
        Label detailLabel = new Label();

        phonetic.setText("Phiên âm");
        phoneticInput.setPromptText("Nhập cách phát âm ... ");
        detailLabel.setText("Nghĩa Tiếng Viêt");
        editConfirm.setText("Sửa");
        editConfirm.getStylesheets().add("buttonStyle.css");

        VBox editPane = new VBox(10);
        editPane.setPadding(new Insets(10, 10, 10, 10));
        editPane.getChildren().addAll(inputWordLabel, inputWord, inputWordLabel2, inputWord2, phonetic, phoneticInput, detailLabel, wordDetail, editConfirm);
        editConfirm.setOnAction(event -> {
            String s1 = inputWord.getText();
            String s2 = inputWord2.getText();
             if (s1.trim().equals("") || s2.trim().equals("")) {
                Notification.NortificationBox("Không hợp lệ!", "");
            }else {
                int result = Collections.binarySearch(dictionaryUpgrade.getWords(), new WordUpgrade(s1));
                if (result > 0) {
                    dictionaryUpgrade.getWords().remove(result);
                    StringBuilder s3 = new StringBuilder(wordDetail.getText());
                    String s4 = phoneticInput.getText();
                    WordUpgrade newWord = new WordUpgrade(s2, new WordExplain(new StringBuilder("- " + s3 + "\n")), "/" + s4 + "/");
                    dictionaryUpgrade.getWords().add(newWord);
                    dictionaryUpgrade.Sort_Word();
                    Notification.NortificationBox("Sửa từ thành công!", "");
                    trie.insert(newWord.getTarget());
                } else {
                    Notification.NortificationBox("Từ không tồn tại!", "");
                }
            }
        });
        setBackground(editPane, image1);
        Scene scene = new Scene(editPane, 300, 500);
        stage.setScene(scene);
        stage.setMinWidth(400);
        stage.setMinHeight(400);
        stage.show();
    }

    public void CloseButton() {
        try {
            exportDataOnExit();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        currentStage.close();
    }

    public void exportDataOnExit() throws IOException {
        dictionaryUpgrade.exportDataToFile();
        dictionaryUpgrade.removeLines();
    }
}
