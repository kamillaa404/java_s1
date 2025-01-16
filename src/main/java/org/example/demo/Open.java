package org.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.IOException;
import java.net.URL;

public class Open {
    public void open(String fxmlFile, String title, boolean modal, Class<?> controllerClass) {
        try {
            URL resourceUrl = getClass().getResource("/org/example/demo/" + fxmlFile + ".fxml");
            if (resourceUrl == null) {
                System.err.println("Cannot find FXML file: " + fxmlFile);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));

            if (modal) {
                stage.initModality(Modality.APPLICATION_MODAL);
            }

            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading " + fxmlFile);
            e.printStackTrace();
        }
    }
}