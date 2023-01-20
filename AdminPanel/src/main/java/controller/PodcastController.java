package controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import modal.Podcast;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PodcastController
{

    @FXML
    private DatePicker date;

    @FXML
    private TextField podcastExplanation;

    @FXML
    private TextField duration;

    @FXML
    private TextField image;

    @FXML
    private TextField link;

    @FXML
    private TextField title;

    @FXML
    private TextField creator;

    @FXML
    public void createPodcast() throws MalformedURLException
    {
        String head = title.getText().trim();
        URL url = new URL(link.getText());

        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate datePublish = date.getValue();
        Date date = Date.from(datePublish.atStartOfDay(defaultZoneId).toInstant());

        long totalDuration = Long.parseLong(duration.getText());
        URL podcastImage = new URL(image.getText());
        String generator = creator.getText();
        String explanation = podcastExplanation.getText();

        Podcast podcast = new Podcast(head,podcastImage,date,totalDuration,generator,0,url,explanation);
        podcast.addDoc();

    }

}
