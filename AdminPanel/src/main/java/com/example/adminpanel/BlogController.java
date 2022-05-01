package com.example.adminpanel;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;

public class BlogController {

    @FXML
    private TextArea Content;

    @FXML
    private TextField creator;

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

    public void createBlog() throws MalformedURLException {
        String head = title.getText();
        URL url = new URL(link.getText());
        LocalDate datePublish = date.getValue();
        Duration totalDuration = Duration.parse(duration.getText());
        ImageIcon cover = new ImageIcon(image.getText());
        String generator = creator.getText().trim();
        String content = Content.getText();

        com.example.adminpanel.Blog blog = new com.example.adminpanel.Blog(head, cover, datePublish, totalDuration, generator, 0, url, false, content);


        //Will be added to the database
    }