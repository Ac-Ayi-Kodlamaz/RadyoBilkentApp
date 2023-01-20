package controller;

import com.google.cloud.firestore.DocumentReference;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modal.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static modal.Storable.db;

public class MainController  implements Initializable
{
    ArrayList<Content> blogs = new ArrayList<>();
    ArrayList<Content> podcasts = new ArrayList<>();
    ArrayList<Programme> programme = new ArrayList<>();
    ArrayList<VotingSession> votingSessions = new ArrayList<>();
    ArrayList<Feedback> feedbacks = new ArrayList<>();

    ArrayList<String> titleBlog = new ArrayList<>();
    ArrayList<String> titlePodcast = new ArrayList<>();
    ArrayList<String> titleProgramme = new ArrayList<>();
    ArrayList<String> titleVoting = new ArrayList<>();
    ArrayList<String> titleFeedback = new ArrayList<>();
    ArrayList<String> titleSongs = new ArrayList<>();


    @FXML
    private TextArea feedbackText;
    @FXML
    private ListView<String > feedbackView;
    @FXML
    private TextArea votingText;
    @FXML
    private ListView<String> votingView;
    @FXML
    private ListView<String> programmeView;
    @FXML
    private TextArea programmeText;
    @FXML
    private ListView<String> podcastView;
    @FXML
    private TextArea podcastText;
    @FXML
    private TextArea blogText;
    @FXML
    private ListView<String> blogView = new ListView<>();
    @FXML
    private ListView<String> songDisplayer;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
       setOrder();
    }

    public void arrangeFirstViews() throws MalformedURLException, ExecutionException, InterruptedException {

        Blog blogData = new Blog();
        blogs = blogData.getData();

        Podcast podcastData = new Podcast();
        podcasts = podcastData.getData();

        VotingSession votingData = new VotingSession();
        votingSessions = votingData.getData();

        Programme programmeData = new Programme();
        programme = programmeData.getData();

        Feedback feedbackData = new Feedback();
        feedbacks = feedbackData.getData();

        Song songData = new Song();
        titleSongs = songData.getAllTitle();



        titleBlog.removeAll(titleBlog);
        for ( int i = 0 ; i < blogs.size(); i++)
            titleBlog.add(blogs.get(i).getTitle());

        titlePodcast.removeAll(titlePodcast);
        for ( int i = 0 ; i < podcasts.size(); i++)
            titlePodcast.add(podcasts.get(i).getTitle());

        titleVoting.removeAll(titleVoting);
        for ( int i = 0 ; i < votingSessions.size(); i++)
            titleVoting.add(votingSessions.get(i).getTitle());

        titleProgramme.removeAll(titleProgramme);
        for ( int i = 0 ; i < programme.size(); i++)
            titleProgramme.add(programme.get(i).getTitle());

        titleFeedback.removeAll(titleFeedback);
        for ( int i = 0 ; i < feedbacks.size(); i++)
            titleFeedback.add(feedbacks.get(i).getTitle());

    }

    public void setOrder()
    {
        try {
            arrangeFirstViews();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        songDisplayer.getItems().removeAll(titleSongs);
        songDisplayer.getItems().addAll(titleSongs);

        blogView.getItems().removeAll(blogView.getItems());
        blogView.getItems().addAll(titleBlog);
        blogView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {

                String blog = blogView.getSelectionModel().getSelectedItem();
                String infoDisplay = "";
                for ( int i = 0 ; i < blogs.size() ; i++)
                {
                    if ( blogs.get(i).getTitle() == blog)
                    {
                        infoDisplay = blogs.get(i).toString();
                        break;
                    }
                }

                blogText.setWrapText(true);
                blogText.setText(infoDisplay);
                blogText.setEditable(false);
            }
        });

        podcastView.getItems().removeAll(podcastView.getItems());
        podcastView.getItems().addAll(titlePodcast);

        podcastView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String podcast = podcastView.getSelectionModel().getSelectedItem();
                String infoDisplay = "";
                for ( int i = 0 ; i < podcasts.size() ; i++)
                {
                    if ( podcasts.get(i).getTitle() == podcast)
                    {
                        infoDisplay = podcasts.get(i).toString();
                        break;
                    }
                }

                podcastText.setText(infoDisplay);
                podcastText.setEditable(false);
                podcastText.setWrapText(true);
            }
        });

        votingView.getItems().removeAll(votingView.getItems());
        votingView.getItems().addAll(titleVoting);
        votingView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String votingS = votingView.getSelectionModel().getSelectedItem();
                String infoDisplay = "";
                for ( int i = 0 ; i < votingSessions.size() ; i++)
                {
                    if ( votingSessions.get(i).getTitle() == votingS)
                    {
                        infoDisplay = votingSessions.get(i).display();
                        break;
                    }
                }

                votingText.setText(infoDisplay);
                votingText.setEditable(false);
                votingText.setWrapText(true);
            }
        });

        programmeView.getItems().removeAll(programmeView.getItems());
        programmeView.getItems().addAll(titleProgramme);
        programmeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String programmeF = programmeView.getSelectionModel().getSelectedItem();
                String infoDisplay = "";
                for ( int i = 0 ; i < programme.size() ; i++)
                {
                    if ( programme.get(i).getTitle() == programmeF)
                    {
                        infoDisplay = programme.get(i).display();
                        break;
                    }
                }

                programmeText.setText(infoDisplay);
                programmeText.setEditable(false);
                programmeText.setWrapText(true);
            }
        });

        feedbackView.getItems().removeAll(feedbackView.getItems());
        feedbackView.getItems().addAll(titleFeedback);
        feedbackView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String feedbackk = feedbackView.getSelectionModel().getSelectedItem();
                String infoDisplay = "";
                for ( int i = 0 ; i < feedbacks.size() ; i++)
                {
                    if ( feedbacks.get(i).getTitle() == feedbackk)
                    {
                        infoDisplay = feedbacks.get(i).display();
                        break;
                    }
                }

                feedbackText.setText(infoDisplay);
                feedbackText.setEditable(false);
                feedbackText.setWrapText(true);
            }



        });


    }




    //Podcast Controller
    @FXML
    private AnchorPane podcastPane;
    @FXML
    private Button newPodcast;
    @FXML
    public void addPodcast(ActionEvent event) throws IOException {

        Dialog<ButtonType> podcastDialog = new Dialog<>();
        podcastDialog.initOwner(podcastPane.getScene().getWindow());

        FXMLLoader podcastLoader = new FXMLLoader();
        podcastLoader.setLocation(getClass().getResource("PodcastEditor.fxml"));

        podcastDialog.setTitle("Add Podcast");
        podcastDialog.getDialogPane().setContent(podcastLoader.load());

        podcastDialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        podcastDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Optional<ButtonType> result = podcastDialog.showAndWait();

        if(result.get() == ButtonType.APPLY)
        {
            PodcastController dialogController = podcastLoader.getController();
            dialogController.createPodcast();
        }
        setOrder();

    }



    //Blog Controller
    @FXML
    private AnchorPane blogPane;
    @FXML
    private Button newBlog;
    @FXML
    public void addBlog(ActionEvent event) throws IOException
    {
        Dialog<ButtonType> blogDialog = new Dialog<>();
        blogDialog.initOwner(blogPane.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("BlogEditor.fxml"));

        blogDialog.setTitle("Add Blog");
        blogDialog.getDialogPane().setContent(fxmlLoader.load());

        blogDialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        blogDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Optional<ButtonType> blogResult = blogDialog.showAndWait();

        if(blogResult.get() == ButtonType.APPLY)
        {
            BlogController dialogController = fxmlLoader.getController();
            dialogController.createBlog();
        }
        setOrder();
    }



    //Programme Flow Controller

    @FXML
    private TextField finishTime;
    @FXML
    private TextField imageLink;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea programmeExplanation;
    @FXML
    private DatePicker programmeDate;

    public void addProgramme(ActionEvent event) throws MalformedURLException, ExecutionException, InterruptedException
    {

        String name = titleField.getText();
        String clockTime = finishTime.getText();

        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate datePublish = programmeDate.getValue();
        Date date = Date.from(datePublish.atStartOfDay(defaultZoneId).toInstant());

        URL cover = new URL(imageLink.getText());
        String explanation = programmeExplanation.getText();

        Programme programme = new Programme(name,explanation,date,clockTime);

        programme.addDoc();
        setOrder();

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
    private TextField endVote;
    @FXML
    private DatePicker votingDate;
    public void addVoting(ActionEvent event)
    {
        /**
        Song firstSong = Song.searchFullName(songName1.getText());
        Song secondSong = Song.searchFullName(songName2.getText());
        Song thirdSong = Song.searchFullName(songName3.getText());
        Song forthSong = Song.searchFullName(songName4.getText());
        Date beginForVote = new Date(beginVote.getText());
    */
        Song firstSong = Song.searchFullName(songName1.getText());
        Song secondSong = Song.searchFullName(songName2.getText());
        Song thirdSong = Song.searchFullName(songName3.getText());
        Song forthSong = Song.searchFullName(songName4.getText());

        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate datePublish = votingDate.getValue();
        Date date = Date.from(datePublish.atStartOfDay(defaultZoneId).toInstant());

        int durationVoting = Integer.parseInt(endVote.getText());
        String titleVoting = "Voting Session: "+(int) (Math.random()*101);
        VotingSession votingSession = new VotingSession(titleVoting,durationVoting,date);

        votingSession.addSongs(firstSong);
        votingSession.addSongs(secondSong);
        votingSession.addSongs(thirdSong);
        votingSession.addSongs(forthSong);

        votingSession.addDoc();
        setOrder();

        songName1.setText("");
        songName2.setText("");
        songName3.setText("");
        songName4.setText("");
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
    private TextField songDuration;
    @FXML
    private TextField songGenre;
    @FXML
    private TextField songLink;
    @FXML
    private TextField songTitle;
    @FXML
    private DatePicker dateSong;

    @FXML
    public void generateSong(ActionEvent event) throws MalformedURLException
    {
        String title = songTitle.getText();
        URL cover = new URL(songCover.getText());
        String album = songAlbum.getText();
        String creator = songCreator.getText();
        String genre = songGenre.getText();

        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate datePublish = dateSong.getValue();
        Date date = Date.from(datePublish.atStartOfDay(defaultZoneId).toInstant());

        long duration = Long.parseLong(songDuration.getText());
        URL link = new URL(songLink.getText());
        Song song = new Song(title,cover,date,duration,creator,0,link,album,genre,null);

        song.addDoc();
        setOrder();
        
        songTitle.setText("");
        songCover.setText("");
        songAlbum.setText("");
        songCreator.setText("");
        songDuration.setText("");
        songLink.setText("");
        songGenre.setText("");
        
    }


    //Winner Song Controller
    @FXML
    private TextField winnerName;
    @FXML
    void currentSong(ActionEvent event)
    {
        String winner = winnerName.getText();

        DocumentReference reference = db.collection("currentSong").document("trial");
        reference.update("name",winner);

        winnerName.setText("");
    }


    public void readFeedback(javafx.scene.input.MouseEvent mouseEvent)
    {
        int feedbackk = feedbackView.getSelectionModel().getSelectedIndex();
        Feedback feedback = feedbacks.get(feedbackk);
        feedback.deleteDoc();

        setOrder();
    }


    public void finishVoting(javafx.scene.input.MouseEvent mouseEvent)
    {
        int votingFinish  = votingView.getSelectionModel().getSelectedIndex();
        VotingSession votingSession = votingSessions.get(votingFinish);
        votingSession.deleteDoc();

        setOrder();
    }

    public void deleteProgramme(MouseEvent mouseEvent)
    {
        int programmeFinish  = programmeView.getSelectionModel().getSelectedIndex();
        Programme programme1 = programme.get(programmeFinish);

        programme1.deleteDoc();

        setOrder();
    }

    public void deleteBlog(MouseEvent mouseEvent) {
        int blogFinish  = blogView.getSelectionModel().getSelectedIndex();
        Content blog1 = blogs.get(blogFinish);

        blog1.deleteDoc();

        setOrder();
    }

    public void deletePodcast(MouseEvent mouseEvent) {
        int podcastFinish  = podcastView.getSelectionModel().getSelectedIndex();
        Content podcast1 = podcasts.get(podcastFinish);

        podcast1.deleteDoc();

        setOrder();
    }

    public void refresh(MouseEvent mouseEvent) {
        setOrder();
    }

    public void refreshVoting(MouseEvent mouseEvent) {
        setOrder();
    }

    public void playSong(MouseEvent mouseEvent)
    {
        int songFinish = songDisplayer.getSelectionModel().getSelectedIndex();
        String song1 = titleSongs.get(songFinish);

        DocumentReference reference = db.collection("currentSong").document("trial");
        reference.update("name",song1);

    }
}

