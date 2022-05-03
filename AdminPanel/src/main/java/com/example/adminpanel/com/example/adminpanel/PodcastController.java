package com.example.adminpanel.com.example.adminpanel;

import com.example.adminpanel.Podcast;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;

public class PodcastController
{

    @FXML
    private DatePicker date;

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
    public void createPodcast() throws MalformedURLException {
        String head = title.getText().trim();
        URL url = new URL(link.getText());
        LocalDate datePublish = date.getValue();
        Duration totalDuration = Duration.parse(duration.getText());
        ImageIcon cover = new ImageIcon(image.getText());
        String generator = creator.getText().trim();

        Podcast podcast = new Podcast();
        podcast.title = head;
        //podcast.date = datePublish;
        podcast.link = url;
        podcast.duration = totalDuration;
        //podcast.cover = cover;
        podcast.creator = generator;

        //Will be added to the database



    }

}
