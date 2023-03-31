package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class ControllersUtil<T>
{
    public void redirectToPage(String resourceName, ActionEvent event, Class<T> controllerClass) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ControllersUtil.class.getClassLoader().getResource(resourceName)));

        Parent root = loader.load();

        T controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        stage.setScene(scene);
        stage.show();

        // Call the setScene method of the controller if it exists
        if (controller != null)
        {
            try
            {
                Method setSceneMethod = controllerClass.getMethod("setScene", Scene.class);
                setSceneMethod.invoke(controller, scene);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
            {
                // The setScene method is not defined in the controller, do nothing
            }
        }
    }
}
