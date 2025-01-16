package org.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AjouterProjet implements Initializable {
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

    private ObservableList<Projet> projetsList;
    private ObservableList<Employe> availableEmployes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusComboBox.getItems().addAll("En cours", "Terminé", "En pause", "Annulé");
        statusComboBox.setValue("En cours");

        employesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        dateDebutPicker.setValue(LocalDate.now());
        dateLimitePicker.setValue(LocalDate.now().plusMonths(1));

        loadAvailableEmployes();
    }

    private void loadAvailableEmployes() {
        availableEmployes.add(new Employe(1, "Dupont", "Jean", "Développeur"));
        availableEmployes.add(new Employe(2, "Martin", "Marie", "Designer"));
        employesListView.setItems(availableEmployes);
    }

    public void setProjetsList(ObservableList<Projet> projetsList) {
        this.projetsList = projetsList;
    }

    @FXML
    private void sauvegarder() {
        if (validateInputs()) {
            Projet newProjet = new Projet(
                    generateNewId(),
                    nomField.getText(),
                    descriptionField.getText(),
                    dateDebutPicker.getValue(),
                    dateLimitePicker.getValue()
            );

            newProjet.getEmployes().addAll(employesListView.getSelectionModel().getSelectedItems());

            projetsList.add(newProjet);
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

    private int generateNewId() {
        return projetsList.size() + 1;
    }
}