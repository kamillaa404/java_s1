package org.example.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AjouterEmploye {
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField posteField;
    @FXML private Label messageLabel;

    private ObservableList<Employe> employesList;

    public void setEmployesList(ObservableList<Employe> employesList) {
        this.employesList = employesList;
    }

    @FXML
    private void handleAjouter() {
        if (validateFields()) {
            int newId = employesList.size() + 1;

            Employe newEmploye = new Employe(
                    newId,
                    nomField.getText(),
                    prenomField.getText(),
                    posteField.getText()
            );

            employesList.add(newEmploye);
            closeWindow();
        }
    }

    @FXML
    private void handleAnnuler() {
        closeWindow();
    }

    private boolean validateFields() {
        if (nomField.getText().trim().isEmpty() ||
                prenomField.getText().trim().isEmpty() ||
                posteField.getText().trim().isEmpty()) {

            messageLabel.setText("Tous les champs sont obligatoires");
            return false;
        }
        return true;
    }

    private void closeWindow() {
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }
}