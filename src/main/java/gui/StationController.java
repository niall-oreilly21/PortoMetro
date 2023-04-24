package gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StationController
{
    private final ControllersUtil util = new ControllersUtil();

    @FXML
    private ImageView logo;

    @FXML
    private ImageView profile;

    @FXML
    private ImageView card;

    @FXML
    private VBox stationsBox;

    @FXML
    private MenuButton filterByFacilities;

    @FXML
    private TextField searchField;

    @FXML
    private HBox selectedFacilitiesIconBox;

    private final List<String> stations = List.of("DJ2", "STO", "IPO", "ARA", "ARV", "AZR", "BGM",
            "BOL", "BOT", "BRI", "C24", "CAM", "CAR", "CAS", "CDM", "CDG");

    private final List<String> facilities = List.of("airport", "bus stop", "café", "grocery shop", "hospital", "lift",
            "lockers", "parking", "taxi", "toilets", "wheelchair accessible");

    private List<String> selectedFacilities = new ArrayList<>();

    private List<String> searchedStations = new ArrayList<>();

    private List<String> filteredStations = new ArrayList<>();

    private List<String> searchedFilteredStations = new ArrayList<>();

    public void initialize()
    {
        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        logo.setImage(logoImage);

        Image profileImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/profile.png")));
        profile.setImage(profileImage);

        Image cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/card.png")));
        card.setImage(cardImage);

        drawStationsBox(stations);

        List<CheckMenuItem> checkMenuItems = new ArrayList<>();
        for (String facility : facilities)
        {
            CheckMenuItem checkMenuItem = new CheckMenuItem(util.capitalise(facility));
            checkMenuItems.add(checkMenuItem);
        }

        filterByFacilities.getItems().addAll(checkMenuItems);

        // Filter by facilities every time checkbox is selected
        for (CheckMenuItem checkMenuItem : checkMenuItems)
        {
            checkMenuItem.setOnAction(event ->
            {
                if (!selectedFacilities.contains(checkMenuItem.getText().toLowerCase()))
                {
                    selectedFacilities.add(checkMenuItem.getText().toLowerCase());
                } else
                {
                    selectedFacilities.remove(checkMenuItem.getText().toLowerCase());
                }

                drawSelectedFacilitiesIcon();
                handleFilterFacilities();
            });
        }
    }

    public void redirectToHome(MouseEvent event) throws IOException
    {
        util.redirectToHome(event);
    }

    public void redirectToSchedule(MouseEvent event) throws IOException
    {
        util.redirectToSchedule(event);
    }

    public void redirectToJourneyRoute(MouseEvent event) throws IOException
    {
        util.redirectToJourneyRoute(event);
    }

    public void redirectToProfile(MouseEvent event) throws IOException
    {
        util.redirectToProfile(event);
    }

    public void redirectToCard(MouseEvent event) throws IOException
    {
        util.redirectToCard(event);
    }

    public void drawStationsBox(List<String> stations)
    {
        if (!stationsBox.getChildren().isEmpty())
        {
            stationsBox.getChildren().clear();
        }

        GridPane grid = new GridPane();
        grid.setHgap(50);
        grid.setVgap(50);
        grid.setPadding(new Insets(20, 50, 20, 50));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33);
        grid.getColumnConstraints().addAll(col1, col2, col3);

        int row = 0;
        int col = 0;

        for (String station : stations)
        {
            VBox vbox = new VBox();
            vbox.getStyleClass().add("stations-box");
            vbox.setAlignment(Pos.CENTER);

            Pane pane = new Pane();
            pane.prefWidthProperty().bind(vbox.widthProperty());
            pane.setPrefHeight(280);
            String bgImage = "url('/img/stations/" + station + ".jpg');";
            pane.setStyle("-fx-background-image:" + bgImage + "-fx-background-size: cover");

            Label title = new Label();
            title.setPadding(new Insets(10, 0, 0, 0));
            title.setText(station);
            title.setTextFill(Color.web("#00305f"));
            title.setWrapText(true);
            title.getStyleClass().add("subheader-label");

            Label desc = new Label();
            desc.setWrapText(true);
            desc.setText("Line A | Zone PRT3");

            vbox.getChildren().addAll(pane, title, desc);

            HBox facilitiesBox = new HBox(10);
            facilitiesBox.setAlignment(Pos.CENTER);
            facilitiesBox.paddingProperty().setValue(new Insets(20, 0, 10, 0));
            int iconCount = 0;
            vbox.getChildren().add(facilitiesBox);

            List<String> facilitiesInStation = getFacilitiesForStation(station);
            for (String facility : facilitiesInStation)
            {
                ImageView icon = new ImageView(new Image("/img/facilities/"
                        + facility.replace(' ', '-') + ".png"));
                icon.setFitHeight(50);
                icon.setFitWidth(50);

                Tooltip tooltip = new Tooltip(util.capitalise(facility));
                Tooltip.install(icon, tooltip);

                facilitiesBox.getChildren().add(icon);
                iconCount++;

                if (iconCount == 5)
                {
                    facilitiesBox = new HBox(10); // 10px spacing between icons
                    facilitiesBox.setAlignment(Pos.CENTER);
                    facilitiesBox.paddingProperty().setValue(new Insets(0, 0, 5, 0));
                    iconCount = 0;
                    vbox.getChildren().add(facilitiesBox);
                }
            }

            grid.add(vbox, col, row);

            col++;
            if (col == 3)
            {
                row++;
                col = 0;
            }
        }

        stationsBox.getChildren().add(grid);
    }

    // Temp function for testing purposes
    public List<String> getFacilitiesForStation(String station)
    {
        List<String> facilitiesInStation;

        if (station.equalsIgnoreCase("DJ2"))
        {
            facilitiesInStation = List.of("lockers");
        } else
        {
            facilitiesInStation = List.of("bus stop", "café", "toilets");
        }

        return facilitiesInStation;
    }

    public void drawSelectedFacilitiesIcon()
    {
        if (!selectedFacilitiesIconBox.getChildren().isEmpty())
        {
            selectedFacilitiesIconBox.getChildren().clear();
        }

        for (String facility : selectedFacilities)
        {
            ImageView icon = new ImageView();
            icon.setFitHeight(30);
            icon.setFitWidth(30);
            icon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/img/facilities/" + facility.replace(' ', '-') + ".png"))));

            Tooltip tooltip = new Tooltip(util.capitalise(facility));
            Tooltip.install(icon, tooltip);

            selectedFacilitiesIconBox.getChildren().add(icon);
        }
    }

    public void handleFilterFacilities()
    {
        String query = searchField.getText().toLowerCase();
        filteredStations = new ArrayList<>();
        searchedStations = new ArrayList<>();

        for (String station : stations)
        {
            List<String> facilitiesInStation = getFacilitiesForStation(station);

            if (facilitiesInStation.containsAll(selectedFacilities))
            {
                filteredStations.add(station);
            }

            if (station.toLowerCase().contains(query))
            {
                searchedStations.add(station);
            }
        }

        searchedFilteredStations = filteredStations.stream()
                .filter(searchedStations::contains)
                .collect(Collectors
                        .toList());

        drawStationsBox(searchedFilteredStations);
    }

    public void handleSearch(ActionEvent event)
    {
        String query = searchField.getText().toLowerCase();
        searchedStations = new ArrayList<>();
        filteredStations = new ArrayList<>();

        for (String station : stations)
        {
            if (station.toLowerCase().contains(query))
            {
                searchedStations.add(station);
            }

            if (selectedFacilities.isEmpty() || getFacilitiesForStation(station).containsAll(selectedFacilities))
            {
                filteredStations.add(station);
            }
        }

        searchedFilteredStations = searchedStations.stream()
                .filter(filteredStations::contains)
                .collect(Collectors
                        .toList());

        drawStationsBox(searchedFilteredStations);
    }

    public void resetSearch(ActionEvent event)
    {
        searchedFilteredStations = filteredStations;
        searchField.setText("");

        if (!selectedFacilities.isEmpty())
        {
            drawStationsBox(searchedFilteredStations);
        }
        else
        {
            drawStationsBox(stations);
        }
    }

    public void clearFilter(ActionEvent event)
    {
        ObservableList<MenuItem> items = filterByFacilities.getItems();

        for (MenuItem item : items)
        {
            CheckMenuItem checkItem = (CheckMenuItem) item;
            checkItem.setSelected(false);
        }

        selectedFacilities = new ArrayList<>();
        drawSelectedFacilitiesIcon();

        if (!searchField.getText().isEmpty())
        {
            searchedFilteredStations = searchedStations;
            drawStationsBox(searchedFilteredStations);
        }
        else
        {
            drawStationsBox(stations);
        }
    }
}
