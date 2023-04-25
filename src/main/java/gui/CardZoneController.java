package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CardZoneController extends Controller
{
    @FXML
    private RadioButton allZonesRadioButton;

    @FXML
    private RadioButton threeZonesRadioButton;

    @FXML
    private ToggleGroup zoneToggleGroup;

    @FXML
    private VBox zonesOptionBox;

    @FXML
    private Label zoneSelectionLabel;

    // TODO: this should be fetched from database
    private List<String> checkboxTexts = List.of("PV_VC", "VCD3", "VCD8", "MTS1", "MAI1", "MAI2", "MAI4",
            "PRT1", "PRT2", "PRT3", "GDM1", "VNG1");

    private int numSelected = 0;

    public void initialize()
    {
        // TODO: Change picture to zone map picture
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/metro_4.jpg")));
        metro1.setImage(image1);

        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        logo.setImage(logoImage);

        zoneToggleGroup = new ToggleGroup();
        allZonesRadioButton.setToggleGroup(zoneToggleGroup);
        threeZonesRadioButton.setToggleGroup(zoneToggleGroup);

        zonesOptionBox.visibleProperty().bind(threeZonesRadioButton.selectedProperty());
        zonesOptionBox.managedProperty().bind(threeZonesRadioButton.selectedProperty());

        threeZonesRadioButton.setOnAction(event ->
        {
            if (threeZonesRadioButton.isSelected())
            {
                addCheckboxes();
            }
        });
    }

    public void addCheckboxes()
    {
        int count = 0;
        HBox currentHBox = new HBox(20); // Start by adding checkboxes to the first HBox

        if (zonesOptionBox.getChildren().size() > 0) {
            zonesOptionBox.getChildren().clear(); // Remove existing checkboxes
        }

        zonesOptionBox.getChildren().add(currentHBox);
        for (String checkboxText : checkboxTexts)
        {
            CheckBox checkBox = new CheckBox(checkboxText);
            checkBox.setOnAction(this::handleCheckBoxAction);
            currentHBox.getChildren().add(checkBox);
            HBox.setMargin(checkBox, new Insets(15, 0, 0, 0));
            count++;
            if (count == 3)
            {
                // Create a new HBox and add checkboxes to that
                currentHBox = new HBox(20); // 20 is the spacing between checkboxes
                zonesOptionBox.getChildren().add(currentHBox);
                count = 0; // Reset the count
            }
        }
    }

    @FXML
    private void handleCheckBoxAction(ActionEvent event)
    {
        CheckBox checkBox = (CheckBox) event.getSource();
        if (checkBox.isSelected())
        {
            numSelected++;
        } else
        {
            numSelected--;
        }
        updateCheckBoxes();
    }

    private void updateCheckBoxes()
    {
        if (numSelected >= 3)
        {
            disableUnselectedCheckBoxes();
        } else
        {
            enableAllCheckBoxes();
        }
    }

    private void disableUnselectedCheckBoxes()
    {
        for (Node node : zonesOptionBox.getChildren())
        {
            if (node instanceof HBox)
            {
                HBox hbox = (HBox) node;
                for (Node child : hbox.getChildren())
                {
                    if (child instanceof CheckBox)
                    {
                        CheckBox checkBox = (CheckBox) child;
                        if (!checkBox.isSelected())
                        {
                            checkBox.setDisable(true);
                        }
                    }
                }
            }
        }
    }

    private void enableAllCheckBoxes()
    {
        for (Node node : zonesOptionBox.getChildren())
        {
            if (node instanceof HBox)
            {
                for (Node checkBox : ((HBox) node).getChildren())
                {
                    if (checkBox instanceof CheckBox)
                    {
                        checkBox.setDisable(false);
                    }
                }
            }
        }
    }

    public List<CheckBox> getSelectedCheckboxes() {
        List<CheckBox> selected = new ArrayList<>();
        for (Node hBoxNode : zonesOptionBox.getChildren()) {
            if (hBoxNode instanceof HBox) {
                HBox hBox = (HBox) hBoxNode;
                for (Node checkboxNode : hBox.getChildren()) {
                    if (checkboxNode instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) checkboxNode;
                        if (checkBox.isSelected()) {
                            selected.add(checkBox);
                        }
                    }
                }
            }
        }
        return selected;
    }

    public void setScene(Scene scene)
    {
        scene.heightProperty().addListener((observable, oldValue, newValue) ->
        {
            metro1.fitHeightProperty().setValue(newValue);
        });
    }

    public void submitForm(ActionEvent event) throws IOException
    {
        String asterisk = "*";
        String errorColour = "#de2a1d";

        Text redAsterisk = new Text(asterisk);
        redAsterisk.setFill(Color.web(errorColour));

        List<CheckBox> selectedZones = getSelectedCheckboxes();

        if (threeZonesRadioButton.isSelected() && selectedZones.size() < 3)
        {
            errorText.setText(asterisk + " Choose 3 zones");
            zoneSelectionLabel.setGraphic(redAsterisk);
            zoneSelectionLabel.setContentDisplay(ContentDisplay.RIGHT);
        }
        else
        {
            zoneSelectionLabel.setGraphic(null);
            errorText.setText("");

            for (CheckBox checkBox : selectedZones)
            {
                System.out.println(checkBox.getText());
            }

            // TODO: Add to database

            redirectToPage(event, Page.CARD_ZONE);
        }
    }
}
