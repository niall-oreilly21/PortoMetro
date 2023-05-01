package gui.home;

import com.metroporto.dao.userdao.MySqlUserDao;
import com.metroporto.dao.userdao.UserDaoInterface;
import com.metroporto.exceptions.DaoException;
import com.metroporto.users.Passenger;
import com.metroporto.users.User;
import gui.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileController extends Controller
{
    UserDaoInterface userDao;

    User user;

    @FXML
    private ImageView editProfile;
    @FXML
    private ImageView password;
    @FXML
    private ImageView signOut;

    @FXML
    private VBox contentBox;

    @FXML
    private Label greetLabel;
    @FXML
    private Label editProfileLabel;
    @FXML
    private Label changePasswordLabel;

    private Label firstNameLabel;
    private TextField firstNameText;

    private Label surnameLabel;
    private TextField surnameText;

    private Label emailLabel;
    private TextField emailText;
    private String previousEmail;

    private Label currentPasswordLabel;
    private PasswordField currentPasswordText;

    private Label newPasswordLabel;
    private PasswordField newPasswordText;

    public ProfileController()
    {
        userDao = new MySqlUserDao();
        user = app.getUser();
    }

    public void initialize()
    {
        initialiseLogo();
        initialiseProfileIcon();
        initialiseCardIcon();

        Image editProfileImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/edit-profile.png")));
        editProfile.setImage(editProfileImage);

        Image passwordImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/password.png")));
        password.setImage(passwordImage);

        Image signOutImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/sign-out.png")));
        signOut.setImage(signOutImage);

        greetLabel.setText("Hello, " + user.getFirstName() + "!");

        initialiseEditProfile();
    }

    public void initialiseEditProfile()
    {
        if (!contentBox.getChildren().isEmpty())
            contentBox.getChildren().clear();

        editProfileLabel.getStyleClass().add("nav-active");
        changePasswordLabel.getStyleClass().remove("nav-active");

        Label title = new Label();
        title.setText("Edit profile");
        title.setTextFill(Color.web("#00305f"));
        title.getStyleClass().add("subheader-label-large");
        title.setPadding(new Insets(0, 0, 30, 0));

        HBox namesBox = new HBox(10);
        namesBox.setPadding(new Insets(0, 0, 20, 0));

        VBox firstNameBox = new VBox();
        firstNameLabel = new Label();
        firstNameText = new TextField();
        firstNameText.getStyleClass().add("form-text-field");
        firstNameText.setText(user.getFirstName());
        firstNameLabel.setText("First name");
        firstNameBox.getChildren().addAll(firstNameLabel, firstNameText);

        VBox surnameBox = new VBox();
        surnameLabel = new Label();
        surnameText = new TextField();
        surnameText.getStyleClass().add("form-text-field");
        surnameText.setText(user.getLastName());
        surnameLabel.setText("Surname");
        surnameBox.getChildren().addAll(surnameLabel, surnameText);

        VBox emailBox = new VBox();
        emailBox.setPadding(new Insets(0, 0, 20, 0));
        emailLabel = new Label();
        emailText = new TextField();
        emailText.getStyleClass().add("form-text-field");
        previousEmail = user.getEmail();
        emailText.setText(previousEmail);
        emailLabel.setText("E-mail");
        emailBox.getChildren().addAll(emailLabel, emailText);

        errorText = new Label();

        VBox buttonBox = new VBox();
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        Button saveButton = new Button();
        saveButton.getStyleClass().add("form-button");
        saveButton.setPrefWidth(200);
        saveButton.setText("Save changes");
        saveButton.onActionProperty().setValue(this::editProfile);

        buttonBox.getChildren().add(saveButton);
        namesBox.getChildren().addAll(firstNameBox, surnameBox);

        firstNameText.setFocusTraversable(false);
        surnameText.setFocusTraversable(false);
        emailText.setFocusTraversable(false);

        contentBox.getChildren().addAll(title, namesBox, emailBox, errorText, buttonBox);
    }

    @FXML
    public void initialiseEditProfileEvent(MouseEvent event)
    {
        initialiseEditProfile();
    }

    @FXML
    public void initialiseChangePassword(MouseEvent event)
    {
        if (!contentBox.getChildren().isEmpty())
            contentBox.getChildren().clear();

        changePasswordLabel.getStyleClass().add("nav-active");
        editProfileLabel.getStyleClass().remove("nav-active");

        Label title = new Label();
        title.setText("Change password");
        title.setTextFill(Color.web("#00305f"));
        title.getStyleClass().add("subheader-label-large");
        title.setPadding(new Insets(0, 0, 30, 0));

        VBox currentPasswordBox = new VBox();
        currentPasswordBox.setPadding(new Insets(0, 0, 20, 0));
        currentPasswordLabel = new Label();
        currentPasswordText = new PasswordField();
        currentPasswordText.getStyleClass().add("form-text-field");
        currentPasswordLabel.setText("Current password");
        currentPasswordBox.getChildren().addAll(currentPasswordLabel, currentPasswordText);

        VBox newPasswordBox = new VBox();
        newPasswordBox.setPadding(new Insets(0, 0, 20, 0));
        newPasswordLabel = new Label();
        newPasswordText = new PasswordField();
        newPasswordText.getStyleClass().add("form-text-field");
        newPasswordLabel.setText("New password");
        newPasswordBox.getChildren().addAll(newPasswordLabel, newPasswordText);

        errorText = new Label();

        VBox buttonBox = new VBox();
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        Button saveButton = new Button();
        saveButton.getStyleClass().add("form-button");
        saveButton.setPrefWidth(200);
        saveButton.setText("Save changes");
        saveButton.onActionProperty().setValue(this::changePassword);

        buttonBox.getChildren().add(saveButton);

        contentBox.getChildren().addAll(title, currentPasswordBox, newPasswordBox, errorText, buttonBox);
    }

    public void editProfile(ActionEvent event)
    {
        String firstName = firstNameText.getText();
        String surname = surnameText.getText();

        String email = emailText.getText();
        String emailPattern = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher emailMatcher = pattern.matcher(email);

        String asterisk = "*";
        String errorColour = "#de2a1d";
        errorText.setTextFill(Color.web(errorColour));

        Text redAsterisk = new Text(asterisk);
        redAsterisk.setFill(Color.web(errorColour));

        if (firstName.isEmpty())
        {
            errorText.setText(asterisk + " Invalid first name");
            firstNameLabel.setGraphic(redAsterisk);
            firstNameLabel.setContentDisplay(ContentDisplay.RIGHT);
        } else if (surname.isEmpty())
        {
            firstNameLabel.setGraphic(null);
            errorText.setText(asterisk + " Invalid surname");
            surnameLabel.setGraphic(redAsterisk);
            surnameLabel.setContentDisplay(ContentDisplay.RIGHT);
        } else if (email.isEmpty() || !emailMatcher.matches())
        {
            surnameLabel.setGraphic(null);
            errorText.setText(asterisk + " Invalid email address");
            emailLabel.setGraphic(redAsterisk);
            emailLabel.setContentDisplay(ContentDisplay.RIGHT);
        } else
        {
            firstNameLabel.setGraphic(null);
            surnameLabel.setGraphic(null);
            emailLabel.setGraphic(null);
            errorText.setText("");

            if (!firstName.equals(user.getFirstName()))
            {
                try
                {
                    user.setFirstName(firstName);
                    userDao.updateFirstName(user);
                } catch (DaoException de)
                {
                    de.printStackTrace();
                }
            }

            if (!surname.equals(user.getLastName()))
            {
                try
                {
                    user.setLastName(surname);
                    userDao.updateLastName(user);
                } catch (DaoException de)
                {
                    de.printStackTrace();
                }
            }

            if (!email.equals(user.getEmail()))
            {
                try
                {
                    user.setEmail(email);
                    boolean successful = userDao.updateEmail(user);

                    if (successful)
                    {
                        emailLabel.setGraphic(null);
                        errorText.setText("");
                    } else
                    {
                        user.setEmail(previousEmail);
                        errorText.setText(asterisk + " User with that email already exists");
                        emailLabel.setGraphic(redAsterisk);
                        emailLabel.setContentDisplay(ContentDisplay.RIGHT);
                    }
                } catch (DaoException de)
                {
                    de.printStackTrace();
                }
            }
        }
    }

    public void changePassword(ActionEvent event)
    {
        String currentPassword = currentPasswordText.getText();
        String newPassword = newPasswordText.getText();

        String asterisk = "*";
        String errorColour = "#de2a1d";
        errorText.setTextFill(Color.web(errorColour));

        Text redAsterisk = new Text(asterisk);
        redAsterisk.setFill(Color.web(errorColour));

        if (currentPassword.isEmpty())
        {
            errorText.setText(asterisk + " Invalid password");
            currentPasswordLabel.setGraphic(redAsterisk);
            currentPasswordLabel.setContentDisplay(ContentDisplay.RIGHT);
        } else if (newPassword.isEmpty())
        {
            currentPasswordLabel.setGraphic(null);
            errorText.setText(asterisk + " Invalid password");
            newPasswordLabel.setGraphic(redAsterisk);
            newPasswordLabel.setContentDisplay(ContentDisplay.RIGHT);
        } else
        {
            currentPasswordLabel.setGraphic(null);
            newPasswordLabel.setGraphic(null);
            errorText.setText("");

            if (user.checkPassword(currentPassword))
            {
                try
                {
                    user.setPassword(newPassword);
                    userDao.updatePassword(user);

                    currentPasswordLabel.setGraphic(null);
                    currentPasswordText.setText("");
                    newPasswordText.setText("");
                } catch (DaoException de)
                {
                    de.printStackTrace();
                }
            } else
            {
                currentPasswordLabel.setGraphic(redAsterisk);
                currentPasswordLabel.setContentDisplay(ContentDisplay.RIGHT);
                errorText.setText(asterisk + " Wrong password");
            }
        }
    }
}