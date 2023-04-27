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
import java.util.stream.Collectors;

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
    private ComboBox<String> startStations;

    @FXML
    private ComboBox<String> endStations;

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

    private String selectedStartStationName = "Pólo Universitário";

    private String selectedEndStationName = "Matosinhos Sul";

    private String selectedTimetableType = TimeTableType.MONDAY_TO_FRIDAY.getLabel();

    private int selectedHour = LocalTime.now().getHour();

    private int selectedMinute = LocalTime.now().getMinute();

    private List<Station> stations;

    private List<String> currentLineStationsId = List.of("HSJ", "IPO", "PLU",
            "SGS", "CBT", "MRQ", "FGM", "TDD", "ALD",
            "SBT", "JDM", "GLT", "CMG", "JDD",
            "DJII", "STO", "HSJ", "IPO", "PLU",
            "SGS", "CBT", "MRQ", "FGM", "TDD", "ALD",
            "SBT", "JDM", "GLT", "CMG", "JDD",
            "DJII", "STO", "LAL", "AKA");

    private List<String> currentLineStationsName = List.of("Hospital Sao Joao", "IPO", "PoLo Universitario",
            "SalGueiroS", "ComBanTentes", "MaRQues", "Faria GuiMares", "TrinDaDe", "ALiaDos",
            "Sao BenTo", "Jardim Do Morro", "GeneraL Torres", "CaMara Gaia", "Joao De Deus",
            "D. Joao II", "SanTo Ovidio", "Hospital Sao Joao", "IPO", "PoLo Universitario",
            "SalGueiroS", "ComBanTentes", "MaRQues", "Faria GuiMares", "TrinDaDe", "ALiaDos",
            "Sao BenTo", "Jardim Do Morro", "GeneraL Torres", "CaMara Gaia", "Joao De Deus",
            "D. Joao II", "SanTo Ovidio", "Lalalal", "Akakaka", "Lalalal", "Akakaka");

    private List<String> currentLineStationsId2 = List.of("HSJ", "IPO", "PLU",
            "SGS", "CBT", "MRQ", "FGM", "TDD", "ALD",
            "SBT", "JDM");

    private List<String> currentLineStationsName2 = List.of("Hospital Sao Joao", "IPO", "PoLo Universitario",
            "SalGueiroS", "ComBanTentes", "MaRQues", "Faria GuiMares", "TrinDaDe", "ALiaDos",
            "Sao BenTo", "Jardim Do Morro");


    public JourneyRouteController()
    {
        stationDao = new MySqlStationDao();

        try
        {
            stations = stationDao.findAll();

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

        initialiseStationsComboBox(startStations, selectedStartStationName);
        initialiseStationsComboBox(endStations, selectedEndStationName);

        initialiseTimetableTypeComboBox(timetableType, selectedTimetableType);

        initialiseTimeComboBox(hours, 0, 24, selectedHour);
        initialiseTimeComboBox(minutes, 0, 59, selectedMinute);

        drawStationNodes(currentLineStationsId, currentLineStationsName, aColours,
                stationsPane, stationsLine, 10, 6,
                27, 0, 0);

        drawStationNodes(currentLineStationsId2, currentLineStationsName2, bColours,
                stationsPane2, stationsLine2, 10, 6,
                27, 0, 0);

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

    private void initialiseStationsComboBox(ComboBox<String> comboBox, String selectedName)
    {
        comboBox.getItems().addAll(stations.stream()
                .map(Station::getStationName)
                .collect(Collectors.toList()));

        comboBox.getSelectionModel().select(selectedName);
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
        selectedStartStationName = startStations.getSelectionModel().getSelectedItem();
        selectedEndStationName = endStations.getSelectionModel().getSelectedItem();
        selectedTimetableType = timetableType.getSelectionModel().getSelectedItem();
        selectedHour = hours.getSelectionModel().getSelectedItem();
        selectedMinute = minutes.getSelectionModel().getSelectedItem();

        System.out.println(selectedStartStationName + "-" + selectedEndStationName + " | "
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
        selectedStartStationName = "Pólo Universitário";
        selectedEndStationName = "Matosinhos Sul";
        selectedTimetableType = TimeTableType.MONDAY_TO_FRIDAY.getLabel();
        selectedHour = LocalTime.now().getHour();
        selectedMinute = LocalTime.now().getMinute();
    }
}

