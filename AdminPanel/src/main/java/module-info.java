module com.example.adminpanel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires google.cloud.firestore;
    requires com.google.auth.oauth2;

    opens com.example.adminpanel to javafx.fxml;
    exports com.example.adminpanel;
}