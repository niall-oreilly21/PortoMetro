package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomeController extends Controller
{
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox<String> optionsComboBox;

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
        redirectToPage(event, Page.SCHEDULE);
    }

    public void redirectToJourneyRoute(MouseEvent event) throws IOException
    {
        redirectToPage(event, Page.JOURNEY_ROUTE);
    }

    public void redirectToStation(MouseEvent event) throws IOException
    {
        redirectToPage(event, Page.STATION);
    }

    public void redirectToProfile(MouseEvent event) throws IOException
    {
        redirectToPage(event, Page.PROFILE);
    }

    public void redirectToCard(MouseEvent event) throws IOException
    {
        redirectToPage(event, Page.CARD);
    }

    public void submitForm(ActionEvent event) throws IOException
    {
        String selectedItem = optionsComboBox.getSelectionModel().getSelectedItem();
        switch(selectedItem) {
            case "Look up Metro schedules by line":
                redirectToPage(event, Page.SCHEDULE);
                break;
            case "Look up journey route from one stop to another":
                redirectToPage(event, Page.JOURNEY_ROUTE);
                break;
            case "Look up station(s) information":
                redirectToPage(event, Page.STATION);
                break;
            case "Edit my profile":
                redirectToPage(event, Page.PROFILE);
                break;
            case "View my card details":
                redirectToPage(event, Page.CARD);
                break;
            default:
                break;
        }
    }
}
