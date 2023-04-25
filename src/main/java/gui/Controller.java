package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

    public void redirectToPage(ActionEvent event, Page page) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(Controller.class.getClassLoader()
                        .getResource("com/gui/" + page.getLabel() + ".fxml")));

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

    public void redirectToPage(MouseEvent event, Page page) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(Controller.class.getClassLoader()
                        .getResource("com/gui/" + page.getLabel() + ".fxml")));

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
}
