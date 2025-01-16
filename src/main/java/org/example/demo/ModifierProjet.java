package org.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifierProjet implements Initializable {
    @FXML
    private TextField nomField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private DatePicker dateDebutPicker;
    @FXML
    private DatePicker dateLimitePicker;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private ListView<Employe> employesListView;
    @FXML
    private Label messageLabel;

    private Projet projet;
    private ObservableList<Projet> projetsList;
    private ObservableList<Employe> availableEmployes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusComboBox.getItems().addAll("En cours", "Terminé", "En pause", "Annulé");

        employesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        loadAvailableEmployes();
    }

    private void loadAvailableEmployes() {
        availableEmployes.add(new Employe(1, "Dupont", "Jean", "Développeur"));
        availableEmployes.add(new Employe(2, "Martin", "Marie", "Designer"));
        employesListView.setItems(availableEmployes);
    }

    public void setProjet(Projet projet) {
        this.projet = projet;

        nomField.setText(projet.getNom());
        descriptionField.setText(projet.getDescription());
        dateDebutPicker.setValue(projet.getDateDebut());
        dateLimitePicker.setValue(projet.getDateLimite());
        statusComboBox.setValue(projet.getStatus());

        for (Employe employe : projet.getEmployes()) {
            for (Employe availableEmploye : availableEmployes) {
                if (availableEmploye.getId() == employe.getId()) {
                    employesListView.getSelectionModel().select(availableEmploye);
                }
            }
        }
    }

    public void setProjetsList(ObservableList<Projet> projetsList) {
        this.projetsList = projetsList;
    }

    @FXML
    private void sauvegarder() {
        if (validateInputs()) {
            projet.setNom(nomField.getText());
            projet.setDescription(descriptionField.getText());
            projet.setDateDebut(dateDebutPicker.getValue());
            projet.setDateLimite(dateLimitePicker.getValue());
            projet.setStatus(statusComboBox.getValue());

            projet.getEmployes().clear();
            projet.getEmployes().addAll(employesListView.getSelectionModel().getSelectedItems());

            int index = projetsList.indexOf(projet);
            projetsList.set(index, projet);

            closeWindow();
        }
    }

    @FXML
    private void annuler() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }

    private boolean validateInputs() {
        if (nomField.getText().isEmpty()) {
            messageLabel.setText("Le nom du projet est requis");
            return false;
        }
        if (dateDebutPicker.getValue() == null || dateLimitePicker.getValue() == null) {
            messageLabel.setText("Les dates sont requises");
            return false;
        }
        if (dateLimitePicker.getValue().isBefore(dateDebutPicker.getValue())) {
            messageLabel.setText("La date limite doit être après la date de début");
            return false;
        }
        return true;
    }
}