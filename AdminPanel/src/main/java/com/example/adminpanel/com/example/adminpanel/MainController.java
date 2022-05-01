package com.example.adminpanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class MainController {

    //Podcast Controller
    private AnchorPane podcastPane;
    @FXML
    private Button newPodcast;
    @FXML
    public void addPodcast(ActionEvent event) throws IOException {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(podcastPane.getScene().getWindow());

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

    //Blog Controller
    @FXML
    private AnchorPane blogPane;
    @FXML
    private Button newBlog;
    @FXML
    public void addBlog(ActionEvent event) throws IOException {
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


    //Programme Flow Controller
    @FXML
    private TextField beginTime;
    @FXML
    private TextField finishTime;
    @FXML
    private TextField imageLink;
    @FXML
    private TextField titleField;

    public void addProgramme(ActionEvent event)
    {
        String name = titleField.getText();
        Date finish = new Date(finishTime.getText());
        Date begin = new Date(beginTime.getText());
        ImageIcon cover = new ImageIcon(imageLink.getText());

        //TODO
        //Create and add programme to the data base

        beginTime.setText("");
        finishTime.setText("");
        imageLink.setText("");
        titleField.setText("");
    }

    //Voting Settings Controller

    @FXML
    private TextField songName1;
    @FXML
    private TextField songName2;
    @FXML
    private TextField songName3;
    @FXML
    private TextField songName4;
    @FXML
    private TextField beginVote;
    @FXML
    private TextField endVote;
    public void addVoting(ActionEvent event)
    {
        com.example.adminpanel.Song firstSong = searchSong(songName1);
        com.example.adminpanel.Song secondSong = searchSong(songName2);
        com.example.adminpanel.Song thirdSong = searchSong(songName3);
        com.example.adminpanel.Song forthSong = searchSong(songName4);

        Date finishForVote = new Date(beginVote.getText());
        Date beginForVote = new Date(endVote.getText());

        //TODO
        //Create and add voting session to the data base

        songName1.setText("");
        songName2.setText("");
        songName3.setText("");
        songName4.setText("");
        beginVote.setText("");
        endVote.setText("");

    }

}

