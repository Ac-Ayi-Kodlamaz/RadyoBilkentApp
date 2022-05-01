package com.example.adminpanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Optional;

public class MainController {

    private AnchorPane pane;

    @FXML
    private AnchorPane blogPane;

    @FXML
    private Button newPodc;

    @FXML
    private Button newBlog;


    @FXML
    void addPodcast(ActionEvent event) throws IOException {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(pane.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("PodcastEditor"));

        dialog.setTitle("Add Podcast");
        dialog.getDialogPane().setContent(fxmlLoader.load());

        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.get() == ButtonType.APPLY)
        {
            com.example.adminpanel.PodcastController dialogController = fxmlLoader.getController();
            dialogController.createPodcast();
        }

    }

    @FXML
    void addBlog(ActionEvent event) throws IOException {
        Dialog<ButtonType> blogDialog = new Dialog<>();
        blogDialog.initOwner(blogPane.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("BlogEditor"));

        blogDialog.setTitle("Add Blog");
        blogDialog.getDialogPane().setContent(fxmlLoader.load());

        blogDialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        blogDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Optional<ButtonType> blogResult = blogDialog.showAndWait();

        if(blogResult.get() == ButtonType.APPLY)
        {
            com.example.adminpanel.BlogController dialogController = fxmlLoader.getController();
            dialogController.createBlog();
        }
    }

}

