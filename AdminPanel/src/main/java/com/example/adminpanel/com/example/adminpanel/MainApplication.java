package com.example.adminpanel.com.example.adminpanel;

import com.example.adminpanel.com.example.adminpanel.Song;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;


public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(com.example.adminpanel.com.example.adminpanel.MainApplication.class.getResource("mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Admin Panel");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws  IOException {
        ArrayList<Integer> points = new ArrayList<>();
        points.add(300);
        points.add(600);
        URL link = new URL("https://docs.oracle.com/javase/7/docs/api/java/net/URL.html#URL(java.lang.String)");
        System.out.println(link);
        Song song1 = new Song("Bir Pazar Kahvaltısı", null, new Date(), null,
                "Emre Aydın", 5, link, "Unknown", "Turkish Pop",points);
        song1.addVote(500);
        System.out.println(song1.getData());
        System.out.println("Succesful!");
        Song s = new Song();
        System.out.println(s.getData());
        launch();

    }
}