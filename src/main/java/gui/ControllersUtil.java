package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ControllersUtil
{
    public void redirectToSignUp(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/sign_up.fxml")));

        Parent root = loader.load();

        SignUpController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();

        controller.setScene(scene);
        controller.setApp(App.getApplication());
    }

    public void redirectToSignIn(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/sign_in.fxml")));

        Parent root = loader.load();

        SignInController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();

        controller.setScene(scene);
    }

    public void redirectToPassengerCard(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/passenger_card.fxml")));

        Parent root = loader.load();

        PassengerCardController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();

        controller.setScene(scene);
        controller.setApp(App.getApplication());
    }

    public void redirectToStudentUniversity(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/student_university.fxml")));

        Parent root = loader.load();

        StudentUniversityController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();

        controller.setScene(scene);
        controller.setApp(App.getApplication());
    }

    public void redirectToCardZone(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/card_zone.fxml")));

        Parent root = loader.load();

        CardZoneController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();

        controller.setScene(scene);
        controller.setApp(App.getApplication());
    }

    public void redirectToCardInvoice(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/card_invoice.fxml")));

        Parent root = loader.load();

        CardInvoiceController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();

        controller.setScene(scene);
        controller.setApp(App.getApplication());
    }

    public void redirectToHome(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/home.fxml")));

        Parent root = loader.load();

        HomeController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();

        controller.setApp(App.getApplication());
    }

    public void redirectToSchedule(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/schedule.fxml")));

        Parent root = loader.load();

        ScheduleController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();

        controller.setScene(scene);
    }

    public void redirectToJourneyRoute(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/journey_route.fxml")));

        Parent root = loader.load();

        JourneyRouteController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();
    }

    public void redirectToStation(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/station.fxml")));

        Parent root = loader.load();

        StationController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();
    }

    public void redirectToProfile(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/profile.fxml")));

        Parent root = loader.load();

        ProfileController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();
    }

    public void redirectToCard(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/card.fxml")));

        Parent root = loader.load();

        CardController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();
    }

    public void redirectToHome(MouseEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/home.fxml")));

        Parent root = loader.load();

        HomeController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();

        controller.setApp(App.getApplication());
    }

    public void redirectToSchedule(MouseEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/schedule.fxml")));

        Parent root = loader.load();

        ScheduleController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();
    }

    public void redirectToJourneyRoute(MouseEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/journey_route.fxml")));

        Parent root = loader.load();

        JourneyRouteController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();
    }

    public void redirectToStation(MouseEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/station.fxml")));

        Parent root = loader.load();

        StationController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();
    }

    public void redirectToProfile(MouseEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/profile.fxml")));

        Parent root = loader.load();

        ProfileController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();
    }

    public void redirectToCard(MouseEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource("com/gui/card.fxml")));

        Parent root = loader.load();

        CardController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();
    }
}
