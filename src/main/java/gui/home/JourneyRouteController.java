package gui.home;

import com.metroporto.dao.stationdao.MySqlStationDao;
import com.metroporto.enums.TimeTableType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Station;
import gui.Controller;
import gui.TimeConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private MySqlStationDao stationDao;

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
    private ComboBox<String> savedRoutes;

    @FXML
    private CheckBox saveRouteOption;

    private String selectedStartStation = "PLU";

    private String selectedEndStation = "MAT";

    private String selectedTimetableType = TimeTableType.MONDAY_TO_FRIDAY.getLabel();

    private int selectedHour = LocalTime.now().getHour();

    private int selectedMinute = LocalTime.now().getMinute();

    private List<Station> stations;

    private com.metroporto.metro.Line startStationLine;

    private com.metroporto.metro.Line endStationLine;


    public JourneyRouteController()
    {
        stationDao = new MySqlStationDao();

        try
        {
            stations = stationDao.findAll();
            //startStationLine.findRoute()

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

        initialiseStationsComboBox(startStations, selectedStartStation);
        initialiseStationsComboBox(endStations, selectedEndStation);

        initialiseTimetableTypeComboBox(timetableType, selectedTimetableType);

        initialiseTimeComboBox(hours, 0, 24, selectedHour);
        initialiseTimeComboBox(minutes, 0, 59, selectedMinute);

        drawStationNodes(startStationLine.getStations(), colours.get(startStationLine.getLineId()), stationsPane,
                stationsLine, 10, 6, 27, 0, 0);

        drawStationNodes(endStationLine.getStations(), colours.get(endStationLine.getLineId()),
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

    private void initialiseStationsComboBox(ComboBox<Station> comboBox, String selectedStationId)
    {
        comboBox.getItems().addAll(stations);

        for (int i = 0; i < stations.size(); i++)
            if (stations.get(i).getStationId().equals(selectedStationId))
                comboBox.getSelectionModel().select(i);
    }

    private void initialiseTimetableTypeComboBox(ComboBox<String> comboBox, String selectedType)
    {
        comboBox.getItems().add(TimeTableType.MONDAY_TO_FRIDAY.getLabel());
        comboBox.getItems().add(TimeTableType.SATURDAY.getLabel());
        comboBox.getItems().add(TimeTableType.SUNDAY.getLabel());

        comboBox.getSelectionModel().select(selectedType);
    }

    private void initialiseTimeComboBox(ComboBox<Integer> comboBox, int start, int end, int selectedItem)
    {
        for (int i = start; i <= end; i++)
        {
            comboBox.getItems().add(i);
        }

        comboBox.setConverter(new TimeConverter());
        comboBox.getSelectionModel().select(selectedItem);
    }

    public void searchRoute(ActionEvent event)
    {
        selectedStartStation = startStations.getSelectionModel().getSelectedItem().getStationId();
        selectedEndStation = endStations.getSelectionModel().getSelectedItem().getStationId();
        selectedTimetableType = timetableType.getSelectionModel().getSelectedItem();
        selectedHour = hours.getSelectionModel().getSelectedItem();
        selectedMinute = minutes.getSelectionModel().getSelectedItem();

        System.out.println(selectedStartStation + "-" + selectedEndStation+ " | "
                + selectedTimetableType + " | " + selectedHour + ":" + selectedMinute);

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
        selectedStartStation = "PLU";
        selectedEndStation = "MAT";
        selectedTimetableType = TimeTableType.MONDAY_TO_FRIDAY.getLabel();
        selectedHour = LocalTime.now().getHour();
        selectedMinute = LocalTime.now().getMinute();
    }
}

