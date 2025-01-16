package org.example.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ModifierEmploye {
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField posteField;
    @FXML private Label messageLabel;

    private Employe employeToModify;
    private ObservableList<Employe> employesList;

    public void setEmploye(Employe employe) {
        this.employeToModify = employe;
        nomField.setText(employe.getNom());
        prenomField.setText(employe.getPrenom());
        posteField.setText(employe.getPoste());
    }

    public void setEmployesList(ObservableList<Employe> employesList) {
        this.employesList = employesList;
    }

    @FXML
    private void handleEnregistrer() {
        if (validateFields()) {
            employeToModify.setNom(nomField.getText().trim());
            employeToModify.setPrenom(prenomField.getText().trim());
            employeToModify.setPoste(posteField.getText().trim());

            int index = employesList.indexOf(employeToModify);
            if (index >= 0) {
                employesList.set(index, employeToModify);
            }

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