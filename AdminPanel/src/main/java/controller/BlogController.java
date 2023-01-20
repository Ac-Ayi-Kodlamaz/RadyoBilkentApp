package controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modal.Blog;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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

    public void createBlog() throws MalformedURLException
    {
        String head = title.getText();
        URL url = new URL(link.getText());

        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate datePublish = date.getValue();
        Date date = Date.from(datePublish.atStartOfDay(defaultZoneId).toInstant());

        long totalDuration = Long.parseLong(duration.getText());
        URL blogImage = new URL(image.getText());
        String generator = creator.getText();
        String content = Content.getText();

        Blog blog = new Blog(head,url,date,totalDuration,generator,0,url,content);
        blog.addDoc();

    }

}