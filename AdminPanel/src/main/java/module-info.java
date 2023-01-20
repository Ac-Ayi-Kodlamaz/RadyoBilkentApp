module radyo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    opens radyo to javafx.graphics, javafx.fxml;
    requires java.desktop; //for importing java.awt which requires this module
    requires java.base;
    /*requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx; */
    //---------For Database Connection -------------------
    requires google.cloud.core;
    requires google.cloud.firestore;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires com.google.api.apicommon;
    //-----------------------------------------------------
    exports radyo;
    exports modal;
    opens modal to javafx.fxml, javafx.graphics;
    exports controller;
    opens controller to javafx.fxml, javafx.graphics;
    //opens radyo.Model to javafx.fxml, javafx.graphics;
    //opens radyo.Controller to javafx.fxml, javafx.graphics;
    //opens  to javafx.fxml;
}