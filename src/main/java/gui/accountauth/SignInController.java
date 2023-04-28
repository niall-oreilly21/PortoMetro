package gui.accountauth;

import com.metroporto.dao.userdao.MySqlUserDao;
import com.metroporto.dao.userdao.UserDaoInterface;
import com.metroporto.enums.Folder;
import com.metroporto.exceptions.DaoException;
import com.metroporto.users.Administrator;
import com.metroporto.users.Passenger;
import com.metroporto.users.Student;
import com.metroporto.users.User;
import gui.Controller;
import com.metroporto.enums.Page;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInController extends Controller
{
    UserDaoInterface userDao;

    @FXML
    private Label emailLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField emailText;

    @FXML
    private TextField passwordText;

    public SignInController()
    {
        userDao = new MySqlUserDao();
    }

    @Override
    public void initialize()
    {
        initialiseLogo();
        initialiseMetroImage("metro_3");
    }

    @Override
    public void setScene(Scene scene)
    {
        scene.heightProperty().addListener((observable, oldValue, newValue) ->
                metro1.fitHeightProperty().setValue(newValue)
        );
    }

    public void submitForm(ActionEvent event) throws IOException
    {
        String email = emailText.getText();
        String emailPattern = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher emailMatcher = pattern.matcher(email);

        String password = passwordText.getText();

        String asterisk = "*";
        String errorColour = "#de2a1d";

        Text redAsterisk = new Text(asterisk);
        redAsterisk.setFill(Color.web(errorColour));

        if (email.isEmpty() || !emailMatcher.matches())
        {
            errorText.setText(asterisk + " Invalid email address");
            emailLabel.setGraphic(redAsterisk);
            emailLabel.setContentDisplay(ContentDisplay.RIGHT);
        } else if (password.isEmpty())
        {
            emailLabel.setGraphic(null);
            errorText.setText(asterisk + " Password is required");
            passwordLabel.setGraphic(redAsterisk);
            passwordLabel.setContentDisplay(ContentDisplay.RIGHT);
        } else
        {
            emailLabel.setGraphic(null);
            passwordLabel.setGraphic(null);
            errorText.setText("");

            try
            {
                User user = userDao.findUserByEmail(email);

                if (user.getPassword().equals(password))
                {
                    errorText.setText("");
                    app.setUser(user);

                    if (user instanceof Passenger)
                    {
                        redirectToPage(event, Folder.HOME, Page.HOME);
                    }

                    if (user instanceof Administrator)
                    {
                        redirectToPage(event, Folder.ACCOUNT_AUTH, Page.ADMINISTRATOR);
                    }

                }
                else
                {
                    errorText.setText("Incorrect email or password");
                }

            } catch (DaoException de)
            {
                de.printStackTrace();
            }
        }
    }

    public void redirectToSignUp(ActionEvent event) throws IOException
    {
        redirectToPage(event, Folder.ACCOUNT_AUTH, Page.SIGN_UP);
    }
}
