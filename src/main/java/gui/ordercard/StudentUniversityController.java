package gui.ordercard;

import com.metroporto.dao.universitydao.MySqlUniversityDao;
import com.metroporto.dao.universitydao.UniversityDaoInterface;
import com.metroporto.enums.Folder;
import com.metroporto.exceptions.DaoException;
import com.metroporto.metro.University;
import com.metroporto.users.Student;
import gui.Controller;
import com.metroporto.enums.Page;
import gui.UniversityConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class StudentUniversityController extends Controller
{
    private UniversityDaoInterface universityDao;

    @FXML
    private Label universityLabel;

    @FXML
    private ComboBox<University> universityComboBox;

    @FXML
    private ToggleGroup cardToggleGroup;

    @FXML
    private RadioButton yesCardRadioButton;

    @FXML
    private RadioButton noCardRadioButton;

    List<University> universities;

    public StudentUniversityController()
    {
        user = app.getUser();

        universityDao = new MySqlUniversityDao();

        try
        {
            universities = universityDao.findAll();
        } catch (DaoException de)
        {
            de.printStackTrace();
        }
    }

    public void initialize()
    {
        initialiseLogo();
        initialiseMetroImage("metro_2");

        universityComboBox.setPromptText("Choose university...");
        universityComboBox.getItems().addAll(universities);
        universityComboBox.setConverter(new UniversityConverter());

        cardToggleGroup = new ToggleGroup();
        yesCardRadioButton.setToggleGroup(cardToggleGroup);
        noCardRadioButton.setToggleGroup(cardToggleGroup);
    }

    public void setScene(Scene scene)
    {
        scene.heightProperty().addListener((observable, oldValue, newValue) ->
                metro1.fitHeightProperty().setValue(newValue)
        );
    }

    @FXML
    private void submitForm(ActionEvent event) throws IOException
    {
        String asterisk = "*";
        String errorColour = "#de2a1d";

        Text redAsterisk = new Text(asterisk);
        redAsterisk.setFill(Color.web(errorColour));

        if (universityComboBox.getSelectionModel().getSelectedItem() != null)
        {
            University university = universityComboBox.getValue();

            universityLabel.setGraphic(null);
            errorText.setText("");

            try
            {
                ((Student) user).setUniversity(university);
                universityDao.insertUniversityForStudent(user);
            } catch (DaoException de)
            {
                de.printStackTrace();
            }

            if (cardToggleGroup.getSelectedToggle() == yesCardRadioButton)
            {
                redirectToPage(event, Folder.ORDER_CARD, Page.CARD_ZONE);
            } else
            {
                redirectToPage(event, Folder.HOME, Page.HOME);
            }
        } else
        {
            errorText.setText(asterisk + " Please choose an option");
            universityLabel.setGraphic(redAsterisk);
            universityLabel.setContentDisplay(ContentDisplay.RIGHT);
        }
    }
}
