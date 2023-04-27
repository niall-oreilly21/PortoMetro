package gui.home;

import gui.Controller;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
}