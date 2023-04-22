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
        ControllersUtil<ScheduleController> util = new ControllersUtil<>();
        util.redirectToPage("com/gui/schedule.fxml", event, ScheduleController.class);
    }

    @FXML
    public void redirectToJourneyRoute(MouseEvent event) throws IOException
    {
        ControllersUtil<JourneyRouteController> util = new ControllersUtil<>();
        util.redirectToPage("com/gui/journey_route.fxml", event, JourneyRouteController.class);
    }

    @FXML
    public void redirectToStation(MouseEvent event) throws IOException
    {
        ControllersUtil<StationController> util = new ControllersUtil<>();
        util.redirectToPage("com/gui/station.fxml", event, StationController.class);
    }

    @FXML
    public void redirectToProfile(MouseEvent event) throws IOException
    {
        ControllersUtil<ProfileController> util = new ControllersUtil<>();
        util.redirectToPage("com/gui/profile.fxml", event, ProfileController.class);
    }

    @FXML
    public void redirectToCard(MouseEvent event) throws IOException
    {
        ControllersUtil<CardController> util = new ControllersUtil<>();
        util.redirectToPage("com/gui/card.fxml", event, CardController.class);
    }

    public void redirectToSchedule(ActionEvent event) throws IOException
    {
        ControllersUtil<ScheduleController> util = new ControllersUtil<>();
        util.redirectToPage("com/gui/schedule.fxml", event, ScheduleController.class);
    }

    public void redirectToJourneyRoute(ActionEvent event) throws IOException
    {
        ControllersUtil<JourneyRouteController> util = new ControllersUtil<>();
        util.redirectToPage("com/gui/journey_route.fxml", event, JourneyRouteController.class);
    }

    public void redirectToStation(ActionEvent event) throws IOException
    {
        ControllersUtil<StationController> util = new ControllersUtil<>();
        util.redirectToPage("com/gui/station.fxml", event, StationController.class);
    }

    public void redirectToProfile(ActionEvent event) throws IOException
    {
        ControllersUtil<ProfileController> util = new ControllersUtil<>();
        util.redirectToPage("com/gui/profile.fxml", event, ProfileController.class);
    }

    public void redirectToCard(ActionEvent event) throws IOException
    {
        ControllersUtil<CardController> util = new ControllersUtil<>();
        util.redirectToPage("com/gui/card.fxml", event, CardController.class);
    }

    public void submitForm(ActionEvent event) throws IOException
    {
        String selectedItem = optionsComboBox.getSelectionModel().getSelectedItem();
        switch(selectedItem) {
            case "Look up Metro schedules by line":
                redirectToSchedule(event);
                break;
            case "Look up journey route from one stop to another":
                redirectToJourneyRoute(event);
                break;
            case "Look up station(s) information":
                redirectToStation(event);
                break;
            case "Edit my profile":
                redirectToProfile(event);
                break;
            case "View my card details":
                redirectToCard(event);
                break;
            default:
                break;
        }
    }
}
