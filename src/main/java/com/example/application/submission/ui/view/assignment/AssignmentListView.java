package com.example.application.submission.ui.view.assignment;

import com.example.application.submission.domain.Assignment;
import com.example.application.submission.service.AssignmentService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;

import java.util.List;

public class AssignmentListView extends VerticalLayout {

    private final AssignmentService assignmentService;
    private final Grid<Assignment> assignmentGrid;

    public AssignmentListView(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;

        setSizeFull();
        setPadding(false);
        setSpacing(true);

        assignmentGrid = new Grid<>(Assignment.class, false);
        setupGrid();
        loadData();

        add(assignmentGrid);
    }

    private void setupGrid() {
        assignmentGrid.setSizeFull();

        assignmentGrid.addColumn(Assignment::getId)
                .setHeader("ID")
                .setWidth("80px")
                .setFlexGrow(0);

        assignmentGrid.addColumn(Assignment::getTitle)
                .setHeader("Title")
                .setAutoWidth(true);

        assignmentGrid.addColumn(assignment -> {
                    String desc = assignment.getDescription();
                    if (desc.length() > 50) {
                        return desc.substring(0, 50) + "...";
                    }
                    return desc;
                })
                .setHeader("Description")
                .setAutoWidth(true);

        assignmentGrid.addColumn(assignment ->
                        assignment.getSubmittedBy().getName())
                .setHeader("Submitted By")
                .setAutoWidth(true);

        assignmentGrid.addColumn(new LocalDateTimeRenderer<>(
                        Assignment::getSubmittedAt,
                        "dd/MM/yyyy HH:mm"))
                .setHeader("Submitted At")
                .setWidth("140px")
                .setFlexGrow(0);

        assignmentGrid.addComponentColumn(assignment -> new Div())
                .setHeader("Actions")
                .setWidth("120px")
                .setFlexGrow(0);
    }

    private void loadData() {
        try {
            List<Assignment> assignments = assignmentService.findAllAssignments();
            ListDataProvider<Assignment> dataProvider = new ListDataProvider<>(assignments);
            assignmentGrid.setDataProvider(dataProvider);
        } catch (Exception e) {
            Notification.show("Failed to load assignments: " + e.getMessage())
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    public void refreshData() {
        loadData();
    }
}