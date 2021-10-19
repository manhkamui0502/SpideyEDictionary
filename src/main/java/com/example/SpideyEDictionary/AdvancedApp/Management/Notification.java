package com.example.SpideyEDictionary.AdvancedApp.Management;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Notification {
    public static void NortificationBox(String message, String title) {
        Stage stage = new Stage();
        Label label = new Label();
        Button Okay_btn = new Button();
        VBox pane = new VBox(20);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(200);
        label.setText(message);
        Okay_btn.setText("OK");
        Okay_btn.getStylesheets().add("buttonStyle.css");
        Okay_btn.setOnAction(e -> stage.close());
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.getChildren().addAll(label, Okay_btn);
        pane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
