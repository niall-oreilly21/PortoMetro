package gui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Objects;

public class SignUpController
{
    @FXML
    private Label titleLabel;

    @FXML
    private ImageView metro1;

    @FXML
    private ImageView logo;

    @FXML
    private VBox formContainer;

    @FXML
    private AnchorPane rightAnchorPane;

    public void initialize() {
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/metro_1.jpg")));
        metro1.setImage(image1);

        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        logo.setImage(logoImage);

        VBox.setMargin(titleLabel, new Insets(0, 0, 42, 0));
    }

    public void setScene(Scene scene) {
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            metro1.fitHeightProperty().setValue(newValue);
        });
    }
}
