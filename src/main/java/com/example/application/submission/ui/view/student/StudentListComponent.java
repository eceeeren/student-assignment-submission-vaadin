package com.example.application.submission.ui.view.student;

import com.example.application.submission.domain.Student;
import com.example.application.submission.service.StudentService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.List;

public class StudentListComponent extends VerticalLayout {

    private final StudentService studentService;
    private final Grid<Student> studentGrid;
    private ListDataProvider<Student> dataProvider;

    public StudentListComponent(StudentService studentService) {
        this.studentService = studentService;

        setSizeFull();
        setPadding(false);
        setSpacing(true);

        studentGrid = new Grid<>(Student.class, false);
        setupGrid();
        loadData();

        add(studentGrid);
    }

    private void setupGrid() {
        studentGrid.setSizeFull();

        studentGrid.addColumn(Student::getId)
                .setHeader("ID")
                .setWidth("80px")
                .setFlexGrow(0);

        studentGrid.addColumn(Student::getName)
                .setHeader("Name")
                .setAutoWidth(true);

        studentGrid.addColumn(Student::getEmail)
                .setHeader("Email")
                .setAutoWidth(true);

        studentGrid.addComponentColumn(student -> new Div()).setHeader("Actions").setWidth("120px").setFlexGrow(0);
    }

    private void loadData() {
        try {
            List<Student> students = studentService.findAllStudents();
            dataProvider = new ListDataProvider<>(students);
            studentGrid.setDataProvider(dataProvider);
        } catch (Exception e) {
            Notification.show("Failed to load students: " + e.getMessage())
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    public void refreshData() {
        loadData();
    }
}