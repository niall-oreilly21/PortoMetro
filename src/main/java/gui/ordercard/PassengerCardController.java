package gui.ordercard;

import com.metroporto.enums.Folder;
import gui.Controller;
import com.metroporto.enums.Page;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.Year;
import java.util.Calendar;
import java.util.Objects;

public class PassengerCardController extends Controller
{
    @FXML
    private RadioButton yesCardRadioButton;

    @FXML
    private RadioButton noCardRadioButton;

    @FXML
    private RadioButton greyCardRadioButton;

    @FXML
    private RadioButton blueCardRadioButton;

    @FXML
    private RadioButton touristCardRadioButton;

    @FXML
    private VBox cardBox;

    @FXML
    private ToggleGroup cardToggleGroup;

    @FXML
    private ToggleGroup cardTypeToggleGroup;

    @FXML
    private ComboBox<Integer> blueNumTripsComboBox;

    @FXML
    private ComboBox<Integer> touristNumTripsComboBox;

    @FXML
    private VBox touristCardBox;

    @FXML
    private VBox blueCardBox;

    @FXML
    private VBox greyCardBox;

    @FXML
    private Label cardTypeLabel;

    @FXML
    private ComboBox<String> startMonthComboBox;

    @FXML
    private ComboBox<Integer> startYearComboBox;

    @FXML
    private ComboBox<String> endMonthComboBox;

    @FXML
    private ComboBox<Integer> endYearComboBox;

    public void initialize()
    {
        initialiseLogo();
        initialiseMetroImage("metro_2");

        // Set toggle group to yes/no card radio buttons
        cardToggleGroup = new ToggleGroup();
        yesCardRadioButton.setToggleGroup(cardToggleGroup);
        noCardRadioButton.setToggleGroup(cardToggleGroup);

        // Set toggle group to card type radio buttons
        cardTypeToggleGroup = new ToggleGroup();
        greyCardRadioButton.setToggleGroup(cardTypeToggleGroup);
        blueCardRadioButton.setToggleGroup(cardTypeToggleGroup);
        touristCardRadioButton.setToggleGroup(cardTypeToggleGroup);

        // Bind the visibility and managed property of the "yes" button
        // i.e. cardBox should only be visible if "yes" is selected
        cardBox.visibleProperty().bind(yesCardRadioButton.selectedProperty());
        cardBox.managedProperty().bind(yesCardRadioButton.selectedProperty());

        // If "no" is selected, clear the selected card type radio button
        noCardRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                clearCardTypeSelection();
            }
        });

        // blueCardBox should only be visible if "Blue card" is selected
        blueCardBox.visibleProperty().bind(blueCardRadioButton.selectedProperty());
        blueCardBox.managedProperty().bind(blueCardRadioButton.selectedProperty());

        // Populate blueNumTrips combo box with values 1-10
        for (int i=1; i <= 10; i++)
            blueNumTripsComboBox.getItems().add(i);

        // Make "1" the default selected option for blueNumTrips combo box
        blueNumTripsComboBox.getSelectionModel().selectFirst();

        // touristCardBox should only be visible if "Tourist card" is selected
        touristCardBox.visibleProperty().bind(touristCardRadioButton.selectedProperty());
        touristCardBox.managedProperty().bind(touristCardRadioButton.selectedProperty());

        // Populate touristNumTrips combo box with values 1-10
        for (int i=1; i <= 10; i++)
            touristNumTripsComboBox.getItems().add(i);

        // Make "1" the default selected option for touristNumTrips combo box
        touristNumTripsComboBox.getSelectionModel().selectFirst();

        // greyCardBox should only be visible when grey card is selected
        greyCardBox.visibleProperty().bind(greyCardRadioButton.selectedProperty());
        greyCardBox.managedProperty().bind(greyCardRadioButton.selectedProperty());

        // Populate startMonth combo box
        ObservableList<String> startMonths =
                FXCollections.observableArrayList(
                        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                );
        startMonthComboBox.setItems(startMonths);

        // Populate endMonth combo box
        ObservableList<String> endMonths =
                FXCollections.observableArrayList(
                        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                );
        endMonthComboBox.setItems(endMonths);

        // Populate startYear combo box
        int currentYear = Year.now().getValue();
        ObservableList<Integer> startYears =
                FXCollections.observableArrayList(
                        currentYear, currentYear + 1
                );
        startYearComboBox.setItems(startYears);

        // Populate endYear combo box
        ObservableList<Integer> endYears =
                FXCollections.observableArrayList(
                        currentYear, currentYear + 1
                );
        endYearComboBox.setItems(endYears);

        // Set default values as current month and year
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        startMonthComboBox.getSelectionModel().select(currentMonth);
        startYearComboBox.getSelectionModel().select(startYears.indexOf(currentYear));

        endMonthComboBox.getSelectionModel().select((currentMonth + 1) % 12);
        endYearComboBox.getSelectionModel().select(endYears.indexOf(currentYear));

        // Add listener to end month combo box to prevent user
        endMonthComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (startYearComboBox.getValue() != null && endYearComboBox.getValue() != null) {
                if (endYearComboBox.getValue() < startYearComboBox.getValue()) {
                    endYearComboBox.setValue(startYearComboBox.getValue());
                }

                // Set end month value to the month after the start month value
                if (endYearComboBox.getValue().equals(startYearComboBox.getValue())
                        && endMonths.indexOf(newValue) <= startMonths.indexOf(startMonthComboBox.getValue()))
                {
                    endMonthComboBox.setValue(startMonths.get((startMonths.indexOf(startMonthComboBox.getValue()) + 1) % 12));

                    if (startMonths.indexOf(startMonthComboBox.getValue()) == startMonths.size() - 1)
                    {
                        endYearComboBox.setValue(endYears.indexOf(endYearComboBox.getValue()) + 1);
                    }
                }
            }
        });

        // Add listener to end year combo box
        endYearComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (startYearComboBox.getValue() != null && endYearComboBox.getValue() != null) {
                if (endYearComboBox.getValue() < startYearComboBox.getValue()) {
                    endYearComboBox.setValue(startYearComboBox.getValue());
                }
                if (endYearComboBox.getValue().equals(startYearComboBox.getValue())
                        && endMonths.indexOf(endMonthComboBox.getValue()) <= startMonths.indexOf(startMonthComboBox.getValue())) {
                    endMonthComboBox.setValue(startMonths.get((startMonths.indexOf(startMonthComboBox.getValue()) + 1) % 12));
                }
            }
        });

        // Add listener to start year combo box
        startYearComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (startYearComboBox.getValue() != null && endYearComboBox.getValue() != null) {
                if (endYearComboBox.getValue() <= startYearComboBox.getValue()) {
                    startYearComboBox.setValue(endYearComboBox.getValue());
                }

                if (endYearComboBox.getValue().equals(startYearComboBox.getValue())
                        && endMonths.indexOf(endMonthComboBox.getValue()) <= startMonths.indexOf(startMonthComboBox.getValue())) {
                    startMonthComboBox.setValue(startMonths.get((startMonths.indexOf(startMonthComboBox.getValue()) + 1) % 12));
                }
            }
        });

        // Add listener to start month combo box
        startMonthComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (startYearComboBox.getValue() != null && endYearComboBox.getValue() != null) {
                if (endYearComboBox.getValue() < startYearComboBox.getValue()) {
                    startYearComboBox.setValue(endYearComboBox.getValue());
                }
                if (endYearComboBox.getValue().equals(startYearComboBox.getValue())
                        && endMonths.indexOf(endMonthComboBox.getValue()) <= startMonths.indexOf(newValue))
                {
                    endMonthComboBox.setValue(startMonths.get((startMonths.indexOf(startMonthComboBox.getValue()) + 1) % 12));

                    if (startMonths.indexOf(startMonthComboBox.getValue()) == 11)
                    {
                        if (startYears.indexOf(startYearComboBox.getValue()) == 1)
                        {
                            startMonthComboBox.setValue(startMonths.get(10));
                            endMonthComboBox.setValue(endMonths.get(11));
                        }

                        if (endYears.indexOf(endYearComboBox.getValue()) == 0)
                        {
                            endYearComboBox.setValue(endYears.get(1));

                            if (startMonths.indexOf(startMonthComboBox.getValue()) == startMonths.size() - 1)
                            {
                                endYearComboBox.setValue(endYears.get(1));
                            }
                        }
                    }


                }
            }
        });
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

        if (cardToggleGroup.getSelectedToggle() == yesCardRadioButton)
        {
            if (cardTypeToggleGroup.getSelectedToggle() == null)
            {
                errorText.setText(asterisk + " Choose a card type");
                cardTypeLabel.setGraphic(redAsterisk);
                cardTypeLabel.setContentDisplay(ContentDisplay.RIGHT);
            }
            else
            {
                cardTypeLabel.setGraphic(null);
                errorText.setText("");

                // TODO: Add to database

                redirectToPage(event, Folder.ORDER_CARD, Page.CARD_ZONE);
            }
        }
        else
        {
            redirectToPage(event, Folder.HOME, Page.HOME);
        }
    }

    private void clearCardTypeSelection() {
        // Clear the selection of the card type radio buttons
        cardTypeToggleGroup.selectToggle(null);
    }
}
