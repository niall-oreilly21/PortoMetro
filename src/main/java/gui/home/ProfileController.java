package gui.home;

import com.metroporto.enums.Folder;
import gui.Controller;
import com.metroporto.enums.Page;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Objects;

public class ProfileController extends Controller
{
    @FXML
    private ImageView editProfile;

    @FXML
    private ImageView password;

    @FXML
    private ImageView signOut;

    @FXML
    private VBox contentBox;

    @FXML
    private Label editProfileLabel;

    @FXML
    private Label changePasswordLabel;

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
        Label firstNameLabel = new Label();
        TextField firstNameText = new TextField();
        firstNameText.getStyleClass().add("form-text-field");
        firstNameLabel.setText("First name");
        firstNameBox.getChildren().addAll(firstNameLabel, firstNameText);

        VBox surnameBox = new VBox();
        Label surnameLabel = new Label();
        TextField surnameText = new TextField();
        surnameText.getStyleClass().add("form-text-field");
        surnameLabel.setText("Surname");
        surnameBox.getChildren().addAll(surnameLabel, surnameText);

        VBox emailBox = new VBox();
        emailBox.setPadding(new Insets(0, 0, 40, 0));
        Label emailLabel = new Label();
        TextField emailText = new TextField();
        emailText.getStyleClass().add("form-text-field");
        emailLabel.setText("E-mail");
        emailBox.getChildren().addAll(emailLabel, emailText);

        Button saveButton = new Button();
        saveButton.getStyleClass().add("form-button");
        saveButton.setPrefWidth(200);
        saveButton.setText("Save changes");
        saveButton.onActionProperty().setValue(this::editProfile);

        namesBox.getChildren().addAll(firstNameBox, surnameBox);

        contentBox.getChildren().addAll(title, namesBox, emailBox, saveButton);
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
        Label currentPasswordLabel = new Label();
        TextField currentPasswordText = new TextField();
        currentPasswordText.getStyleClass().add("form-text-field");
        currentPasswordLabel.setText("Current password");
        currentPasswordBox.getChildren().addAll(currentPasswordLabel, currentPasswordText);

        VBox newPasswordBox = new VBox();
        newPasswordBox.setPadding(new Insets(0, 0, 40, 0));
        Label newPasswordLabel = new Label();
        TextField newPasswordText = new TextField();
        newPasswordText.getStyleClass().add("form-text-field");
        newPasswordLabel.setText("New password");
        newPasswordBox.getChildren().addAll(newPasswordLabel, newPasswordText);

        Button saveButton = new Button();
        saveButton.getStyleClass().add("form-button");
        saveButton.setPrefWidth(200);
        saveButton.setText("Save changes");
        saveButton.onActionProperty().setValue(this::changePassword);

        contentBox.getChildren().addAll(title, currentPasswordBox, newPasswordBox, saveButton);
    }

    public void editProfile(ActionEvent event)
    {

    }

    public void changePassword(ActionEvent event)
    {

    }

    public void redirectToHome(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.HOME);
    }

    public void redirectToSchedule(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.SCHEDULE);
    }

    public void redirectToJourneyRoute(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.JOURNEY_ROUTE);
    }

    public void redirectToStation(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.STATION);
    }

    public void redirectToCard(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.CARD);
    }
}

