package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Objects;

public class HomeController
{
    @FXML
    private ImageView logo;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox<String> optionsComboBox;

    @FXML
    private ImageView profile;

    @FXML
    private ImageView card;

    private App app;

    public void setApp(App app) {
        this.app = app;
    }

    public void initialize()
    {
        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        logo.setImage(logoImage);

        Image image = new Image("/img/metro_5.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(
                        BackgroundSize.AUTO,
                        BackgroundSize.AUTO,
                        false,
                        false,
                        true,
                        true
                )
        );
        Background background = new Background(backgroundImage);
        anchorPane.setBackground(background);

        Image profileImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/profile.png")));
        profile.setImage(profileImage);

        Image cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/card.png")));
        card.setImage(cardImage);

        List<String> options = List.of(
                "Look up Metro schedules by line",
                "Look up journey route from one stop to another",
                "Look up station(s) information",
                "Edit my profile",
                "View my card details");

        optionsComboBox.getItems().addAll(options);
        optionsComboBox.getSelectionModel().selectFirst();
    }

    // TODO: Add redirects using the ControllersUtil class to the corresponding classes
    public void redirectToSchedule(MouseEvent mouseEvent)
    {
        System.out.println("Schedule");
    }

    public void redirectToJourneyRoute(MouseEvent mouseEvent)
    {
        System.out.println("Journey Route");
    }

    public void redirectToStation(MouseEvent mouseEvent)
    {
        System.out.println("Station");
    }

    public void redirectToProfile(MouseEvent mouseEvent)
    {
        System.out.println("Profile");
    }

    public void redirectToCard(MouseEvent mouseEvent)
    {
        System.out.println("Card");
    }

    public void redirectToSchedule()
    {
        System.out.println("Schedule");
    }

    public void redirectToJourneyRoute()
    {
        System.out.println("Journey Route");
    }

    public void redirectToStation()
    {
        System.out.println("Station");
    }

    public void redirectToProfile()
    {
        System.out.println("Profile");
    }

    public void redirectToCard()
    {
        System.out.println("Card");
    }

    public void submitForm(ActionEvent event)
    {
        String selectedItem = optionsComboBox.getSelectionModel().getSelectedItem();
        switch(selectedItem) {
            case "Look up Metro schedules by line":
                redirectToSchedule();
                break;
            case "Look up journey route from one stop to another":
                redirectToJourneyRoute();
                break;
            case "Look up station(s) information":
                redirectToStation();
                break;
            case "Edit my profile":
                redirectToProfile();
                break;
            case "View my card details":
                redirectToCard();
                break;
            default:
                break;
        }
    }
}
