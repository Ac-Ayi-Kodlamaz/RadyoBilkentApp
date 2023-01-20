package radyo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Blob;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import modal.Feedback;
import modal.Storable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class demo implements Storable<demo> {
    String name;
    int value;
    BufferedImage bf = new BufferedImage(200,200, BufferedImage.TYPE_INT_ARGB);
    public demo() throws IOException, ExecutionException, InterruptedException {


    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Feedback f = new Feedback();
        f.deleteDoc();
    }

    @Override
    public void addDoc() {

    }

    @Override
    public void update(int point) {

    }

    @Override
    public void deleteDoc() {

    }

    @Override
    public ArrayList<demo> getData() {
        return null;
    }
}
