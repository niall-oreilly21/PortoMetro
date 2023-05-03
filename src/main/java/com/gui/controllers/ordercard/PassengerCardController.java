package com.gui.controllers.ordercard;

import com.metroporto.cards.BlueCard;
import com.metroporto.cards.GreyCard;
import com.metroporto.cards.TourCard;
import com.metroporto.enums.Folder;
import com.gui.controllers.Controller;
import com.metroporto.enums.Page;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

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

    @FXML
    private Label cardValidityLabel;

    public void initialize()
    {
        initialiseLogo();
        initialiseMetroImage("metro-2");

        initialiseToggleGroups();

        initialiseCardBox();
        initialiseBlueCardBox();
        initialiseGreyCardBox();
    }

    private void initialiseToggleGroups()
    {
        cardToggleGroup = new ToggleGroup();
        yesCardRadioButton.setToggleGroup(cardToggleGroup);
        noCardRadioButton.setToggleGroup(cardToggleGroup);

        cardTypeToggleGroup = new ToggleGroup();
        greyCardRadioButton.setToggleGroup(cardTypeToggleGroup);
        blueCardRadioButton.setToggleGroup(cardTypeToggleGroup);
        touristCardRadioButton.setToggleGroup(cardTypeToggleGroup);
    }

    private void initialiseCardBox()
    {
        cardBox.visibleProperty().bind(yesCardRadioButton.selectedProperty());
        cardBox.managedProperty().bind(yesCardRadioButton.selectedProperty());

        noCardRadioButton.selectedProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue)
            {
                clearCardTypeSelection();
            }
        });
    }

    private void initialiseBlueCardBox()
    {
        blueCardBox.visibleProperty().bind(blueCardRadioButton.selectedProperty());
        blueCardBox.managedProperty().bind(blueCardRadioButton.selectedProperty());

        for (int i = 1; i <= 10; i++)
            blueNumTripsComboBox.getItems().add(i);

        blueNumTripsComboBox.getSelectionModel().selectFirst();

        touristCardBox.visibleProperty().bind(touristCardRadioButton.selectedProperty());
        touristCardBox.managedProperty().bind(touristCardRadioButton.selectedProperty());

        for (int i = 1; i <= 10; i++)
            touristNumTripsComboBox.getItems().add(i);

        touristNumTripsComboBox.getSelectionModel().selectFirst();
    }

    private void initialiseGreyCardBox()
    {
        greyCardBox.visibleProperty().bind(greyCardRadioButton.selectedProperty());
        greyCardBox.managedProperty().bind(greyCardRadioButton.selectedProperty());

        ObservableList<String> startMonths =
                FXCollections.observableArrayList(
                        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                );
        startMonthComboBox.setItems(startMonths);

        ObservableList<String> endMonths =
                FXCollections.observableArrayList(
                        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                );
        endMonthComboBox.setItems(endMonths);

        int currentYear = Year.now().getValue();
        ObservableList<Integer> startYears =
                FXCollections.observableArrayList(
                        currentYear, currentYear + 1
                );
        startYearComboBox.setItems(startYears);

        ObservableList<Integer> endYears =
                FXCollections.observableArrayList(
                        currentYear, currentYear + 1
                );
        endYearComboBox.setItems(endYears);

        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        startMonthComboBox.getSelectionModel().select(currentMonth);
        startYearComboBox.getSelectionModel().select(startYears.indexOf(currentYear));

        endMonthComboBox.getSelectionModel().select((currentMonth + 1) % 12);
        endYearComboBox.getSelectionModel().select(endYears.indexOf(currentYear));

        endMonthComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            if (startYearComboBox.getValue() != null && endYearComboBox.getValue() != null)
            {
                if (endYearComboBox.getValue() < startYearComboBox.getValue())
                {
                    endYearComboBox.setValue(startYearComboBox.getValue());
                }

                if (endYearComboBox.getValue().equals(startYearComboBox.getValue())
                        && endMonths.indexOf(newValue) <= startMonths.indexOf(startMonthComboBox.getValue()))
                {
                    endMonthComboBox.setValue(startMonthComboBox.getValue());

                    if (startMonths.indexOf(startMonthComboBox.getValue()) == startMonths.size() - 1)
                    {
                        endYearComboBox.setValue(endYears.indexOf(endYearComboBox.getValue()) + 1);
                    }
                }
            }
        });

        endYearComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            if (startYearComboBox.getValue() != null && endYearComboBox.getValue() != null)
            {
                if (endYearComboBox.getValue() < startYearComboBox.getValue())
                {
                    endYearComboBox.setValue(startYearComboBox.getValue());
                }
                if (endYearComboBox.getValue().equals(startYearComboBox.getValue())
                        && endMonths.indexOf(endMonthComboBox.getValue()) <= startMonths.indexOf(startMonthComboBox.getValue()))
                {
                    endMonthComboBox.setValue(startMonthComboBox.getValue());
                }
            }
        });

        startYearComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            if (startYearComboBox.getValue() != null && endYearComboBox.getValue() != null)
            {
                if (endYearComboBox.getValue() <= startYearComboBox.getValue())
                {
                    startYearComboBox.setValue(endYearComboBox.getValue());
                }

                if (endYearComboBox.getValue().equals(startYearComboBox.getValue())
                        && endMonths.indexOf(endMonthComboBox.getValue()) <= startMonths.indexOf(startMonthComboBox.getValue()))
                {
                    startMonthComboBox.setValue(endMonthComboBox.getValue());
                }
            }
        });

        startMonthComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            if (startYearComboBox.getValue() != null && endYearComboBox.getValue() != null)
            {
                if (endYearComboBox.getValue() < startYearComboBox.getValue())
                {
                    startYearComboBox.setValue(endYearComboBox.getValue());
                }
                if (endYearComboBox.getValue().equals(startYearComboBox.getValue())
                        && endMonths.indexOf(endMonthComboBox.getValue()) <= startMonths.indexOf(newValue))
                {
                    endMonthComboBox.setValue(startMonthComboBox.getValue());
                }
            }
        });
    }

    public void setScene(Scene scene)
    {
        scene.heightProperty().addListener((observable, oldValue, newValue) ->
                metro.fitHeightProperty().setValue(newValue)
        );
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
            } else
            {
                LocalDate now = LocalDate.now();

                if (greyCardRadioButton.isSelected())
                {
                    if (LocalDate.of(startYearComboBox.getValue(),
                            startMonthComboBox.getSelectionModel().getSelectedIndex() + 1, now.getDayOfMonth()).isBefore(now)
                            || LocalDate.of(endYearComboBox.getValue(), endMonthComboBox.getSelectionModel().getSelectedIndex() + 1,
                            now.getDayOfMonth()).isBefore(now))
                    {
                        errorText.setText("Start date/end date can't be before this month");
                        cardValidityLabel.setGraphic(redAsterisk);
                        cardValidityLabel.setContentDisplay(ContentDisplay.RIGHT);
                    } else
                    {
                        cardTypeLabel.setGraphic(null);
                        errorText.setText("");

                        LocalDate startDate = LocalDate.of(startYearComboBox.getValue(),
                                startMonthComboBox.getSelectionModel().getSelectedIndex() + 1, 1);
                        LocalDate endDate = LocalDate.of(now.getYear(),
                                        endMonthComboBox.getSelectionModel().getSelectedIndex() + 1, 1)
                                .with(TemporalAdjusters.lastDayOfMonth());

                        userCard = new GreyCard(startDate, endDate);

                        redirectToPage(event, Folder.ORDER_CARD, Page.CARD_ZONE);
                    }
                }

                if (blueCardRadioButton.isSelected())
                {
                    userCard = new BlueCard(blueNumTripsComboBox.getValue());

                    redirectToPage(event, Folder.ORDER_CARD, Page.CARD_ZONE);
                }

                if (touristCardRadioButton.isSelected())
                {
                    userCard = new TourCard(touristNumTripsComboBox.getValue());

                    redirectToPage(event, Folder.ORDER_CARD, Page.CARD_ZONE);
                }
            }
        } else
        {
            redirectToPage(event, Folder.HOME, Page.HOME);
        }
    }

    private void clearCardTypeSelection()
    {
        cardTypeToggleGroup.selectToggle(null);
    }
}
