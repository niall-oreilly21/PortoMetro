package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentUniversityController extends Controller
{
    @FXML
    private Label universityLabel;

    @FXML
    private ComboBox<String> universityComboBox;

    @FXML
    private ToggleGroup cardToggleGroup;

    @FXML
    private RadioButton yesCardRadioButton;

    @FXML
    private RadioButton noCardRadioButton;

    public void initialize()
    {
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/metro_2.jpg")));
        metro1.setImage(image1);

        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        logo.setImage(logoImage);

        // TODO: this should be an ArrayList of universities fetched from the database
        List<String> universities = new ArrayList<>();
        universities.add("Option 1");
        universities.add("Option 2");
        universities.add("Option 3");

        universityComboBox.getItems().addAll(universities);

        universityComboBox.getItems().add(0, "Choose university...");
        universityComboBox.getSelectionModel().selectFirst();

        // Make first item (Choose university...) grey & disabled
        universityComboBox.setCellFactory(param -> new ListCell<>()
        {
            @Override
            protected void updateItem(String item, boolean empty)
            {
                super.updateItem(item, empty);

                if (empty || item == null)
                {
                    setText(null);
                    setDisable(false);
                } else
                {
                    setText(item);
                    setDisable(getIndex() == 0);
                    setStyle(getIndex() == 0 ? "-fx-text-fill: gray;" : "");
                }
            }
        });

        cardToggleGroup = new ToggleGroup();
        yesCardRadioButton.setToggleGroup(cardToggleGroup);
        noCardRadioButton.setToggleGroup(cardToggleGroup);
    }

    public void setScene(Scene scene)
    {
        scene.heightProperty().addListener((observable, oldValue, newValue) ->
        {
            metro1.fitHeightProperty().setValue(newValue);
        });
    }

    @FXML
    private void submitForm(ActionEvent event) throws IOException
    {
        String university = universityComboBox.getValue();
        String asterisk = "*";
        String errorColour = "#de2a1d";

        Text redAsterisk = new Text(asterisk);
        redAsterisk.setFill(Color.web(errorColour));

        if (!university.equals(universityComboBox.getItems().get(0)))
        {
            universityLabel.setGraphic(null);
            errorText.setText("");

            // TODO: add the student university data to database
            System.out.println(university);

            if (cardToggleGroup.getSelectedToggle() == yesCardRadioButton)
            {
                redirectToPage(event, Page.CARD_ZONE);
            }
            else
            {
                redirectToPage(event, Page.HOME);
            }
        }
        else
        {
            errorText.setText(asterisk + " Please choose an option");
            universityLabel.setGraphic(redAsterisk);
            universityLabel.setContentDisplay(ContentDisplay.RIGHT);
        }
    }
}
