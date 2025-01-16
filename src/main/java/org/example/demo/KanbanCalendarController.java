package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KanbanCalendarController {
    @FXML
    private VBox kanbanContainer;
    @FXML
    private VBox calendarContainer;

    private KanbanBoard kanbanBoard;
    private ProjectCalendar projectCalendar;
    private List<Projet> projets; // pr stocker les projets

    @FXML
    public void initialize() {
        kanbanBoard = new KanbanBoard();
        projectCalendar = new ProjectCalendar();

        if (kanbanContainer != null) {
            kanbanContainer.getChildren().add(kanbanBoard);
        }
        if (calendarContainer != null) {
            calendarContainer.getChildren().add(projectCalendar);
        }

        loadProjets();
        updateViews();
    }

    private void loadProjets() {
        // implementer le chargement des projets...
        projets = new ArrayList<>();
    }

    private void updateViews() {
        if (kanbanBoard != null && projets != null) {
            for (Projet projet : projets) {
                kanbanBoard.addProject(projet);
            }
        }

        if (projectCalendar != null && projets != null) {
            for (Projet projet : projets) {
                projectCalendar.addProject(projet);
            }
        }
    }

    public void addNewProject(Projet projet) {
        projets.add(projet);
        kanbanBoard.addProject(projet);
        projectCalendar.addProject(projet);
    }
}