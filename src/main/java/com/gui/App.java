package com.gui;

import com.gui.controllers.Controller;
import com.metroporto.enums.Folder;
import com.metroporto.enums.Page;
import com.metroporto.users.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Porto Metro Application
 * Authors: Luana Kimley & Niall O'Reilly
 */
public class App extends Application
{
    public static void main(String[] args) {
        launch(args);
    }

    private User user;

    private static App instance;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("fxml/" + Folder.ACCOUNT_AUTH.getLabel() + "/" + Page.SIGN_IN.getLabel() + ".fxml")));
        Parent root = loader.load();
        Controller controller = loader.getController();

        instance = this;

        primaryStage.setTitle("Porto Metro");
        Scene scene = new Scene(root, 1200, 768);
        primaryStage.setScene(scene);
        primaryStage.show();
        controller.setScene(scene);
        controller.setApp(instance);
    }

    public static App getApplication() {
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
