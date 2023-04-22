package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Objects;

public class StationController
{
    @FXML
    private ImageView logo;

    @FXML
    private ImageView profile;

    @FXML
    private ImageView card;

    public void initialize()
    {
        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        logo.setImage(logoImage);

        Image profileImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/profile.png")));
        profile.setImage(profileImage);

        Image cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/card.png")));
        card.setImage(cardImage);
    }

    public void redirectToHome(MouseEvent event) throws IOException
    {
        ControllersUtil<HomeController > util = new ControllersUtil<>();
        util.redirectToPage("com/gui/home.fxml", event, HomeController.class);
    }

    public void redirectToSchedule(MouseEvent event) throws IOException
    {
        ControllersUtil<ScheduleController > util = new ControllersUtil<>();
        util.redirectToPage("com/gui/schedule.fxml", event, ScheduleController.class);
    }

    public void redirectToJourneyRoute(MouseEvent event) throws IOException
    {
        ControllersUtil<JourneyRouteController> util = new ControllersUtil<>();
        util.redirectToPage("com/gui/journey_route.fxml", event, JourneyRouteController.class);
    }

    public void redirectToProfile(MouseEvent event) throws IOException
    {
        ControllersUtil<ProfileController> util = new ControllersUtil<>();
        util.redirectToPage("com/gui/profile.fxml", event, ProfileController.class);
    }

    public void redirectToCard(MouseEvent event) throws IOException
    {
        ControllersUtil<CardController> util = new ControllersUtil<>();
        util.redirectToPage("com/gui/card.fxml", event, CardController.class);
    }
}

