package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController
{
    @FXML
    private ImageView metro1;

    @FXML
    private ImageView logo;

    @FXML
    private Label emailLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private TextField emailText;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField confirmPasswordText;

    @FXML
    private Label errorText;

    @FXML
    private RadioButton studentRadioButton;

    @FXML
    private RadioButton passengerRadioButton;

    @FXML
    private ToggleGroup passengerTypeToggleGroup;

    private StudentUniversityController studentUniversityController;


    public void initialize()
    {
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/metro_1.jpg")));
        metro1.setImage(image1);

        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        logo.setImage(logoImage);

        passengerTypeToggleGroup = new ToggleGroup();
        studentRadioButton.setToggleGroup(passengerTypeToggleGroup);
        passengerRadioButton.setToggleGroup(passengerTypeToggleGroup);
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
        String confirmPassword = confirmPasswordText.getText();
        String asterisk = "*";
        String errorColour = "#de2a1d";

        RadioButton selectedPassengerType = (RadioButton) passengerTypeToggleGroup.getSelectedToggle();
        String passengerType = (String) selectedPassengerType.getUserData();

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
        else if (!password.equals(confirmPassword))
        {
            emailLabel.setGraphic(null);
            passwordLabel.setGraphic(null);
            errorText.setText(asterisk + " Password does not match");
            confirmPasswordLabel.setGraphic(redAsterisk);
            confirmPasswordLabel.setContentDisplay(ContentDisplay.RIGHT);
        }
        else
        {
            emailLabel.setGraphic(null);
            passwordLabel.setGraphic(null);
            confirmPasswordLabel.setGraphic(null);
            errorText.setText("");

            // TODO: Add details to MySQL database
            System.out.println("Sign up successful with email: " + email + ", passenger type: "  + passengerType);

            if (passengerType.equals("student"))
            {
                ControllersUtil<StudentUniversityController> util = new ControllersUtil<>();
                util.redirectToPage("com/gui/student_university.fxml", event, StudentUniversityController.class);
            }

        }
    }

    public void redirectToSignIn(ActionEvent event) throws IOException
    {
        ControllersUtil<SignInController> util = new ControllersUtil<>();
        util.redirectToPage("com/gui/sign_in.fxml", event, SignInController.class);
    }
}
