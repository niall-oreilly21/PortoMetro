package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AppController
{
    @FXML
    private Label label;

    public void initialize() {
        label.setText("Secret text, click button to reveal!");
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        label.setText("You're a bitch!");
    }
}
