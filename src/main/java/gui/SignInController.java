package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInController
{
    private final ControllersUtil util = new ControllersUtil();

    @FXML
    private ImageView metro1;

    @FXML
    private ImageView logo;

    @FXML
    private Label emailLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label errorText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField passwordText;

    public void initialize()
    {
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/metro_3.jpg")));
        metro1.setImage(image1);

        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        logo.setImage(logoImage);
    }

    public void setScene(Scene scene)
    {
        scene.heightProperty().addListener((observable, oldValue, newValue) ->
        {
            metro1.fitHeightProperty().setValue(newValue);
        });
    }

    public void submitForm(ActionEvent event) throws IOException
    {
        String email = emailText.getText();
        String emailPattern = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher emailMatcher = pattern.matcher(email);

        String password = passwordText.getText();

        String asterisk = "*";
        String errorColour = "#de2a1d";

        Text redAsterisk = new Text(asterisk);
        redAsterisk.setFill(Color.web(errorColour));

        if (email.isEmpty() || !emailMatcher.matches())
        {
            errorText.setText(asterisk + " Invalid email address");
            emailLabel.setGraphic(redAsterisk);
            emailLabel.setContentDisplay(ContentDisplay.RIGHT);
        }
        else if (password.isEmpty())
        {
            emailLabel.setGraphic(null);
            errorText.setText(asterisk + " Password is required");
            passwordLabel.setGraphic(redAsterisk);
            passwordLabel.setContentDisplay(ContentDisplay.RIGHT);
        }
        else
        {
            emailLabel.setGraphic(null);
            passwordLabel.setGraphic(null);
            errorText.setText("");

            // TODO: implement sign in authentication using MySQL + set user in App

            util.redirectToHome(event);
        }
    }

    public void redirectToSignUp(ActionEvent event) throws IOException
    {
        util.redirectToSignUp(event);
    }
}
