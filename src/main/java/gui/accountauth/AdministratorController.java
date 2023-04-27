package gui.accountauth;

import gui.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class AdministratorController extends Controller
{
    @FXML
    private Label trainsLabel;

    @FXML
    private Label usersLabel;

    @FXML
    private ImageView administrator;

    @FXML
    private ImageView trains;

    @FXML
    private ImageView users;

    @FXML
    private ImageView signOut;

    @FXML
    private VBox contentBox;

    @Override
    public void initialize()
    {
        initialiseLogo();

        Image adminImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/administrator.png")));
        administrator.setImage(adminImage);

        Image trainImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/train.png")));
        trains.setImage(trainImage);

        Image usersImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/group.png")));
        users.setImage(usersImage);

        Image signOutImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/sign-out.png")));
        signOut.setImage(signOutImage);

        initialiseTrains();
    }

    private void initialiseTrains()
    {
        if (!contentBox.getChildren().isEmpty())
            contentBox.getChildren().clear();

        trainsLabel.getStyleClass().add("nav-active");
        usersLabel.getStyleClass().remove("nav-active");
    }

    public void initialiseTrainsEvent(MouseEvent event)
    {
        initialiseTrains();
    }

    public void initialiseUsersEvent(MouseEvent event)
    {
        if (!contentBox.getChildren().isEmpty())
            contentBox.getChildren().clear();
        
        usersLabel.getStyleClass().add("nav-active");
        trainsLabel.getStyleClass().remove("nav-active");
    }
}
