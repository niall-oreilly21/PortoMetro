package gui.accountauth;

import com.metroporto.enums.Folder;
import gui.Controller;
import com.metroporto.enums.Page;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController extends Controller
{
    @FXML
    private Label firstNameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private TextField firstNameText;

    @FXML
    private TextField surnameText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField confirmPasswordText;

    @FXML
    private RadioButton studentRadioButton;

    @FXML
    private RadioButton passengerRadioButton;

    @FXML
    private ToggleGroup passengerTypeToggleGroup;

    @Override
    public void initialize()
    {
        initialiseLogo();
        initialiseMetroImage("metro_1");

        passengerTypeToggleGroup = new ToggleGroup();
        studentRadioButton.setToggleGroup(passengerTypeToggleGroup);
        passengerRadioButton.setToggleGroup(passengerTypeToggleGroup);
    }

    @Override
    public void setScene(Scene scene)
    {
        scene.heightProperty().addListener((observable, oldValue, newValue) ->
        {
            metro1.fitHeightProperty().setValue(newValue);
        });
    }

    public void submitForm(ActionEvent event) throws IOException
    {
        String firstName = firstNameText.getText();
        String surname = surnameText.getText();

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

        if (firstName.isEmpty())
        {
            errorText.setText(asterisk + " Invalid first name");
            firstNameLabel.setGraphic(redAsterisk);
            firstNameLabel.setContentDisplay(ContentDisplay.RIGHT);
        }
        else if (surname.isEmpty())
        {
            firstNameLabel.setGraphic(null);
            errorText.setText(asterisk + " Invalid surname");
            surnameLabel.setGraphic(redAsterisk);
            surnameLabel.setContentDisplay(ContentDisplay.RIGHT);
        }
        else if (email.isEmpty() || !emailMatcher.matches())
        {
            surnameLabel.setGraphic(null);
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

            // TODO: Add details to MySQL database + set user in App
            System.out.println("Sign up successful with email: " + email + ", passenger type: "  + passengerType);

//            User user = new User(1, email, password);
//            app.setUser(user);
//            System.out.println(app.getUser());

            if (passengerType.equals("student"))
            {
                redirectToPage(event, Folder.ORDER_CARD, Page.STUDENT_UNIVERSITY);
            }
            else
            {
                redirectToPage(event, Folder.ORDER_CARD, Page.PASSENGER_CARD);
            }
        }
    }

    public void redirectToSignIn(ActionEvent event) throws IOException
    {
        redirectToPage(event, Folder.ACCOUNT_AUTH, Page.SIGN_IN);
    }
}
