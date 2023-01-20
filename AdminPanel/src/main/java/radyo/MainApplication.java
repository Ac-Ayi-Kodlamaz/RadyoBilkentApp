package radyo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modal.Blog;
import modal.Content;
import modal.FireBaseService;

import java.io.IOException;
import java.util.ArrayList;


public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 450);
        stage.setTitle("Admin Panel");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws  IOException
    {
        FireBaseService fs = new FireBaseService();
        Blog b = new Blog();
        ArrayList<Content> blogList = b.getData();
        blogList.get(2).addDoc();

        //Podcast p1 = new Podcast("Tuna",new URL("https://www.radyobilkent.com/genre?id=697"),null,100,"Tuna",0,new URL("https://www.radyobilkent.com/genre?id=697"),"ADFADF");
        //p1.addDoc();
        launch();
    }
}