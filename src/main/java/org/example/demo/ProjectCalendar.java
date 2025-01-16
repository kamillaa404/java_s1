package org.example.demo;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class ProjectCalendar extends VBox {
    private GridPane calendarGrid;
    private YearMonth currentYearMonth;
    private Map<LocalDate, List<Projet>> projectDeadlines;

    public ProjectCalendar() {
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        currentYearMonth = YearMonth.now();
        projectDeadlines = new HashMap<>();

        initializeCalendar();
    }

    private void initializeCalendar() {
        HBox header = new HBox(10);
        Button prevMonth = new Button("←");
        Button nextMonth = new Button("→");
        Label monthLabel = new Label(currentYearMonth.toString());
        monthLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        prevMonth.setOnAction(e -> {
            currentYearMonth = currentYearMonth.minusMonths(1);
            monthLabel.setText(currentYearMonth.toString());
            updateCalendarView();
        });

        nextMonth.setOnAction(e -> {
            currentYearMonth = currentYearMonth.plusMonths(1);
            monthLabel.setText(currentYearMonth.toString());
            updateCalendarView();
        });

        header.getChildren().addAll(prevMonth, monthLabel, nextMonth);

        calendarGrid = new GridPane();
        calendarGrid.setHgap(5);
        calendarGrid.setVgap(5);

        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < 7; i++) {
            Label dayLabel = new Label(weekDays[i]);
            dayLabel.setStyle("-fx-font-weight: bold;");
            calendarGrid.add(dayLabel, i, 0);
        }

        this.getChildren().addAll(header, calendarGrid);
        updateCalendarView();
    }

    private void updateCalendarView() {
        calendarGrid.getChildren().removeIf(node ->
                GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;

        int day = 1;
        int monthLength = currentYearMonth.lengthOfMonth();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0 && j < dayOfWeek || day > monthLength) {
                    continue;
                }

                VBox dateCell = createDateCell(day);
                calendarGrid.add(dateCell, j, i + 1);
                day++;
            }
        }
    }

    private VBox createDateCell(int day) {
        VBox cell = new VBox(5);
        cell.setPadding(new Insets(5));
        cell.setStyle("-fx-border-color: #e0e0e0; -fx-background-color: white;");

        Label dateLabel = new Label(String.valueOf(day));
        cell.getChildren().add(dateLabel);

        LocalDate date = currentYearMonth.atDay(day);
        if (projectDeadlines.containsKey(date)) {
            for (Projet projet : projectDeadlines.get(date)) {
                VBox projectBox = new VBox(2);
                projectBox.setStyle("-fx-background-color: #e8f2ff; -fx-padding: 5; -fx-background-radius: 3;");

                Label projectLabel = new Label(projet.getNom());
                projectLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold;");

                Label dateLabel2 = new Label("Date limite: " + projet.getDateLimite().toString());
                dateLabel2.setStyle("-fx-font-size: 10px;");

                Label statusLabel = new Label("Status: " + projet.getStatus());
                statusLabel.setStyle("-fx-font-size: 10px;");

                projectBox.getChildren().addAll(projectLabel, dateLabel2, statusLabel);
                cell.getChildren().add(projectBox);
            }
        }

        return cell;
    }

    public void addProject(Projet projet) {
        if (!projectDeadlines.containsKey(projet.getDateLimite())) {
            projectDeadlines.put(projet.getDateLimite(), new ArrayList<>());
        }
        projectDeadlines.get(projet.getDateLimite()).add(projet);
        updateCalendarView();
    }
}