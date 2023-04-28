package gui.home;

import com.metroporto.JourneyPlanner;
import com.metroporto.JourneyRoute;
import com.metroporto.MetroSystem;
import com.metroporto.dao.linedao.LineDaoInterface;
import com.metroporto.dao.linedao.MySqlLineDao;
import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.dao.stationdao.StationDaoInterface;
import com.metroporto.enums.EnumLabelConverter;
import com.metroporto.enums.TimeTableType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Schedule;
import com.metroporto.metro.Station;
import gui.Controller;
import gui.StationConverter;
import gui.TimeConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Line;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class JourneyRouteController extends Controller
{
    private StationDaoInterface stationDao;

    private LineDaoInterface lineDao;

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
    private Label connectionLabel;

    @FXML
    private ComboBox<Station> endStations;

    @FXML
    private ComboBox<String> timetableType;

    @FXML
    private ComboBox<Integer> hours;

    @FXML
    private ComboBox<Integer> minutes;

    @FXML
    private ComboBox<String> savedRoutes;

    @FXML
    private CheckBox saveRouteOption;

    private Station selectedStartStation;

    private Station selectedEndStation;

    private TimeTableType selectedTimetableType;

    private int selectedHour = LocalTime.now().getHour();

    private int selectedMinute = LocalTime.now().getMinute();

    private List<Station> stations;

    private List<com.metroporto.metro.Line> lines;

    private MetroSystem metroSystem;

    private JourneyPlanner journeyPlanner;

    private JourneyRoute startStationLine;

    private JourneyRoute endStationLine;


    public JourneyRouteController()
    {
        stationDao = new MySqlStationDao();
        lineDao = new MySqlLineDao();

        try
        {
            stations = stationDao.findAll();
            lines = lineDao.findAll();
            metroSystem = new MetroSystem(lines);

            selectedStartStation = stationDao.findStationByStationId("APO");
            selectedEndStation = stationDao.findStationByStationId("CDR");
            selectedTimetableType = TimeTableType.MONDAY_TO_FRIDAY;

            journeyPlanner = new JourneyPlanner(selectedStartStation, selectedEndStation,
                    LocalTime.of(selectedHour, selectedMinute), selectedTimetableType);

            journeyPlanner.setMetroSystem(metroSystem);
            journeyPlanner.start();

            startStationLine = journeyPlanner.getJourneyRoutes().get(0);
            endStationLine = journeyPlanner.getJourneyRoutes().get(1);


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

        connectionLabel.setText("Switch line in: " +
                journeyPlanner.getJourneyRoutes().get(1).getSchedules().get(0).getStation().getStationName());

        drawStationNodes(startStationLine, colours.get(startStationLine.getLine().getLineId()), stationsPane,
                stationsLine, 10, 6, 27, 0, 0);

        drawStationNodes(endStationLine, colours.get(endStationLine.getLine().getLineId()),
                stationsPane2, stationsLine2, 10, 6, 27, 0, 0);

        savedRoutes.getItems().addAll("PLU-JDD | Monday-Friday | 13:30",
                "AER-MTS | Saturday | 09:00");

        savedRoutes.setCellFactory(listView -> new ListCell<>()
        {
            private final Button button = new Button();
            private final Label label = new Label();
            private final HBox hbox = new HBox(label, button);

            @Override
            public void updateItem(String item, boolean empty)
            {
                super.updateItem(item, empty);
                if (empty)
                {
                    setText(null);
                    setGraphic(null);
                } else
                {
                    hbox.setPadding(new Insets(0, 10, 0, 10));
                    label.setText(item);
                    button.setOnAction(event ->
                    {
                        System.out.println("Delete route");
                    });
                    button.getStyleClass().add("form-button");
                    ImageView trash = new ImageView();
                    trash.setFitHeight(15);
                    trash.setFitWidth(15);
                    Image trashImage = new Image(Objects.requireNonNull(getClass()
                            .getResourceAsStream("/img/trash-can.png")));
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

        savedRoutes.setButtonCell(new ListCell<>()
        {
            @Override
            public void updateItem(String item, boolean empty)
            {
                super.updateItem(item, empty);
                if (empty)
                {
                    setText("View saved routes");
                    setGraphic(null);
                } else
                {
                    setText(item);
                }
            }
        });
    }

    private void initialiseStationsComboBox(ComboBox<Station> comboBox, Station selectedStation)
    {
        comboBox.getItems().addAll(stations);
        comboBox.setConverter(new StationConverter());

        for (int i = 0; i < stations.size(); i++)
            if (stations.get(i).getStationId().equals(selectedStation.getStationId()))
                comboBox.getSelectionModel().select(i);
    }

    private void initialiseTimetableTypeComboBox(ComboBox<String> comboBox, String selectedType)
    {
        comboBox.getItems().add(TimeTableType.MONDAY_TO_FRIDAY.getLabel());
        comboBox.getItems().add(TimeTableType.SATURDAY.getLabel());
        comboBox.getItems().add(TimeTableType.SUNDAY.getLabel());

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

    public void searchRoute(ActionEvent event)
    {
        selectedStartStation = startStations.getSelectionModel().getSelectedItem();
        selectedEndStation = endStations.getSelectionModel().getSelectedItem();
        EnumLabelConverter labelConverter = new EnumLabelConverter();
        selectedTimetableType = labelConverter.fromLabel(timetableType.getSelectionModel().getSelectedItem(),
                TimeTableType.class);
        selectedHour = hours.getSelectionModel().getSelectedItem();
        selectedMinute = minutes.getSelectionModel().getSelectedItem();

        journeyPlanner = new JourneyPlanner(selectedStartStation, selectedEndStation,
                LocalTime.of(selectedHour, selectedMinute), selectedTimetableType);

        journeyPlanner.setMetroSystem(metroSystem);
        journeyPlanner.start();

        startStationLine = journeyPlanner.getJourneyRoutes().get(0);

        drawStationNodes(startStationLine, colours.get(startStationLine.getLine().getLineId()), stationsPane,
                stationsLine, 10, 6, 27, 0, 0);

        if (journeyPlanner.getJourneyRoutes().size() > 1)
        {
            connectionLabel.setText("Switch line in: " +
                    journeyPlanner.getJourneyRoutes().get(1).getSchedules().get(0).getStation().getStationName());

            endStationLine = journeyPlanner.getJourneyRoutes().get(1);
            stationsLine2.setVisible(true);
            drawStationNodes(endStationLine, colours.get(endStationLine.getLine().getLineId()),
                    stationsPane2, stationsLine2, 10, 6, 27, 0, 0);
        }
        else
        {
            connectionLabel.setText("");

            stationsPane2.getChildren().removeIf(node -> !(node instanceof Line));
            stationsLine2.setVisible(false);

        }

        if (saveRouteOption.isSelected())
        {
            System.out.println("Route saved");
        }

    }

    public void viewSavedRoute(ActionEvent event)
    {
    }

    public void clearSavedRoute(ActionEvent event)
    {
        savedRoutes.getSelectionModel().clearSelection();
        try
        {
            selectedStartStation = stationDao.findStationByStationId("PLU");
            selectedEndStation = stationDao.findStationByStationId("PLU");
        } catch (DaoException de)
        {
            de.printStackTrace();
        }
        selectedTimetableType = TimeTableType.MONDAY_TO_FRIDAY;
        selectedHour = LocalTime.now().getHour();
        selectedMinute = LocalTime.now().getMinute();
    }
}