package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomeController
{
    private final ControllersUtil util = new ControllersUtil();

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

    @FXML
    public void redirectToSchedule(MouseEvent event) throws IOException
    {
        util.redirectToSchedule(event);
    }

    public void redirectToJourneyRoute(MouseEvent event) throws IOException
    {
        util.redirectToJourneyRoute(event);
    }

    public void redirectToStation(MouseEvent event) throws IOException
    {
        util.redirectToStation(event);
    }

    public void redirectToProfile(MouseEvent event) throws IOException
    {
        util.redirectToProfile(event);
    }

    public void redirectToCard(MouseEvent event) throws IOException
    {
        util.redirectToCard(event);
    }

    public void submitForm(ActionEvent event) throws IOException
    {
        String selectedItem = optionsComboBox.getSelectionModel().getSelectedItem();
        switch(selectedItem) {
            case "Look up Metro schedules by line":
                util.redirectToSchedule(event);
                break;
            case "Look up journey route from one stop to another":
                util.redirectToJourneyRoute(event);
                break;
            case "Look up station(s) information":
                util.redirectToStation(event);
                break;
            case "Edit my profile":
                util.redirectToProfile(event);
                break;
            case "View my card details":
                util.redirectToCard(event);
                break;
            default:
                break;
        }
    }
}
