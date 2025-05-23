package com.example.application.submission.ui.view.student;

import com.example.application.submission.service.StudentService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.security.AuthenticationContext;

public class StudentView extends VerticalLayout {

    private final StudentService studentService;
    private final AuthenticationContext authenticationContext;
    private final Runnable onStudentAddedCallback;
    private StudentListComponent studentListComponent;

    public StudentView(StudentService studentService,
                       AuthenticationContext authenticationContext,
                       Runnable onStudentAddedCallback) {
        this.studentService = studentService;
        this.authenticationContext = authenticationContext;
        this.onStudentAddedCallback = onStudentAddedCallback;

        setSizeFull();
        setPadding(true);

        add(createStudentsContent());
    }

    private VerticalLayout createStudentsContent() {
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setPadding(false);

        if (authenticationContext.hasRole("ADMIN")) {
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.setSizeFull();
            horizontalLayout.setSpacing(true);
            horizontalLayout.add(createRegistrationSection(), createStudentListSection());
            content.add(horizontalLayout);
        } else {
            VerticalLayout listSection = new VerticalLayout();
            listSection.setPadding(false);
            listSection.setSpacing(true);
            listSection.setSizeFull();

            com.vaadin.flow.component.html.H3 listTitle =
                    new com.vaadin.flow.component.html.H3("Student List");
            listSection.add(listTitle);

            studentListComponent = new StudentListComponent(studentService);
            listSection.add(studentListComponent);

            content.add(listSection);
        }

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

        StudentRegistrationForm registrationForm = new StudentRegistrationForm(studentService, () -> {
            refreshStudentList();
            if (onStudentAddedCallback != null) {
                onStudentAddedCallback.run();
            }
        });

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