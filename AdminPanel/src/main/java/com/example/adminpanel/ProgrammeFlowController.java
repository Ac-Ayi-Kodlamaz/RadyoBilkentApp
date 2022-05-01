import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.util.Date;

public class ProgrammeFlowController {

    @FXML
    private TextField beginTime;

    @FXML
    private TextField finishTime;

    @FXML
    private TextField imageLink;

    @FXML
    private TextField titleField;

    @FXML
    public void addProgramme(ActionEvent event)
    {
        String name = titleField.getText();
        Date finish = new Date(finishTime.getText());
        Date begin = new Date(beginTime.getText());
        ImageIcon cover = new ImageIcon(imageLink.getText());

        //TODO
        //Add the data base

        beginTime.setText("");
        finishTime.setText("");
        imageLink.setText("");
        titleField.setText("");

    }

}
