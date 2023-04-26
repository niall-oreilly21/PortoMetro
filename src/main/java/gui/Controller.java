package gui;

import com.metroporto.enums.Folder;
import com.metroporto.enums.Page;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public abstract class Controller
{
    @FXML
    protected ImageView logo;

    @FXML
    protected ImageView metro1;

    @FXML
    protected ImageView profile;

    @FXML
    protected ImageView card;

    @FXML
    protected Label errorText;

    protected App app;

    public abstract void initialize();

    protected void initialiseLogo()
    {
        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        logo.setImage(logoImage);
    }

    protected void initialiseMetroImage(String name)
    {
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/" + name + ".jpg")));
        metro1.setImage(image1);
    }

    protected void initialiseProfileIcon()
    {
        Image profileImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/profile.png")));
        profile.setImage(profileImage);
    }

    protected void initialiseCardIcon()
    {
        Image cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/card.png")));
        card.setImage(cardImage);
    }

    public void redirectToPage(ActionEvent event, Folder folder, Page page) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(Controller.class.getClassLoader()
                        .getResource("com/gui/" + folder.getLabel() + "/" + page.getLabel() + ".fxml")));

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();

        setScene(scene);
        setApp(App.getApplication());
    }

    public void redirectToPage(MouseEvent event, Folder folder, Page page) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(Controller.class.getClassLoader()
                        .getResource("com/gui/" + folder.getLabel() + "/" + page.getLabel() + ".fxml")));

        Parent root = loader.load();

        Controller controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();

        controller.setScene(scene);
        controller.setApp(App.getApplication());
    }

    protected void setApp(App application) {
        this.app = application;
    }

    protected void setScene(Scene scene) {}

    public String capitalise(String text)
    {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
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

    public void redirectToProfile(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.PROFILE);
    }

    public void redirectToCard(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.CARD);
    }
}
