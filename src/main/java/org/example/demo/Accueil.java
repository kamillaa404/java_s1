package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.IOException;
import java.net.URL;

public class Accueil {
    @FXML
    public Button boutton_employe;
    @FXML
    public Button boutton_projet;
    @FXML
    public Button boutton_calendrier;

    @FXML
    private void initialize() {
        boutton_employe.setOnAction(e -> openPage("/org/example/demo/employe.fxml", "Gestion des Employés"));
        boutton_projet.setOnAction(e -> openPage("/org/example/demo/projet.fxml", "Gestion des Projets"));
        boutton_calendrier.setOnAction(e -> openPage("/org/example/demo/kanbancontroller.fxml", "Calendrier"));
    }

    private void openPage(String fxmlPath, String title) {
        try {
            URL resourceUrl = getClass().getResource(fxmlPath);
            if (resourceUrl == null) {
                System.err.println("Cannot find FXML file: " + fxmlPath);
                return;
            }

            // nouveau fichier fxml
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();

            // nouveau stage
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading " + fxmlPath);
            e.printStackTrace();
        }
    }

    @FXML
    public void openEmploye() {
        openPage("/org/example/demo/employe.fxml", "Gestion des Employés");
    }
    @FXML
    public void openCalendrier() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("kanbancontroller.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Kanban Board & Calendar");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}