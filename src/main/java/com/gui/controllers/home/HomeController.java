package com.gui.controllers.home;

import com.metroporto.enums.EnumLabelConverter;
import com.metroporto.enums.Folder;
import com.metroporto.enums.HomeMenuOption;
import com.gui.controllers.Controller;
import com.metroporto.enums.Page;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.List;

public class HomeController extends Controller
{
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox<String> optionsComboBox;

    private EnumLabelConverter enumLabelConverter;

    public void initialize()
    {
        enumLabelConverter = new EnumLabelConverter();

        initialiseLogo();
        initialiseCardIcon();
        initialiseProfileIcon();

        Image image = new Image("/img/metro/metro-5.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(
                        BackgroundSize.AUTO,
                        BackgroundSize.AUTO,
                        false,
                        false,
                        true,
                        true
                )
        );
        Background background = new Background(backgroundImage);
        anchorPane.setBackground(background);

        List<String> options = List.of(
                HomeMenuOption.SCHEDULE_OPTION.getLabel(),
                HomeMenuOption.JOURNEY_ROUTE_OPTION.getLabel(),
                HomeMenuOption.STATION_OPTION.getLabel(),
                HomeMenuOption.PROFILE_OPTION.getLabel(),
                HomeMenuOption.CARD_OPTION.getLabel());

        optionsComboBox.getItems().addAll(options);
        optionsComboBox.getSelectionModel().selectFirst();
    }

    public void submitForm(ActionEvent event) throws IOException
    {
        HomeMenuOption selectedItem = enumLabelConverter.fromLabel(
                optionsComboBox.getSelectionModel().getSelectedItem(), HomeMenuOption.class);

        switch(selectedItem)
        {
            case SCHEDULE_OPTION:
                redirectToPage(event, Folder.HOME, Page.SCHEDULE);
                break;
            case JOURNEY_ROUTE_OPTION:
                redirectToPage(event, Folder.HOME, Page.JOURNEY_PLANNER);
                break;
            case STATION_OPTION:
                redirectToPage(event, Folder.HOME, Page.STATION);
                break;
            case PROFILE_OPTION:
                redirectToPage(event, Folder.HOME, Page.PROFILE);
                break;
            case CARD_OPTION:
                redirectToPage(event, Folder.HOME, Page.CARD);
                break;
            default:
                break;
        }
    }
}
