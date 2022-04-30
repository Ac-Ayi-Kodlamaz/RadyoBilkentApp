module com.example.adminpanel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires java.desktop; //for importing java.awt which requires this module
    requires java.base;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    //---------For Database Connection -------------------
    requires google.cloud.core;
    requires google.cloud.firestore;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires com.google.api.apicommon;
    //-----------------------------------------------------
    opens com.example.adminpanel to javafx.fxml;
    exports com.example.adminpanel;
}