package gui.ordercard;

import com.metroporto.dao.carddao.CardDaoInterface;
import com.metroporto.dao.carddao.MySqlCardDao;
import com.metroporto.dao.zonedao.MySqlZoneDao;
import com.metroporto.dao.zonedao.ZoneDaoInterface;
import com.metroporto.enums.Folder;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.Zone;
import com.metroporto.users.Student;
import gui.Controller;
import com.metroporto.enums.Page;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardZoneController extends Controller
{
    private ZoneDaoInterface zoneDao;
    private CardDaoInterface cardDao;

    @FXML
    private RadioButton allZonesRadioButton;

    @FXML
    private RadioButton threeZonesRadioButton;

    @FXML
    private ToggleGroup zoneToggleGroup;

    @FXML
    private VBox zonesOptionBox;

    @FXML
    private Label zoneSelectionLabel;

    private List<Zone> zones;

    private int numSelected = 0;

    public CardZoneController()
    {
        user = app.getUser();

        zoneDao = new MySqlZoneDao();
        cardDao = new MySqlCardDao();

        try
        {
            zones = zoneDao.findAll();
        } catch (DaoException de)
        {
            de.printStackTrace();
        }
    }

    public void initialize()
    {
        initialiseMetroImage("zone-map");

        zoneToggleGroup = new ToggleGroup();
        allZonesRadioButton.setToggleGroup(zoneToggleGroup);
        threeZonesRadioButton.setToggleGroup(zoneToggleGroup);

        zonesOptionBox.visibleProperty().bind(threeZonesRadioButton.selectedProperty());
        zonesOptionBox.managedProperty().bind(threeZonesRadioButton.selectedProperty());

        threeZonesRadioButton.setOnAction(event ->
        {
            if (threeZonesRadioButton.isSelected())
            {
                addCheckboxes();
            }
        });
    }

    public void addCheckboxes()
    {
        int count = 0;
        HBox currentHBox = new HBox(20);

        if (zonesOptionBox.getChildren().size() > 0)
        {
            zonesOptionBox.getChildren().clear();
        }

        zonesOptionBox.getChildren().add(currentHBox);
        for (Zone zone : zones)
        {
            CheckBox checkBox = new CheckBox(zone.getZoneName());
            checkBox.setOnAction(this::handleCheckBoxAction);
            currentHBox.getChildren().add(checkBox);
            HBox.setMargin(checkBox, new Insets(15, 0, 0, 0));
            count++;
            if (count == 3)
            {
                currentHBox = new HBox(20);
                zonesOptionBox.getChildren().add(currentHBox);
                count = 0;
            }
        }
    }

    @FXML
    private void handleCheckBoxAction(ActionEvent event)
    {
        CheckBox checkBox = (CheckBox) event.getSource();
        if (checkBox.isSelected())
        {
            numSelected++;
        } else
        {
            numSelected--;
        }
        updateCheckBoxes();
    }

    private void updateCheckBoxes()
    {
        if (numSelected >= 3)
        {
            disableUnselectedCheckBoxes();
        } else
        {
            enableAllCheckBoxes();
        }
    }

    private void disableUnselectedCheckBoxes()
    {
        for (Node node : zonesOptionBox.getChildren())
        {
            if (node instanceof HBox)
            {
                HBox hbox = (HBox) node;
                for (Node child : hbox.getChildren())
                {
                    if (child instanceof CheckBox)
                    {
                        CheckBox checkBox = (CheckBox) child;
                        if (!checkBox.isSelected())
                        {
                            checkBox.setDisable(true);
                        }
                    }
                }
            }
        }
    }

    private void enableAllCheckBoxes()
    {
        for (Node node : zonesOptionBox.getChildren())
        {
            if (node instanceof HBox)
            {
                for (Node checkBox : ((HBox) node).getChildren())
                {
                    if (checkBox instanceof CheckBox)
                    {
                        checkBox.setDisable(false);
                    }
                }
            }
        }
    }

    public List<CheckBox> getSelectedCheckboxes()
    {
        List<CheckBox> selected = new ArrayList<>();
        for (Node hBoxNode : zonesOptionBox.getChildren())
        {
            if (hBoxNode instanceof HBox)
            {
                HBox hBox = (HBox) hBoxNode;
                for (Node checkboxNode : hBox.getChildren())
                {
                    if (checkboxNode instanceof CheckBox)
                    {
                        CheckBox checkBox = (CheckBox) checkboxNode;
                        if (checkBox.isSelected())
                        {
                            selected.add(checkBox);
                        }
                    }
                }
            }
        }
        return selected;
    }

    public void setScene(Scene scene)
    {
        scene.heightProperty().addListener((observable, oldValue, newValue) ->
                metro1.fitHeightProperty().setValue(newValue)
        );
    }

    public void submitForm(ActionEvent event) throws IOException
    {
        String asterisk = "*";
        String errorColour = "#de2a1d";

        Text redAsterisk = new Text(asterisk);
        redAsterisk.setFill(Color.web(errorColour));

        List<CheckBox> selectedZones = getSelectedCheckboxes();

        if (threeZonesRadioButton.isSelected() && selectedZones.size() < 3)
        {
            errorText.setText(asterisk + " Choose 3 zones");
            zoneSelectionLabel.setGraphic(redAsterisk);
            zoneSelectionLabel.setContentDisplay(ContentDisplay.RIGHT);
        } else
        {
            zoneSelectionLabel.setGraphic(null);
            errorText.setText("");

            if (user instanceof Student)
            {
//                Card card = new StudentCard();

                // if 3 zones -> card.setZones(zonesList)

                // user.setCard(card)
                // cardDao.insertCardForUser(user)
            }

            for (CheckBox checkBox : selectedZones)
            {
                System.out.println(checkBox.getText());
            }

            // TODO: Add to database

            redirectToPage(event, Folder.ORDER_CARD, Page.CARD_INVOICE);
        }
    }
}
