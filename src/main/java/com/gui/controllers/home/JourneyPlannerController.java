package com.gui.controllers.home;

import com.metroporto.metro.ConnectionSchedule;
import com.metroporto.metro.JourneyPlanner;
import com.metroporto.metro.JourneyRoute;
import com.metroporto.metro.MetroSystem;
import com.metroporto.dao.journeyplannerdao.JourneyPlannerDaoInterface;
import com.metroporto.dao.journeyplannerdao.MySqlJourneyPlannerDao;
import com.metroporto.dao.linedao.LineDaoInterface;
import com.metroporto.dao.linedao.MySqlLineDao;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.enums.EnumLabelConverter;
import com.metroporto.enums.TimetableType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Schedule;
import com.metroporto.metro.Station;
import com.metroporto.users.User;
import com.gui.controllers.Controller;
import com.gui.converters.StationConverter;
import com.gui.converters.TimeConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.time.LocalTime;
import java.util.*;

public class JourneyPlannerController extends Controller
{
    User user;

    private StationDaoInterface stationDao;
    private LineDaoInterface lineDao;
    private JourneyPlannerDaoInterface journeyPlannerDao;

    @FXML
    private VBox searchRouteBox;

    @FXML
    private Label journeyDirection;
    @FXML
    private Label journeyZones;
    @FXML
    private Label journeyDuration;

    @FXML
    private Pane stationsPane;
    @FXML
    private Line stationsLine;
    @FXML
    private Pane stationsPane2;
    @FXML
    private Line stationsLine2;

    @FXML
    private ComboBox<Station> startStations;
    @FXML
    private ComboBox<Station> endStations;
    @FXML
    private ComboBox<String> timetableType;
    @FXML
    private ComboBox<Integer> hours;
    @FXML
    private ComboBox<Integer> minutes;
    @FXML
    private CheckBox saveRouteOption;

    @FXML
    private ComboBox<JourneyPlanner> savedRoutes;

    @FXML
    private Label connectionLine;
    @FXML
    private Label connectionDirection;
    @FXML
    private Label connectionStation;
    @FXML
    private Label connectionTransferTime;
    @FXML
    private HBox connectionBox;

    private Station selectedStartStation;
    private Station selectedEndStation;
    private TimetableType selectedTimetableType;
    private int selectedHour = LocalTime.now().getHour();
    private int selectedMinute = LocalTime.now().getMinute();

    private JourneyRoute startStationLine;
    private JourneyRoute endStationLine;

    private List<Station> stations;

    private MetroSystem metroSystem;
    private JourneyPlanner journeyPlannerField;

    public JourneyPlannerController()
    {
        user = app.getUser();
        stationDao = new MySqlStationDao();
        lineDao = new MySqlLineDao();
        journeyPlannerDao = new MySqlJourneyPlannerDao();

        try
        {
            stations = stationDao.findAll();
            List<com.metroporto.metro.Line> lines = lineDao.findAll();
            metroSystem = new MetroSystem(lines);

            selectedStartStation = stationDao.findStationByStationId("PLU");
            selectedEndStation = stationDao.findStationByStationId("MAT");
            selectedTimetableType = TimetableType.MONDAY_TO_FRIDAY;
        } catch (DaoException de)
        {
            de.printStackTrace();
        }
    }

    public void initialize()
    {
        initialiseLogo();
        initialiseProfileIcon();
        initialiseCardIcon();

        initialiseStationsComboBox(startStations, selectedStartStation);
        initialiseStationsComboBox(endStations, selectedEndStation);

        initialiseTimetableTypeComboBox(timetableType, selectedTimetableType.getLabel());

        initialiseTimeComboBox(hours, 24, selectedHour);
        initialiseTimeComboBox(minutes, 59, selectedMinute);

        initialiseSavedRoutesComboBox(savedRoutes);
    }

    private void initialiseStationsComboBox(ComboBox<Station> comboBox, Station selectedStation)
    {
        comboBox.getItems().addAll(stations);
        comboBox.setConverter(new StationConverter());

        for (int i = 0; i < stations.size(); i++)
            if (stations.get(i).getStationId().equals(selectedStation.getStationId()))
                comboBox.getSelectionModel().select(i);
    }

    private void initialiseSavedRoutesComboBox(ComboBox<JourneyPlanner> comboBox)
    {
        try
        {
            List<JourneyPlanner> userSavedRoute = journeyPlannerDao.findAllJourneyPlannersByUserId(user.getUserId());

            comboBox.getItems().addAll(userSavedRoute);
        } catch (DaoException de)
        {
            de.printStackTrace();
        }

        comboBox.setCellFactory(listView -> new ListCell<>()
        {
            private final Button button = new Button();
            private final Label label = new Label();
            private final HBox hbox = new HBox(label, button);

            @Override
            public void updateItem(JourneyPlanner planner, boolean empty)
            {
                super.updateItem(planner, empty);
                if (empty)
                {
                    setText(null);
                    setGraphic(null);
                } else
                {
                    hbox.setPadding(new Insets(0, 10, 0, 10));
                    label.setText(planner.getStartStation().getStationId() + "-" + planner.getEndStation().getStationId()
                            + " | " + planner.getTimetableType().getLabel() + " | " + planner.getStartTime().toString());
                    button.setOnAction(event ->
                    {
                        try
                        {
                            journeyPlannerDao.remove(planner);
                            savedRoutes.getItems().remove(planner);
                        } catch (DaoException de)
                        {
                            de.printStackTrace();
                        }
                    });
                    button.getStyleClass().add("form-button");
                    ImageView trash = new ImageView();
                    trash.setFitHeight(15);
                    trash.setFitWidth(15);
                    Image trashImage = new Image(Objects.requireNonNull(getClass()
                            .getResourceAsStream("/img/icons/trash-can.png")));
                    trash.setImage(trashImage);
                    button.setGraphic(trash);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    hbox.setSpacing(10);
                    label.setMaxWidth(Double.MAX_VALUE);
                    HBox.setHgrow(label, Priority.ALWAYS);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(hbox);
                }
            }
        });

        comboBox.setButtonCell(new ListCell<>()
        {
            @Override
            public void updateItem(JourneyPlanner planner, boolean empty)
            {
                super.updateItem(planner, empty);
                if (empty)
                {
                    setText("View saved routes");
                    setGraphic(null);
                } else
                {
                    setText(planner.getStartStation().getStationId() + "-" + planner.getEndStation().getStationId()
                            + " | " + planner.getTimetableType().getLabel() + " | " + planner.getStartTime().toString());
                }
            }
        });
    }

    private void initialiseTimetableTypeComboBox(ComboBox<String> comboBox, String selectedType)
    {
        comboBox.getItems().add(TimetableType.MONDAY_TO_FRIDAY.getLabel());
        comboBox.getItems().add(TimetableType.SATURDAY.getLabel());
        comboBox.getItems().add(TimetableType.SUNDAY.getLabel());

        comboBox.getSelectionModel().select(selectedType);
    }

    private void initialiseTimeComboBox(ComboBox<Integer> comboBox, int end, int selectedItem)
    {
        for (int i = 0; i <= end; i++)
        {
            comboBox.getItems().add(i);
        }

        comboBox.setConverter(new TimeConverter());
        comboBox.getSelectionModel().select(selectedItem);
    }

    private void initialiseConnectionDescription()
    {
        connectionBox.setVisible(true);

        String connectionLineId = journeyPlannerField.getJourneyRoutes().get(1).getLine().getLineId();
        String connectionDirectionEndStation = journeyPlannerField.getJourneyRoutes().get(1).getRoute().getEndStation().getStationName();
        String connectionStationName = journeyPlannerField.getJourneyRoutes().get(1).getSchedules().get(0).getStation().getStationName();
        String connectionTransferTimeValue = "";

        for (Schedule schedule : journeyPlannerField.getJourneyRoutes().get(1).getSchedules())
        {
            if (schedule instanceof ConnectionSchedule)
            {
                connectionTransferTimeValue = Integer.toString(((ConnectionSchedule) schedule).getTransferTime());
            }
        }

        connectionLine.setText(connectionLineId);
        connectionDirection.setText(connectionDirectionEndStation);
        connectionStation.setText(connectionStationName);
        connectionTransferTime.setText(connectionTransferTimeValue);
    }

    public void searchRoute(ActionEvent event)
    {
        JourneyPlanner journeyPlanner = initialiseSelectedItems();
        journeyPlannerField = journeyPlanner;
        searchRoute(journeyPlanner);
    }

    private JourneyPlanner initialiseSelectedItems()
    {
        selectedStartStation = startStations.getSelectionModel().getSelectedItem();
        selectedEndStation = endStations.getSelectionModel().getSelectedItem();
        EnumLabelConverter labelConverter = new EnumLabelConverter();
        selectedTimetableType = labelConverter.fromLabel(timetableType.getSelectionModel().getSelectedItem(),
                TimetableType.class);
        selectedHour = hours.getSelectionModel().getSelectedItem();
        selectedMinute = minutes.getSelectionModel().getSelectedItem();

        return new JourneyPlanner(selectedStartStation, selectedEndStation,
                LocalTime.of(selectedHour, selectedMinute), selectedTimetableType);
    }

    private void searchRoute(JourneyPlanner journeyPlanner)
    {
        Set<String> zoneNames = new LinkedHashSet<>();

        searchRouteBox.setVisible(true);

        journeyPlanner.setMetroSystem(metroSystem);
        journeyPlanner.start();

        startStationLine = journeyPlanner.getJourneyRoutes().get(0);

        zoneNames.add(startStationLine.getSchedules().get(0).getStation().getZone().getZoneName());

        drawStationNodes(startStationLine, colours.get(startStationLine.getLine().getLineId()), stationsPane,
                stationsLine, 10, 6, 27, 0, 0);

        if (journeyPlanner.getJourneyRoutes().size() > 1)
        {
            initialiseConnectionDescription();

            endStationLine = journeyPlanner.getJourneyRoutes().get(1);

            int endStationSchedulesSize = endStationLine.getSchedules().size();
            zoneNames.add(endStationLine.getSchedules().get(0).getStation().getZone().getZoneName());
            zoneNames.add(endStationLine.getSchedules().get(endStationSchedulesSize - 1).getStation().getZone().getZoneName());

            stationsLine2.setVisible(true);

            drawStationNodes(endStationLine, colours.get(endStationLine.getLine().getLineId()),
                    stationsPane2, stationsLine2, 10, 6, 27, 0, 0);
        } else
        {
            int startStationSchedulesSize = startStationLine.getSchedules().size();
            zoneNames.add(startStationLine.getSchedules().get(startStationSchedulesSize - 1).getStation().getZone().getZoneName());

            connectionBox.setVisible(false);
            stationsPane2.getChildren().removeIf(node -> !(node instanceof Line));
            stationsLine2.setVisible(false);

        }

        journeyDirection.setText(journeyPlanner.getJourneyRoutes().get(0).getRoute().getEndStation().getStationName());
        StringBuilder zoneNameText = new StringBuilder();
        int count = 0;

        for (String zone : zoneNames)
        {
            count++;
            if (count != zoneNames.size())
                zoneNameText.append(zone).append(", ");
            else
                zoneNameText.append(zone);
        }
        journeyZones.setText(zoneNameText.toString());
        journeyDuration.setText(journeyPlanner.getTotalJourneyDuration() + " mins");

        if (saveRouteOption.isSelected())
        {
            try
            {
                boolean successful = journeyPlannerDao.insertJourneyPlannerForPassenger(user, journeyPlanner);

                if (successful)
                    savedRoutes.getItems().add(journeyPlanner);
            } catch (DaoException de)
            {
                de.printStackTrace();
            }
        }
    }

    public void viewSavedRoute(ActionEvent event)
    {
        JourneyPlanner selectedJourneyPlanner = savedRoutes.getSelectionModel().getSelectedItem();
        journeyPlannerField = selectedJourneyPlanner;
        searchRoute(selectedJourneyPlanner);

        startStations.getSelectionModel().select(selectedJourneyPlanner.getStartStation());
        endStations.getSelectionModel().select(selectedJourneyPlanner.getEndStation());
        timetableType.getSelectionModel().select(selectedJourneyPlanner.getTimetableType().getLabel());
        hours.getSelectionModel().select(selectedJourneyPlanner.getStartTime().getHour());
        minutes.getSelectionModel().select(selectedJourneyPlanner.getStartTime().getMinute());
    }

    public void clearSavedRoute(ActionEvent event)
    {
        searchRouteBox.setVisible(false);

        savedRoutes.getSelectionModel().clearSelection();
        try
        {
            selectedStartStation = stationDao.findStationByStationId("PLU");
            selectedEndStation = stationDao.findStationByStationId("MAT");
        } catch (DaoException de)
        {
            de.printStackTrace();
        }
        selectedTimetableType = TimetableType.MONDAY_TO_FRIDAY;
        selectedHour = LocalTime.now().getHour();
        selectedMinute = LocalTime.now().getMinute();

        startStations.getSelectionModel().select(selectedStartStation);
        endStations.getSelectionModel().select(selectedEndStation);
        timetableType.getSelectionModel().select(selectedTimetableType.getLabel());
        hours.getSelectionModel().select(selectedHour);
        minutes.getSelectionModel().select(selectedMinute);
    }

    @Override
    public void setScene(Scene scene)
    {
        stationsPane.prefWidthProperty().bind(scene.widthProperty());
        stationsPane2.prefWidthProperty().bind(scene.widthProperty());
        scene.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            if (startStationLine != null)
            {
                drawStationNodes(startStationLine, colours.get(startStationLine.getLine().getLineId()), stationsPane,
                        stationsLine, 10, 6, 27, 0, 0);

                if (journeyPlannerField.getJourneyRoutes().size() > 1)
                {
                    drawStationNodes(endStationLine, colours.get(endStationLine.getLine().getLineId()), stationsPane2,
                            stationsLine2, 10, 6, 27, 0, 0);
                }
            }
        });
    }
}