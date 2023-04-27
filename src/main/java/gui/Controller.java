package gui;

import com.metroporto.enums.Folder;
import com.metroporto.enums.Page;
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
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Controller
{
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

    protected final Paint[] aColours = {Color.web("#0086de"), Color.web("#63beea")};

    protected final Paint[] bColours = {Color.web("#fb0000"), Color.web("#fc8788")};

    protected final Paint[] cColours = {Color.web("#59ad00"), Color.web("#a9d184")};

    protected final Paint[] dColours = {Color.web("#ffa200"), Color.web("#ffcc6b")};

    protected final Paint[] eColours = {Color.web("#665a99"), Color.web("#ada7c8")};

    protected final Paint[] fColours = {Color.web("#ff5c00"), Color.web("#ffab7d")};

    protected App app;

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

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();

        setScene(scene);
        setApp(App.getApplication());
    }

    public void redirectToPage(MouseEvent event, Folder folder, Page page) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(Controller.class.getClassLoader()
                        .getResource("com/gui/" + folder.getLabel() + "/" + page.getLabel() + ".fxml")));

        Parent root = loader.load();

        Controller controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();

        controller.setScene(scene);
        controller.setApp(App.getApplication());
    }

    protected void setApp(App application) {
        this.app = application;
    }

    protected void setScene(Scene scene) {}

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

    public void drawStationNodes(List<String> currentLineStationsId, List<String> currentLineStationsName,
                                 Paint[] colours, Pane stationsPane, Line stationsLine, int bigRadius, int smallRadius,
                                 double spacing, double lineStartX, double lineEndX) {
        int numStations = currentLineStationsId.size();
        double lineWidth = lineEndX - lineStartX;
        if (lineWidth == 0) {
            lineWidth = bigRadius + (numStations - 1) * (smallRadius + spacing);
            lineStartX = (stationsPane.getPrefWidth() - lineWidth) / 2.0;
            lineEndX = lineStartX + lineWidth;
            stationsLine.setStartX(lineStartX);
            stationsLine.setEndX(lineEndX);
        }

        double increment = lineWidth / (numStations - 1);
        double circleX = lineStartX + bigRadius/2.0;
        double textX = lineStartX - 8;

        if (stationsPane.getChildren().size() > 1 && stationsPane.getChildren().get(1) instanceof Group) {
            List<Node> nodesToRemove = new ArrayList<>();
            for (Node node : stationsPane.getChildren()) {
                if (node instanceof Group) {
                    nodesToRemove.add(node);
                }
            }
            stationsPane.getChildren().removeAll(nodesToRemove);
        }

        Group group = new Group();
        stationsPane.getChildren().add(group);

        for (int i = 0; i < numStations; i++) {
            int radius = (i == 0 || i == numStations - 1) ? bigRadius : smallRadius;
            int colourIndex = (i == 0 || i == numStations - 1) ? 0 : 1;
            boolean isBold = (i == 0 || i == numStations - 1);
            circleX = lineWidth == 0 && i != 0 ? circleX + smallRadius + spacing
                    : lineWidth == 0 || i == 0 ? circleX
                    : circleX + increment;
            textX = lineWidth == 0 && i != 0 ? textX + smallRadius + spacing
                    : i == 0 ? textX
                    : textX + increment;

            drawStationNode(group, circleX, radius, currentLineStationsId.get(i),
                    currentLineStationsName.get(i), textX, colours[colourIndex], isBold);
        }
    }

    public void drawStationNode(Group group, double circleX, double radius, String textValue,
                                String tooltipText, double textX, Paint colour, boolean bold)
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

        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(text, tooltip);

        if (group != null)
        {
            group.getChildren().add(circle);
            group.getChildren().add(text);
        }
    }
}
