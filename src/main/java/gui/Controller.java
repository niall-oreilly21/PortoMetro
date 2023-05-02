package gui;

import com.metroporto.ConnectionSchedule;
import com.metroporto.JourneyRoute;
import com.metroporto.enums.Folder;
import com.metroporto.enums.Page;
import com.metroporto.metro.Schedule;
import com.metroporto.metro.Station;
import com.metroporto.users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Controller
{
    protected User user;

    @FXML
    protected ImageView logo;

    @FXML
    protected ImageView metro1;

    @FXML
    protected ImageView profile;

    @FXML
    protected ImageView card;

    @FXML
    protected Label errorText;

    protected final Map<String, Paint[]> colours = Map.of(
            "A", new Paint[]{Color.web("#0086de"), Color.web("#63beea")},
            "B", new Paint[]{Color.web("#fb0000"), Color.web("#fc8788")},
            "C", new Paint[]{Color.web("#59ad00"), Color.web("#a9d184")},
            "D", new Paint[]{Color.web("#ffa200"), Color.web("#ffcc6b")},
            "E", new Paint[]{Color.web("#665a99"), Color.web("#ada7c8")},
            "F", new Paint[]{Color.web("#ff5c00"), Color.web("#ffab7d")}
    );

    protected static App app;

    public abstract void initialize();

    protected void initialiseLogo()
    {
        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        logo.setImage(logoImage);
    }

    protected void initialiseMetroImage(String name)
    {
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/" + name + ".jpg")));
        metro1.setImage(image1);
    }

    protected void initialiseProfileIcon()
    {
        Image profileImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/profile.png")));
        profile.setImage(profileImage);
    }

    protected void initialiseCardIcon()
    {
        Image cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/card.png")));
        card.setImage(cardImage);
    }

    public void redirectToPage(ActionEvent event, Folder folder, Page page) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(Controller.class.getClassLoader()
                        .getResource("com/gui/" + folder.getLabel() + "/" + page.getLabel() + ".fxml")));

        Parent root = loader.load();
        Controller controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene previousScene = stage.getScene();
        boolean isFullscreen = stage.isFullScreen(); // save the previous fullscreen state
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, previousScene.getWidth(), previousScene.getHeight());
        stage.setScene(scene);

        stage.show();

        controller.setScene(scene);
        controller.setApp(App.getApplication());

        if (isFullscreen)
        {
            stage.setFullScreen(true); // set back to fullscreen if it was fullscreen before
        }
    }

    public void redirectToPage(MouseEvent event, Folder folder, Page page) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(Controller.class.getClassLoader()
                        .getResource("com/gui/" + folder.getLabel() + "/" + page.getLabel() + ".fxml")));

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (stage != null)
        {
            Scene previousScene = stage.getScene();
            Scene scene = new Scene(root, previousScene.getWidth(), previousScene.getHeight());
            boolean isFullscreen = stage.isFullScreen();
            Controller controller = loader.getController();
            controller.setScene(scene);
            controller.setApp(App.getApplication());

            stage.setTitle("Porto Metro");
            stage.setScene(scene);

            if (isFullscreen)
                stage.setFullScreen(true);

            stage.show();
        }
    }

    protected void setApp(App application)
    {
        app = application;
    }

    protected void setScene(Scene scene)
    {
    }

    public String capitalise(String text)
    {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    public void redirectToHome(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.HOME);
    }

    public void redirectToSchedule(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.SCHEDULE);
    }

    public void redirectToJourneyRoute(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.JOURNEY_ROUTE);
    }

    public void redirectToStation(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.STATION);
    }

    public void redirectToProfile(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.PROFILE);
    }

    public void redirectToCard(MouseEvent event) throws IOException
    {
        redirectToPage(event, Folder.HOME, Page.CARD);
    }

    public void drawStationNodes(List<Station> currentLineStations,
                                 Paint[] colours, Pane stationsPane, Line stationsLine, int bigRadius, int smallRadius,
                                 double spacing, double lineStartX, double lineEndX)
    {
        int numStations = currentLineStations.size();
        double lineWidth = lineEndX - lineStartX;
        if (lineWidth == 0)
        {
            lineWidth = bigRadius + (numStations - 1) * (smallRadius + spacing);
            lineStartX = (stationsPane.getPrefWidth() - lineWidth) / 2.0;
            lineEndX = lineStartX + lineWidth;
            stationsLine.setStartX(lineStartX);
            stationsLine.setEndX(lineEndX);
        }

        double increment = lineWidth / (numStations - 1);
        double circleX = lineStartX + bigRadius / 2.0;
        double textX = lineStartX - 8;

        if (stationsPane.getChildren().size() > 1 && stationsPane.getChildren().get(1) instanceof Group)
        {
            List<Node> nodesToRemove = new ArrayList<>();
            for (Node node : stationsPane.getChildren())
            {
                if (node instanceof Group)
                {
                    nodesToRemove.add(node);
                }
            }
            stationsPane.getChildren().removeAll(nodesToRemove);
        }

        Group group = new Group();
        stationsPane.getChildren().add(group);

        for (int i = 0; i < numStations; i++)
        {
            int radius = (i == 0 || i == numStations - 1) ? bigRadius : smallRadius;
            int colourIndex = (i == 0 || i == numStations - 1) ? 0 : 1;
            boolean isBold = (i == 0 || i == numStations - 1);
            circleX = lineWidth == 0 && i != 0 ? circleX + smallRadius + spacing
                    : lineWidth == 0 || i == 0 ? circleX
                    : circleX + increment;
            textX = lineWidth == 0 && i != 0 ? textX + smallRadius + spacing
                    : i == 0 ? textX
                    : textX + increment;

            drawStationNode(group, circleX, radius, currentLineStations.get(i).getStationId(),
                    currentLineStations.get(i).getStationName(), textX, colours[colourIndex], isBold, "");
        }
    }

    public void drawStationNodes(JourneyRoute currentJourneyRoute,
                                 Paint[] colours, Pane stationsPane, Line stationsLine, int bigRadius, int smallRadius,
                                 double spacing, double lineStartX, double lineEndX)
    {
        int numSchedules = currentJourneyRoute.getSchedules().size();
        double lineWidth = lineEndX - lineStartX;
        if (lineWidth == 0)
        {
            lineWidth = bigRadius + (numSchedules - 1) * (smallRadius + spacing);
            lineStartX = (stationsPane.getPrefWidth() - lineWidth) / 2.0;
            lineEndX = lineStartX + lineWidth;
            stationsLine.setStartX(lineStartX);
            stationsLine.setEndX(lineEndX);
        }

        double increment = lineWidth / (numSchedules - 1);
        double circleX = lineStartX + bigRadius / 2.0;
        double textX = lineStartX - 10;

        if (stationsPane.getChildren().size() > 1 && stationsPane.getChildren().get(1) instanceof Group)
        {
            List<Node> nodesToRemove = new ArrayList<>();
            for (Node node : stationsPane.getChildren())
            {
                if (node instanceof Group)
                {
                    nodesToRemove.add(node);
                }
            }
            stationsPane.getChildren().removeAll(nodesToRemove);
        }

        Group group = new Group();
        stationsPane.getChildren().add(group);

        for (int i = 0; i < numSchedules; i++)
        {
            int radius = (i == 0 || i == numSchedules - 1) ? bigRadius : smallRadius;
            int colourIndex = (i == 0 || i == numSchedules - 1) ? 0 : 1;
            boolean isBold = (i == 0 || i == numSchedules - 1);
            circleX = lineWidth == 0 && i != 0 ? circleX + smallRadius + spacing
                    : lineWidth == 0 || i == 0 ? circleX
                    : circleX + increment;
            textX = lineWidth == 0 && i != 0 ? textX + smallRadius + spacing
                    : i == 0 ? textX
                    : textX + increment;
            String timeTextValue = currentJourneyRoute.getSchedules().get(i).getDepartureTime().toString();

            Schedule schedule = currentJourneyRoute.getSchedules().get(i);
            Station station = schedule.getStation();

            if (i == 0)
            {
                if (schedule instanceof ConnectionSchedule)
                {
                    timeTextValue = schedule.getDepartureTime().toString();
                }

                drawStationNode(group, circleX, radius, schedule.getStation().getStationId(),
                        station.getStationName() + " | " + station.getZone().getZoneName(),
                        textX, colours[colourIndex], isBold, timeTextValue);
            } else if (i == numSchedules - 1)
            {
                if (currentJourneyRoute.getSchedules().get(i) instanceof ConnectionSchedule)
                {
                    timeTextValue = ((ConnectionSchedule) schedule).getArrivalTime().toString();
                }

                drawStationNode(group, circleX, radius, station.getStationId(),
                        station.getStationName() + " | " + station.getZone().getZoneName(),
                        textX, colours[colourIndex], isBold, timeTextValue);
            } else
            {
                drawStationNode(group, circleX, radius,
                        station.getStationId(),
                        station.getStationName() + " | " + timeTextValue + " | " + station.getZone().getZoneName(),
                        textX, colours[colourIndex], isBold, "");
            }
        }
    }

    public void drawStationNode(Group group, double circleX, double radius, String textValue,
                                String tooltipText, double textX, Paint colour, boolean bold, String timeTextValue)
    {
        Circle circle = new Circle();
        circle.setCenterX(circleX);
        circle.setCenterY(80);
        circle.setRadius(radius);
        circle.setFill(colour);

        Label text = new Label();
        if (bold)
            text.getStyleClass().add("bold");
        else
            text.getStyleClass().add("regular");

        text.setLayoutX(textX);
        text.setLayoutY(30);
        text.setTextAlignment(TextAlignment.LEFT);
        text.setRotate(-90);
        text.setText(textValue);

        Label timeText = new Label();
        timeText.getStyleClass().add("regular");
        timeText.setLayoutX(textX);
        timeText.setLayoutY(100);
        timeText.setTextAlignment(TextAlignment.LEFT);
        timeText.setText(timeTextValue);

        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(text, tooltip);

        if (group != null)
        {
            group.getChildren().add(circle);
            group.getChildren().add(text);
            group.getChildren().add(timeText);
        }
    }
}
