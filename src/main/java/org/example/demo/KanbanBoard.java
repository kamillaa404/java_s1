package org.example.demo;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.TransferMode;
import java.util.*;

public class KanbanBoard extends VBox {
    private HBox boardContainer;
    private List<VBox> columns;

    public KanbanBoard() {
        this.setPadding(new Insets(10));
        this.setSpacing(10);

        Label title = new Label("Project Kanban Board");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        boardContainer = new HBox();
        boardContainer.setSpacing(10);
        boardContainer.setPadding(new Insets(10));

        initializeColumns();

        this.getChildren().addAll(title, boardContainer);
    }

    private void initializeColumns() {
        columns = new ArrayList<>();
        String[] columnTitles = {"À faire", "En cours", "Terminé"};

        for (String title : columnTitles) {
            VBox column = createColumn(title);
            columns.add(column);
            boardContainer.getChildren().add(column);
        }
    }

    public void addProject(Projet projet) {
        if (columns == null) {
            initializeColumns();
        }

        VBox projectCard = createProjectCard(projet);
        int columnIndex = getColumnIndexForStatus(projet.getStatus());
        VBox targetColumn = columns.get(columnIndex);
        ((VBox) targetColumn.getChildren().get(1)).getChildren().add(projectCard);
    }

    private VBox createColumn(String title) {
        VBox column = new VBox();
        column.setPrefWidth(300);
        column.setSpacing(10);
        column.setPadding(new Insets(10));
        column.setStyle("-fx-background-color: #f4f5f7; -fx-background-radius: 5;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        VBox tasksContainer = new VBox();
        tasksContainer.setSpacing(5);

        column.getChildren().addAll(titleLabel, tasksContainer);

        setupDragAndDrop(tasksContainer);

        return column;
    }

    private void setupDragAndDrop(VBox container) {
        container.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });

        container.setOnDragDropped(event -> {
            event.setDropCompleted(true);
            event.consume();
        });
    }

    private VBox createProjectCard(Projet projet) {
        VBox card = new VBox();
        card.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 3;");
        card.setPadding(new Insets(10));
        card.setSpacing(5);

        Label titleLabel = new Label(projet.getNom());
        titleLabel.setStyle("-fx-font-weight: bold;");

        Label descLabel = new Label(projet.getDescription());
        descLabel.setWrapText(true);

        Label dateLabel = new Label("Date limite: " + projet.getDateLimite().toString());
        dateLabel.setStyle("-fx-font-size: 11px;");

        card.getChildren().addAll(titleLabel, descLabel, dateLabel);
        return card;
    }

    private int getColumnIndexForStatus(String status) {
        if (status == null) return 0;

        switch (status.toLowerCase()) {
            case "en cours":
                return 1;
            case "terminé":
                return 2;
            default:
                return 0;
        }
    }
}