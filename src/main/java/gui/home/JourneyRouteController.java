package gui.home;

import com.metroporto.enums.Folder;
import gui.Controller;
import com.metroporto.enums.Page;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Objects;

public class JourneyRouteController extends Controller
{
    public void initialize()
    {
        initialiseLogo();
        initialiseProfileIcon();
        initialiseCardIcon();
    }

    public void redirectToHome(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.HOME);
    }

    public void redirectToSchedule(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.SCHEDULE);
    }

    public void redirectToStation(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.STATION);
    }

    public void redirectToProfile(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.PROFILE);
    }

    public void redirectToCard(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.CARD);
    }
}

