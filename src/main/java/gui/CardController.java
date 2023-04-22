package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Objects;

public class CardController
{
    private final ControllersUtil util = new ControllersUtil();

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
        util.redirectToHome(event);
    }

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
}

