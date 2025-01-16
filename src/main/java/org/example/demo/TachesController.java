package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;

public class TachesController {
    @FXML
    private TextField titreField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private DatePicker dateLimitePicker;
    @FXML
    private ComboBox<String> prioriteCombo;
    @FXML
    private ComboBox<String> categorieCombo;
    @FXML
    private ComboBox<String> statusCombo;
    @FXML
    private ListView<Employe> employesListView;
    @FXML
    private TableView<Tache> tachesTable;
    @FXML
    private TableColumn<Tache, Integer> idColumn;
    @FXML
    private TableColumn<Tache, String> titreColumn;
    @FXML
    private TableColumn<Tache, String> descriptionColumn;
    @FXML
    private TableColumn<Tache, LocalDate> dateLimiteColumn;
    @FXML
    private TableColumn<Tache, String> prioriteColumn;
    @FXML
    private TableColumn<Tache, String> statusColumn;

    private ObservableList<Tache> taches = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialiser les ComboBox
        prioriteCombo.getItems().addAll("BASSE", "MOYENNE", "URGENT");
        categorieCombo.getItems().addAll("DEVELOPPEMENT", "DESIGN", "TEST", "AUTRE");
        statusCombo.getItems().addAll("À FAIRE", "EN COURS", "TERMINÉ");

        // Configurer les colonnes de la TableView
        idColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        titreColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTitre()));

        descriptionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescription()));

        dateLimiteColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDateLimite()));

        prioriteColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPriorite()));

        statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus()));

        tachesTable.setItems(taches);
    }

    @FXML
    private void handleAjouter() {
        if (validerChamps()) {
            Tache nouvelleTache = new Tache(
                    taches.size() + 1,
                    titreField.getText(),
                    descriptionArea.getText(),
                    dateLimitePicker.getValue(),
                    null
            );
            nouvelleTache.setPriorite(prioriteCombo.getValue());
            nouvelleTache.setCategorie(categorieCombo.getValue());
            nouvelleTache.setStatus(statusCombo.getValue());

            taches.add(nouvelleTache);
            viderChamps();
        }
    }

    @FXML
    private void handleModifier() {
        Tache tacheSelectionnee = tachesTable.getSelectionModel().getSelectedItem();
        if (tacheSelectionnee != null && validerChamps()) {
            tacheSelectionnee.setTitre(titreField.getText());
            tacheSelectionnee.setDescription(descriptionArea.getText());
            tacheSelectionnee.setDateLimite(dateLimitePicker.getValue());
            tacheSelectionnee.setPriorite(prioriteCombo.getValue());
            tacheSelectionnee.setCategorie(categorieCombo.getValue());
            tacheSelectionnee.setStatus(statusCombo.getValue());

            tachesTable.refresh();
            viderChamps();
        }
    }

    @FXML
    private void handleSupprimer() {
        Tache tacheSelectionnee = tachesTable.getSelectionModel().getSelectedItem();
        if (tacheSelectionnee != null) {
            taches.remove(tacheSelectionnee);
        }
    }

    private boolean validerChamps() {
        if (titreField.getText().isEmpty() || descriptionArea.getText().isEmpty()
                || dateLimitePicker.getValue() == null || prioriteCombo.getValue() == null
                || categorieCombo.getValue() == null || statusCombo.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void viderChamps() {
        titreField.clear();
        descriptionArea.clear();
        dateLimitePicker.setValue(null);
        prioriteCombo.setValue(null);
        categorieCombo.setValue(null);
        statusCombo.setValue(null);
    }
}