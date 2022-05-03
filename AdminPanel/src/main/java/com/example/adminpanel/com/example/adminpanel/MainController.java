package com.example.adminpanel.com.example.adminpanel;

import com.example.adminpanel.com.example.adminpanel.Song;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.awt.Label;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable
{



    //Initialized Views

    ArrayList<Blog> blogs;
    ArrayList<Podcast> podcasts;
    ArrayList<Programme> programmes;
    ArrayList<VotingSession> votingSessions;
    Blog currentBlog;
    Podcast currentPodcast;
    Programme currentProgramme;
    VotingSession currentVoting;

    @FXML
    private Label votingInfo;
    @FXML
    private ListView<VotingSession> votingView;
    @FXML
    private Label BlogText;
    @FXML
    private ListView<Blog> BlogView;
    @FXML
    private Label PodcastText;
    @FXML
    private ListView<String> PodcastView;  //private ListView<Podcast> PodcastView; //Test
    @FXML
    private Label ProgrammeText;
    @FXML
    private ListView<Programme> ProgrammeView;

    //Here it is accepted that toString returns the listView and display returns the detailed specifications to show to the user
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        votingView.getItems().addAll(votingSessions);
        votingView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<VotingSession>() {
            @Override
            public void changed(ObservableValue<? extends VotingSession> observableValue, VotingSession votingSession, VotingSession t1) {
                currentVoting = votingView.getSelectionModel().getSelectedItem();
                votingInfo.setText(currentVoting.display());

            }
        });

        BlogView.getItems().addAll(blogs);
        BlogView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Blog>() {
            @Override
            public void changed(ObservableValue<? extends Blog> observableValue, Blog blog, Blog t1) {
                currentBlog = BlogView.getSelectionModel().getSelectedItem();
                BlogText.setText(currentBlog.display());
            }
        });

        /**
        PodcastView.getItems().addAll(podcasts);
        PodcastView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Podcast>() {
            @Override
            public void changed(ObservableValue<? extends Podcast> observableValue, Podcast podcast, Podcast t1) {
                currentPodcast = PodcastView.getSelectionModel().getSelectedItem();
                PodcastText.setText(currentPodcast.display());
            }
        });*/

        //Changing variables to test
        ArrayList<String> pods = new ArrayList<>();
        pods.add("Yusuf");
        pods.add("Tuna");
        pods.add("Ulaş");
        pods.add("Cevat");
        pods.add("Yiğit");
        PodcastView.getItems().addAll(pods);
        PodcastView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                PodcastText.setText(PodcastView.getSelectionModel().getSelectedItem().toString());
            }
        });

        ProgrammeView.getItems().addAll(programmes);
        ProgrammeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Programme>() {
            @Override
            public void changed(ObservableValue<? extends Programme> observableValue, Programme programme, Programme t1) {
                currentProgramme = ProgrammeView.getSelectionModel().getSelectedItem();
                PodcastText.setText(currentProgramme.display());
            }
        });


    }

    public void arrangeFirstViews()
    {
        //TODO
        //It will add all blogs, podcasts, programmes and voting sessions to the related ArrayList from database
    }





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
            PodcastController dialogController = fxmlLoader.getController();
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
        /*com.example.adminpanel.com.example.adminpanel.Song firstSong = searchSong(songName1);
        com.example.adminpanel.com.example.adminpanel.Song secondSong = searchSong(songName2);
        com.example.adminpanel.com.example.adminpanel.Song thirdSong = searchSong(songName3);
        com.example.adminpanel.com.example.adminpanel.Song forthSong = searchSong(songName4);*/

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



    //AddSong Controller
    @FXML
    private TextField songAlbum;
    @FXML
    private TextField songCover;
    @FXML
    private TextField songCreator;
    @FXML
    private TextField songDate;
    @FXML
    private TextField songDuration;
    @FXML
    private TextField songGenre;
    @FXML
    private TextField songLink;
    @FXML
    private TextField songTitle;

    @FXML
    public void generateSong(ActionEvent event) throws MalformedURLException
    {
        String title = songTitle.getText();
        ImageIcon cover = new ImageIcon(songCover.getText());
        String album = songAlbum.getText();
        String creator = songCreator.getText();
        String genre = songGenre.getText();
        Date date = new Date(songDate.getText());
        Duration duration = Duration.parse(songDuration.getText());
        URL link = new URL(songLink.getText());

        Song song = new Song(title,cover,date,duration,creator,0,link,album,genre,null);
        
        //Add to the database
        
        songTitle.setText("");
        songCover.setText("");
        songAlbum.setText("");
        songCreator.setText("");
        songDate.setText("");
        songDuration.setText("");
        songLink.setText("");
        songGenre.setText("");
        
    }

}

