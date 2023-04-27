package gui.home;

import com.metroporto.dao.facilitydao.MySqlFacilityDao;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.zonedao.MySqlZoneDao;
import com.metroporto.exceptions.DaoException;
import gui.Controller;
import com.metroporto.metro.Facility;
import com.metroporto.metro.Station;
import com.metroporto.metro.Zone;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StationController extends Controller
{
    private MySqlStationDao stationDao;

    private MySqlFacilityDao facilityDao;

    private MySqlZoneDao zoneDao;

    @FXML
    private VBox stationsBox;

    @FXML
    private MenuButton filterByFacilities;

    @FXML
    private MenuButton filterByZones;

    @FXML
    private TextField searchField;

    @FXML
    private HBox selectedFacilitiesIconBox;

    private List<Station> stations;

    private List<Facility> facilities;

    private List<Zone> zones;

    private List<String> selectedFacilities;

    private String selectedZone;

    private List<Station> searchedStations;

    private List<Station> filteredByFacilitiesStations;

    private List<Station> filteredByZoneStations;

    private List<Station> searchedFilteredStations;

    private String query;

    public StationController()
    {
        stationDao = new MySqlStationDao();
        facilityDao = new MySqlFacilityDao();
        zoneDao = new MySqlZoneDao();

        selectedFacilities = new ArrayList<>();
        searchedStations = new ArrayList<>();
        filteredByFacilitiesStations = new ArrayList<>();
        filteredByZoneStations = new ArrayList<>();
        searchedFilteredStations = new ArrayList<>();
        query = "";
        selectedZone = "";

        try
        {
            stations = stationDao.findAll();
            facilities = facilityDao.findAll();
            zones = zoneDao.findAll();

        } catch (DaoException de)
        {
            System.out.println(de.getMessage());
        }
    }

    public void initialize()
    {
        initialiseLogo();
        initialiseProfileIcon();
        initialiseCardIcon();

        drawStationsBox(stations);

        initialiseFilterByFacilities();
        initialiseFilterByZones();
    }

    private void drawStationsBox(List<Station> stations)
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

        for (Station station : stations)
        {
            VBox vbox = new VBox();
            vbox.getStyleClass().add("stations-box");
            vbox.setAlignment(Pos.TOP_CENTER);

            Pane pane = new Pane();
            pane.prefWidthProperty().bind(vbox.widthProperty());
            pane.setPrefHeight(280);
            String bgImage = "url('/img/stations/" + station.getStationId() + ".jpg');";
            pane.setStyle("-fx-background-image:" + bgImage + "-fx-background-size: cover");

            Label title = new Label();
            title.setPadding(new Insets(10, 0, 0, 0));
            title.setText(station.getStationName());
            title.setTextFill(Color.web("#00305f"));
            title.setWrapText(true);
            title.getStyleClass().add("subheader-label");

            Label desc = new Label();
            desc.setWrapText(true);
            desc.setText(station.getStationId() + " | Zone " + station.getZone().getZoneName());

            vbox.getChildren().addAll(pane, title, desc);

            HBox facilitiesBox = new HBox(10);
            facilitiesBox.setAlignment(Pos.CENTER);
            facilitiesBox.paddingProperty().setValue(new Insets(20, 0, 10, 0));
            int iconCount = 0;
            vbox.getChildren().add(facilitiesBox);

            List<Facility> facilitiesInStation = station.getFacilities();
            for (Facility facility : facilitiesInStation)
            {
                ImageView icon = new ImageView(new Image("/img/facilities/"
                        + facility.getFacilitiesDescription().replace(' ', '-') + ".png"));
                icon.setFitHeight(50);
                icon.setFitWidth(50);

                Tooltip tooltip = new Tooltip(capitalise(facility.getFacilitiesDescription()));
                Tooltip.install(icon, tooltip);

                facilitiesBox.getChildren().add(icon);
                iconCount++;

                if (iconCount == 5)
                {
                    facilitiesBox = new HBox(10); // 10px spacing between icons
                    facilitiesBox.setAlignment(Pos.CENTER);
                    facilitiesBox.paddingProperty().setValue(new Insets(0, 0, 10, 0));
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

    private void drawSelectedFacilitiesIcon()
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

            Tooltip tooltip = new Tooltip(capitalise(facility));
            Tooltip.install(icon, tooltip);

            selectedFacilitiesIconBox.getChildren().add(icon);
        }
    }

    private void handleFacilitiesFilter()
    {
        filteredByFacilitiesStations = new ArrayList<>();
        searchedFilteredStations = new ArrayList<>();
        for (Station station : stations)
        {
            List<String> facilitiesInStation = new ArrayList<>();
            for (Facility facility : station.getFacilities())
            {
                facilitiesInStation.add(facility.getFacilitiesDescription());
            }

            if (facilitiesInStation.containsAll(selectedFacilities))
            {
                filteredByFacilitiesStations.add(station);
            }
        }

        searchedFilteredStations.addAll(filteredByFacilitiesStations);
        drawStationsBox(searchedFilteredStations);
    }

    private void handleZoneFilter()
    {
        filteredByZoneStations = new ArrayList<>();
        searchedFilteredStations = new ArrayList<>();

        for (Station station : stations)
        {
            if (station.getZone().getZoneName().equals(selectedZone))
            {
                filteredByZoneStations.add(station);
            }
        }

        searchedFilteredStations.addAll(filteredByZoneStations);
        drawStationsBox(searchedFilteredStations);
    }

    private void handleSearchStation()
    {
        searchedStations = new ArrayList<>();
        searchedFilteredStations = new ArrayList<>();

        for (Station station : stations)
        {
            if (station.getStationName().toLowerCase().contains(query))
            {
                searchedStations.add(station);
            }
        }

        searchedFilteredStations.addAll(searchedStations);
        drawStationsBox(searchedFilteredStations);
    }

    private void handleFacilitiesFilterSearch()
    {
        filteredByFacilitiesStations = new ArrayList<>();
        searchedStations = new ArrayList<>();
        searchedFilteredStations = new ArrayList<>();

        for (Station station : stations)
        {
            List<String> facilitiesInStation = new ArrayList<>();
            for (Facility facility : station.getFacilities())
            {
                facilitiesInStation.add(facility.getFacilitiesDescription());
            }

            if (facilitiesInStation.containsAll(selectedFacilities))
            {
                filteredByFacilitiesStations.add(station);
            }

            if (station.getStationName().toLowerCase().contains(query))
            {
                searchedStations.add(station);
            }
        }

        searchedFilteredStations = filteredByFacilitiesStations.stream()
                .filter(searchedStations::contains)
                .collect(Collectors
                        .toList());

        drawStationsBox(searchedFilteredStations);
    }

    private void handleZoneFilterSearch()
    {
        filteredByZoneStations = new ArrayList<>();
        searchedStations = new ArrayList<>();
        searchedFilteredStations = new ArrayList<>();

        for (Station station : stations)
        {
            if (station.getStationName().toLowerCase().contains(query))
            {
                searchedStations.add(station);
            }

            if (station.getZone().getZoneName().equals(selectedZone))
            {
                filteredByZoneStations.add(station);
            }
        }

        searchedFilteredStations = filteredByZoneStations.stream()
                .filter(searchedStations::contains)
                .collect(Collectors
                        .toList());

        drawStationsBox(searchedFilteredStations);
    }

    private void handleFacilitiesZoneFilter()
    {
        filteredByZoneStations = new ArrayList<>();
        filteredByFacilitiesStations = new ArrayList<>();
        searchedFilteredStations = new ArrayList<>();

        for (Station station : stations)
        {
            List<String> facilitiesInStation = new ArrayList<>();
            for (Facility facility : station.getFacilities())
            {
                facilitiesInStation.add(facility.getFacilitiesDescription());
            }

            if (facilitiesInStation.containsAll(selectedFacilities))
            {
                filteredByFacilitiesStations.add(station);
            }

            if (station.getZone().getZoneName().equals(selectedZone))
            {
                filteredByZoneStations.add(station);
            }
        }

        searchedFilteredStations = filteredByZoneStations.stream()
                .filter(filteredByFacilitiesStations::contains)
                .collect(Collectors
                        .toList());

        drawStationsBox(searchedFilteredStations);
    }

    private void handleFacilitiesZoneFilterSearch()
    {
        filteredByZoneStations = new ArrayList<>();
        filteredByFacilitiesStations = new ArrayList<>();
        searchedStations = new ArrayList<>();
        searchedFilteredStations = new ArrayList<>();

        for (Station station : stations)
        {
            List<String> facilitiesInStation = new ArrayList<>();
            for (Facility facility : station.getFacilities())
            {
                facilitiesInStation.add(facility.getFacilitiesDescription());
            }

            if (facilitiesInStation.containsAll(selectedFacilities))
            {
                filteredByFacilitiesStations.add(station);
            }

            if (station.getZone().getZoneName().equals(selectedZone))
            {
                filteredByZoneStations.add(station);
            }

            if (station.getStationName().toLowerCase().contains(query))
            {
                searchedStations.add(station);
            }
        }

        searchedFilteredStations = filteredByZoneStations.stream()
                .filter(filteredByFacilitiesStations::contains)
                .collect(Collectors
                        .toList());

        searchedFilteredStations = searchedFilteredStations.stream()
                .filter(searchedStations::contains)
                .collect(Collectors
                        .toList());

        drawStationsBox(searchedFilteredStations);
    }

    private void initialiseFilterByFacilities()
    {
        List<CheckMenuItem> checkMenuItems = new ArrayList<>();
        for (Facility facility : facilities)
        {
            CheckMenuItem checkMenuItem = new CheckMenuItem(capitalise(facility.getFacilitiesDescription()));
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

                if (searchField.getText().isEmpty() && selectedZone.isEmpty())
                {
                    handleFacilitiesFilter();
                }
                else if (!searchField.getText().isEmpty() && selectedZone.isEmpty())
                {
                    handleFacilitiesFilterSearch();
                }
                else if (searchField.getText().isEmpty())
                {
                    handleFacilitiesZoneFilter();
                }
                else
                {
                    handleFacilitiesZoneFilterSearch();
                }
            });
        }
    }

    private void initialiseFilterByZones()
    {
        List<RadioMenuItem> radioMenuItems = new ArrayList<>();
        ToggleGroup toggleGroup = new ToggleGroup();

        for (Zone zone : zones)
        {
            RadioMenuItem radioMenuItem = new RadioMenuItem(capitalise(zone.getZoneName()));
            radioMenuItem.setToggleGroup(toggleGroup);
            radioMenuItems.add(radioMenuItem);
        }

        filterByZones.getItems().addAll(radioMenuItems);

        // Filter by zones every time checkbox is selected
        for (RadioMenuItem radioMenuItem : radioMenuItems)
        {
            radioMenuItem.setOnAction(event ->
            {
                selectedZone = radioMenuItem.getText();
                filterByZones.setText(selectedZone);

                if (searchField.getText().isEmpty() && selectedFacilities.isEmpty())
                {
                    handleZoneFilter();
                }
                else if (!searchField.getText().isEmpty() && selectedFacilities.isEmpty())
                {
                    handleZoneFilterSearch();
                }
                else if (searchField.getText().isEmpty())
                {
                    handleFacilitiesZoneFilter();
                }
                else
                {
                    handleFacilitiesZoneFilterSearch();
                }
            });
        }
    }

    @FXML
    private void handleSearch(ActionEvent event)
    {
        query = searchField.getText().toLowerCase();

        if (selectedFacilities.isEmpty() && selectedZone.isEmpty())
        {
            handleSearchStation();
        }
        else if (!selectedFacilities.isEmpty() && selectedZone.isEmpty())
        {
            handleFacilitiesFilterSearch();
        }
        else if (selectedFacilities.isEmpty())
        {
            handleZoneFilterSearch();
        }
        else
        {
            handleFacilitiesZoneFilterSearch();
        }
    }

    @FXML
    private void resetSearch(ActionEvent event)
    {
        searchField.setText("");

        if (!selectedFacilities.isEmpty() && selectedZone.isEmpty())
        {
            handleFacilitiesFilter();
        }
        else if (selectedFacilities.isEmpty() && !selectedZone.isEmpty())
        {
            handleZoneFilter();
        }
        else if (!selectedFacilities.isEmpty())
        {
            handleFacilitiesZoneFilter();
        }
        else
        {
            drawStationsBox(stations);
        }
    }

    @FXML
    private void clearFacilitiesFilter(ActionEvent event)
    {
        ObservableList<MenuItem> items = filterByFacilities.getItems();

        for (MenuItem item : items)
        {
            CheckMenuItem checkItem = (CheckMenuItem) item;
            checkItem.setSelected(false);
        }

        selectedFacilities = new ArrayList<>();
        drawSelectedFacilitiesIcon();

        if (!searchField.getText().isEmpty() && selectedZone.isEmpty())
        {
            handleSearchStation();
        }
        else if (searchField.getText().isEmpty() && !selectedZone.isEmpty())
        {
            handleZoneFilter();
        }
        else if (!searchField.getText().isEmpty())
        {
            handleZoneFilterSearch();
        }
        else
        {
            drawStationsBox(stations);
        }
    }

    @FXML
    private void clearZonesFilter(ActionEvent event)
    {
        filterByZones.setText("Zones");
        selectedZone = "";

        ObservableList<MenuItem> items = filterByZones.getItems();

        for (MenuItem item : items)
        {
            RadioMenuItem radioItem = (RadioMenuItem) item;
            radioItem.setSelected(false);
        }

        if (!searchField.getText().isEmpty() && selectedFacilities.isEmpty())
        {
            handleSearchStation();
        }
        else if (searchField.getText().isEmpty() && !selectedFacilities.isEmpty())
        {
            handleFacilitiesFilter();
        }
        else if (!searchField.getText().isEmpty())
        {
            handleFacilitiesFilterSearch();
        }
        else
        {
            drawStationsBox(stations);
        }
    }
}
