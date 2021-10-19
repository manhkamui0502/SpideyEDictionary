module com.example.SpideyEDictionary.AdvancedApp {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires freetts;
    requires java.desktop;

    opens com.example.SpideyEDictionary.AdvancedApp to javafx.controls;
    exports com.example.SpideyEDictionary.AdvancedApp;
}