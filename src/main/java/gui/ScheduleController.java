package gui;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScheduleController
{
    private final ControllersUtil util = new ControllersUtil();

    @FXML
    private ImageView logo;

    @FXML
    private ImageView profile;

    @FXML
    private ImageView card;

    @FXML
    private HBox linesBox;

    @FXML
    private Pane stationsPane;

    @FXML
    private Line stationsLine;

    @FXML
    private List<String> linesId = List.of("A", "B", "C", "D", "E", "F");

    private ImageView selectedImageView;

    private List<String> currentLineStationsId;

    private List<String> currentLineStationsName;

    private final Paint[] aColours = {Color.web("#0086de"), Color.web("#63beea")};

    private final Paint[] bColours = {Color.web("#fb0000"), Color.web("#fc8788")};

    private final Paint[] cColours = {Color.web("#59ad00"), Color.web("#a9d184")};

    private final Paint[] dColours = {Color.web("#ffa200"), Color.web("#ffcc6b")};

    private final Paint[] eColours = {Color.web("#665a99"), Color.web("#ada7c8")};

    private final Paint[] fColours = {Color.web("#ff5c00"), Color.web("#ffab7d")};

    private Paint[] selectedColours = aColours;

    private final double selectedOpacity = 1.0;

    private final double unselectedOpacity = 0.5;

    private final double lineStartX = 50;
    private double lineEndX;

    public void initialize()
    {
        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        logo.setImage(logoImage);

        Image profileImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/profile.png")));
        profile.setImage(profileImage);

        Image cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/card.png")));
        card.setImage(cardImage);

        for (String lineId : linesId)
        {
            ImageView lineImageView = new ImageView();
            lineImageView.setFitWidth(70);
            lineImageView.setFitHeight(70);
            Image lineImage = new Image(
                    Objects.requireNonNull(
                            getClass().getResourceAsStream("/img/" + lineId + "_line.png")));
            lineImageView.setImage(lineImage);
            lineImageView.setOpacity(unselectedOpacity);

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

                switch (lineId)
                {
                    case "A":
                        currentLineStationsId = List.of("HSJ", "IPO", "PLU",
                                "SGS", "CBT", "MRQ", "FGM", "TDD", "ALD",
                                "SBT", "JDM", "GLT", "CMG", "JDD",
                                "DJII", "STO", "HSJ", "IPO", "PLU",
                                "SGS", "CBT", "MRQ", "FGM", "TDD", "ALD",
                                "SBT", "JDM", "GLT", "CMG", "JDD",
                                "DJII", "STO", "LAL", "AKA");
                        selectedColours = aColours;
                        drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours);
                        break;
                    case "B":
                        currentLineStationsId = List.of("B", "BB", "BBB", "BBBB", "BBBBB");
                        selectedColours = bColours;
                        drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours);
                        break;
                    case "C":
                        currentLineStationsId = List.of("C", "CC", "CCC", "CCCC", "CCCCC");
                        selectedColours = cColours;
                        drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours);
                        break;
                    case "D":
                        currentLineStationsId = List.of("D", "DD", "DDD", "DDDD", "DDDDD");
                        selectedColours = dColours;
                        drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours);
                        break;
                    case "E":
                        currentLineStationsId = List.of("E", "EE", "EEE", "EEEE", "EEEEE");
                        selectedColours = eColours;
                        drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours);
                        break;
                    case "F":
                        currentLineStationsId = List.of("F", "FF", "FFF", "FFFF", "FFFFF");
                        selectedColours = fColours;
                        drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours);
                        break;
                    default:
                        break;
                }
            });

            linesBox.getChildren().add(lineImageView);
        }

        // Set the first image as the default selected image
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

        stationsPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            stationsLine.setStartX(lineStartX);
            stationsLine.setEndX(lineEndX);
        });

        lineEndX = stationsPane.getPrefWidth() - 50;

        drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours);
    }

    public void drawStationNodes(List<String> currentLineStationsId, List<String> currentLineStationsName,
                                 Paint[] colours)
    {
        double lineWidth = lineEndX - lineStartX;
        int numStations = currentLineStationsId.size();
        double increment = lineWidth / (numStations - 1);

        double circleX = 50;
        double textX = 32;

        // Check if stationsPane already has a Group child and remove if yes
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
            if (i == 0)
            {
                drawStationNode(group, circleX, 10, currentLineStationsId.get(i),
                        currentLineStationsName.get(i), textX, colours[0]);
            }
            else if (i == numStations - 1)
            {
                drawStationNode(group, circleX += increment, 10, currentLineStationsId.get(i),
                        currentLineStationsName.get(i), textX += increment, colours[0]);
            }
            else
            {
                drawStationNode(group, circleX += increment, 6, currentLineStationsId.get(i),
                        currentLineStationsName.get(i), textX += increment, colours[1]);
            }
        }
    }

    public void drawStationNode(Group group, double circleX, double radius, String textValue,
                                String tooltipText, double textX, Paint colour)
    {
        Circle circle = new Circle();
        circle.setCenterX(circleX);
        circle.setCenterY(80);
        circle.setRadius(radius);
        circle.setFill(colour);

        Label text = new Label();
        text.setLayoutX(textX);
        text.setLayoutY(30);
        text.setTextAlignment(TextAlignment.LEFT);
        text.setRotate(-90);
        text.setText(textValue);
        text.setFont(Font.font("HarmoniaSansProCyr-Regular", 14));

        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(text, tooltip);

        if (group != null)
        {
            group.getChildren().add(circle);
            group.getChildren().add(text);
        }
    }

    public void redirectToHome(MouseEvent event) throws IOException
    {
        util.redirectToHome(event);
    }

    public void redirectToJourneyRoute(MouseEvent event) throws IOException
    {
        util.redirectToJourneyRoute(event);
    }

    public void redirectToStation(MouseEvent event) throws IOException
    {
        util.redirectToStation(event);
    }

    public void redirectToProfile(MouseEvent event) throws IOException
    {
        util.redirectToProfile(event);
    }

    public void redirectToCard(MouseEvent event) throws IOException
    {
        util.redirectToCard(event);
    }

    public void setScene(Scene scene)
    {
        stationsPane.prefWidthProperty().bind(scene.widthProperty());
        scene.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            lineEndX = stationsPane.getPrefWidth() - 50;
            drawStationNodes(currentLineStationsId, currentLineStationsName, selectedColours);
        });
    }
}
