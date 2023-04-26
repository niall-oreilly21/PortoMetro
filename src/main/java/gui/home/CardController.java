package gui.home;

import com.metroporto.enums.Folder;
import gui.Controller;
import com.metroporto.enums.Page;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Objects;

public class CardController extends Controller
{
    @FXML
    private ImageView cardType;

    public void initialize()
    {
        initialiseLogo();
        initialiseProfileIcon();
        initialiseCardIcon();

        Image cardTypeImage = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/img/cards/blue.png")));
        cardType.setImage(cardTypeImage);
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
        redirectToPage(event, Folder.HOME,  Page.STATION);
    }

    public void redirectToProfile(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.PROFILE);
    }
}