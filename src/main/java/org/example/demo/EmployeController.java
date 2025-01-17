package org.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.io.IOException;
import java.util.Optional;

public class EmployeController {
    @FXML private ListView<Employe> listViewEmployes;
    @FXML private Button boutton_openajouterEPY;
    @FXML private Button boutton_openmodifierEPY;
    @FXML private Button boutton_supprimerEPY;
    @FXML private Label messageLabel;

    private ObservableList<Employe> employes = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        listViewEmployes.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Employe employe, boolean empty) {
                super.updateItem(employe, empty);
                if (empty || employe == null) {
                    setText(null);
                } else {
                    setText(employe.getNom() + " " + employe.getPrenom() + " - " + employe.getPoste());
                }
            }
        });

        listViewEmployes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean disableButtons = newSelection == null;
            boutton_openmodifierEPY.setDisable(disableButtons);
            boutton_supprimerEPY.setDisable(disableButtons);
        });

        loadEmployees();
    }

    private void loadEmployees() {
        employes.add(new Employe(1, "Dupont", "Jean", "Développeur"));
        employes.add(new Employe(2, "Martin", "Marie", "Designer"));
        listViewEmployes.setItems(employes);
    }

    @FXML
    private void openAjouterEmploye() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/ajouterEmploye.fxml"));
            Parent root = loader.load();

            AjouterEmploye controller = loader.getController();
            controller.setEmployesList(employes);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter un employé");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture du formulaire");
            e.printStackTrace();
        }
    }

    @FXML
    private void openModifierEmploye() {
        Employe selectedEmploye = listViewEmployes.getSelectionModel().getSelectedItem();
        if (selectedEmploye != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/modifierEmploye.fxml"));
                Parent root = loader.load();

                ModifierEmploye controller = loader.getController();
                controller.setEmploye(selectedEmploye);
                controller.setEmployesList(employes);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Modifier un employé");
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (IOException e) {
                messageLabel.setText("Erreur lors de l'ouverture du formulaire");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void supprimerEmploye() {
        Employe selectedEmploye = listViewEmployes.getSelectionModel().getSelectedItem();
        if (selectedEmploye != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer l'employé");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer " +
                    selectedEmploye.getNom() + " " + selectedEmploye.getPrenom() + " ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                employes.remove(selectedEmploye);
                messageLabel.setText("Employé supprimé avec succès");
            }
        }
    }
}
