package gui.home;

import com.metroporto.dao.linedao.LineDaoInterface;
import com.metroporto.dao.linedao.MySqlLineDao;
import com.metroporto.enums.TimeTableType;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Route;
import gui.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

import java.util.List;
import java.util.Objects;

public class ScheduleController extends Controller
{
    LineDaoInterface lineDao;

    @FXML
    private HBox linesBox;

    @FXML
    private Pane stationsPane;

    @FXML
    private Line stationsLine;

    @FXML
    private ComboBox<String> daysComboBox;

    @FXML
    private Label endStationLabel;

    private List<com.metroporto.metro.Line> lines;

    private ImageView selectedImageView;

    private List<String> currentLineStationsId;

    private List<String> currentLineStationsName;

    private com.metroporto.metro.Line currentLine;

    private final double selectedOpacity = 1.0;

    private final double unselectedOpacity = 0.5;

    private final double lineStartX = 50;

    private double lineEndX;

    private Paint[] selectedColours = aColours;

    private Route currentRoute;

    public ScheduleController()
    {
        lineDao = new MySqlLineDao();

        try
        {
            lines = lineDao.findAll();
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

        for (com.metroporto.metro.Line line : lines)
        {
            ImageView lineImageView = new ImageView();
            lineImageView.setFitWidth(70);
            lineImageView.setFitHeight(70);
            Image lineImage = new Image(
                    Objects.requireNonNull(
                            getClass().getResourceAsStream("/img/lines/" + line.getLineId() + "_line.png")));
            lineImageView.setImage(lineImage);
            lineImageView.setOpacity(unselectedOpacity);

            Tooltip tooltip = new Tooltip(line.getLineName());
            Tooltip.install(lineImageView, tooltip);

            // Add mouse click event handler to each image view
            lineImageView.setOnMouseClicked(event ->
            {
                // Set the opacity of the clicked ImageView to 100%
                lineImageView.setOpacity(selectedOpacity);

                // If a previous image was selected, set its opacity to 50%
                if (selectedImageView != null && selectedImageView != lineImageView)
                {
                    selectedImageView.setOpacity(unselectedOpacity);
                }

                // Set the clicked ImageView as the selectedImageView
                selectedImageView = lineImageView;

                currentRoute = line.getRoutes().get(0);
                endStationLabel.setText(currentRoute.getEndStation().getStationName());

                switch (line.getLineId())
                {
                    case "A":
                        currentLineStationsId = List.of("HSJ", "IPO", "PLU",
                                "SGS", "CBT", "MRQ", "FGM", "TDD", "ALD",
                                "SBT", "JDM", "GLT", "CMG", "JDD",
                                "DJII", "STO", "HSJ", "IPO", "PLU",
                                "SGS", "CBT", "MRQ", "FGM", "TDD", "ALD",
                                "SBT", "JDM", "GLT", "CMG", "JDD",
                                "DJII", "STO", "LAL", "AKA");
                        setCurrentLine("A");
                        selectedColours = aColours;
                        drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours,
                                stationsPane, stationsLine, 10, 6, 0, lineStartX, lineEndX);
                        break;
                    case "B":
                        currentLineStationsId = List.of("B", "BB", "BBB", "BBBB", "BBBBB");
                        setCurrentLine("B");
                        selectedColours = bColours;
                        drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours,
                                stationsPane, stationsLine, 10, 6, 0, lineStartX, lineEndX);
                        break;
                    case "C":
                        currentLineStationsId = List.of("C", "CC", "CCC", "CCCC", "CCCCC");
                        setCurrentLine("C");
                        selectedColours = cColours;
                        drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours,
                                stationsPane, stationsLine, 10, 6, 0, lineStartX, lineEndX);
                        break;
                    case "D":
                        currentLineStationsId = List.of("D", "DD", "DDD", "DDDD", "DDDDD");
                        setCurrentLine("D");
                        selectedColours = dColours;
                        drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours,
                                stationsPane, stationsLine, 10, 6, 0, lineStartX, lineEndX);
                        break;
                    case "E":
                        currentLineStationsId = List.of("E", "EE", "EEE", "EEEE", "EEEEE");
                        setCurrentLine("E");
                        selectedColours = eColours;
                        drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours,
                                stationsPane, stationsLine, 10, 6, 0, lineStartX, lineEndX);
                        break;
                    case "F":
                        currentLineStationsId = List.of("F", "FF", "FFF", "FFFF", "FFFFF");
                        setCurrentLine("F");
                        selectedColours = fColours;
                        drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours,
                                stationsPane, stationsLine, 10, 6, 0, lineStartX, lineEndX);
                        break;
                    default:
                        break;
                }
            });

            linesBox.getChildren().add(lineImageView);
        }

        try
        {
            initialiseInitialLine();
        }
        catch (DaoException de)
        {
            System.out.println(de.getMessage());
        }

        stationsPane.widthProperty().addListener((obs, oldVal, newVal) ->
        {
            stationsLine.setStartX(lineStartX);
            stationsLine.setEndX(lineEndX);
        });

        lineEndX = stationsPane.getPrefWidth() - 50;

        drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours,
                stationsPane, stationsLine, 10, 6, 0, lineStartX, lineEndX);

        List<String> days = List.of(TimeTableType.MONDAY_TO_FRIDAY.getLabel(),
                TimeTableType.SATURDAY.getLabel(), TimeTableType.SUNDAY.getLabel());
        daysComboBox.getItems().addAll(days);
        daysComboBox.getSelectionModel().selectFirst();
    }

    public void initialiseInitialLine() throws DaoException
    {
        // Set the first image as the default selected image
        setCurrentLine("A");
        selectedImageView = (ImageView) linesBox.getChildren().get(0);
        selectedImageView.setOpacity(selectedOpacity);
        currentLineStationsId = List.of("HSJ", "IPO", "PLU",
                "SGS", "CBT", "MRQ", "FGM", "TDD", "ALD",
                "SBT", "JDM", "GLT", "CMG", "JDD",
                "DJII", "STO", "HSJ", "IPO", "PLU",
                "SGS", "CBT", "MRQ", "FGM", "TDD", "ALD",
                "SBT", "JDM", "GLT", "CMG", "JDD",
                "DJII", "STO", "LAL", "AKA", "AKA", "ALA");

        currentLineStationsName = List.of("Hospital Sao Joao", "IPO", "PoLo Universitario",
                "SalGueiroS", "ComBanTentes", "MaRQues", "Faria GuiMares", "TrinDaDe", "ALiaDos",
                "Sao BenTo", "Jardim Do Morro", "GeneraL Torres", "CaMara Gaia", "Joao De Deus",
                "D. Joao II", "SanTo Ovidio", "Hospital Sao Joao", "IPO", "PoLo Universitario",
                "SalGueiroS", "ComBanTentes", "MaRQues", "Faria GuiMares", "TrinDaDe", "ALiaDos",
                "Sao BenTo", "Jardim Do Morro", "GeneraL Torres", "CaMara Gaia", "Joao De Deus",
                "D. Joao II", "SanTo Ovidio", "Lalalal", "Akakaka", "Lalalal", "Akakaka");

        currentRoute = lineDao.findLineByLineId("A").getRoutes().get(0);
        endStationLabel.setText(currentRoute.getEndStation().getStationName());
    }

    private void setCurrentLine(String lineId)
    {
        try
        {
            currentLine = lineDao.findLineByLineId(lineId);
        } catch (DaoException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setScene(Scene scene)
    {
        stationsPane.prefWidthProperty().bind(scene.widthProperty());
        scene.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            lineEndX = stationsPane.getPrefWidth() - 50;
            drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours,
                    stationsPane, stationsLine, 10, 6, 0, lineStartX, lineEndX);
        });
    }

    public void switchRoutes(ActionEvent event)
    {
        if (currentLine != null)
        {
            if (currentRoute.getRouteId() == currentLine.getRoutes().get(0).getRouteId())
            {
                currentRoute = currentLine.getRoutes().get(1);
            } else
            {
                currentRoute = currentLine.getRoutes().get(0);
            }
            endStationLabel.setText(currentRoute.getEndStation().getStationName());
        }
    }
}
