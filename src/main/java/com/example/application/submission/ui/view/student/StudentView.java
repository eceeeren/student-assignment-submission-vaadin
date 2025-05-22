package com.example.application.submission.ui.view.student;

import com.example.application.submission.service.StudentService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class StudentView extends VerticalLayout {

    private final StudentService studentService;
    private StudentRegistrationForm registrationForm;
    private StudentListComponent studentListComponent;

    public StudentView(StudentService studentService) {
        this.studentService = studentService;

        setSizeFull();
        setPadding(true);

        add(createStudentsContent());
    }

    private VerticalLayout createStudentsContent() {
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setPadding(false);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);

        horizontalLayout.add(createRegistrationSection(), createStudentListSection());

        content.add(horizontalLayout);

        return content;
    }

    private VerticalLayout createRegistrationSection() {
        VerticalLayout registrationSection = new VerticalLayout();
        registrationSection.setPadding(false);
        registrationSection.setSpacing(true);
        registrationSection.setWidth("50%");

        com.vaadin.flow.component.html.H3 registrationTitle =
                new com.vaadin.flow.component.html.H3("Student Registration");
        registrationSection.add(registrationTitle);

        registrationForm = new StudentRegistrationForm(studentService, this::refreshStudentList);

        registrationSection.add(registrationForm);

        return registrationSection;
    }

    private VerticalLayout createStudentListSection() {
        VerticalLayout listSection = new VerticalLayout();
        listSection.setPadding(false);
        listSection.setSpacing(true);
        listSection.setWidth("50%");

        com.vaadin.flow.component.html.H3 listTitle =
                new com.vaadin.flow.component.html.H3("Student List");
        listSection.add(listTitle);

        studentListComponent = new StudentListComponent(studentService);
        listSection.add(studentListComponent);

        return listSection;
    }

    private void refreshStudentList() {
        if (studentListComponent != null) {
            studentListComponent.refreshData();
        }
    }
}