package org.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProjetController implements Initializable {
    @FXML private TableView<Projet> tableViewProjets;
    @FXML private TableColumn<Projet, String> colonneNom;
    @FXML private TableColumn<Projet, String> colonneDescription;
    @FXML private TableColumn<Projet, LocalDate> colonneDateDebut;
    @FXML private TableColumn<Projet, LocalDate> colonneDateLimite;
    @FXML private TableColumn<Projet, String> colonneStatus;
    @FXML private TableColumn<Projet, String> colonneEmployes;

    @FXML private Button btnAjouter;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    @FXML private Label messageLabel;

    private ObservableList<Projet> projets = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colonneNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colonneDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colonneDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colonneDateLimite.setCellValueFactory(new PropertyValueFactory<>("dateLimite"));
        colonneStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        colonneEmployes.setCellValueFactory(cellData -> {
            List<Employe> employesList = cellData.getValue().getEmployes();
            String employesStr = employesList.stream()
                    .map(e -> e.getNom() + " " + e.getPrenom())
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(employesStr);
        });

        colonneDateDebut.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });
        colonneDateLimite.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });

        tableViewProjets.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean disableButtons = newSelection == null;
            btnModifier.setDisable(disableButtons);
            btnSupprimer.setDisable(disableButtons);
        });

        loadProjets();
    }

    private void loadProjets() {
        projets.add(new Projet(1, "Site Web", "Développement site e-commerce",
                LocalDate.now(), LocalDate.now().plusMonths(3)));
        tableViewProjets.setItems(projets);
    }

    @FXML
    private void openAjouterProjet() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/ajouterProjet.fxml"));            Parent root = loader.load();

            AjouterProjet controller = loader.getController();
            controller.setProjetsList(projets);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter un projet");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture du formulaire");
            e.printStackTrace();
        }
    }

    @FXML
    private void openModifierProjet() {
        Projet selectedProjet = tableViewProjets.getSelectionModel().getSelectedItem();
        if (selectedProjet != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/modifierProjet.fxml"));
                Parent root = loader.load();

                ModifierProjet controller = loader.getController();
                controller.setProjet(selectedProjet);
                controller.setProjetsList(projets);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Modifier un projet");
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (IOException e) {
                messageLabel.setText("Erreur lors de l'ouverture du formulaire");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void supprimerProjet() {
        Projet selectedProjet = tableViewProjets.getSelectionModel().getSelectedItem();
        if (selectedProjet != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer le projet");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer le projet " +
                    selectedProjet.getNom() + " ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                projets.remove(selectedProjet);
                messageLabel.setText("Projet supprimé avec succès");
            }
        }
    }


}