package com.example.application.submission.ui.view.assignment;

import com.example.application.submission.service.AssignmentService;
import com.example.application.submission.service.StudentService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.security.AuthenticationContext;

public class AssignmentView extends VerticalLayout {

    private final AssignmentService assignmentService;
    private final StudentService studentService;
    private final AuthenticationContext authenticationContext;
    private AssignmentRegistrationForm registrationForm;
    private AssignmentListView assignmentListView;

    public AssignmentView(AssignmentService assignmentService,
                          StudentService studentService,
                          AuthenticationContext authenticationContext) {
        this.assignmentService = assignmentService;
        this.studentService = studentService;
        this.authenticationContext = authenticationContext;

        setSizeFull();
        setPadding(true);

        add(createAssignmentsContent());
    }

    private VerticalLayout createAssignmentsContent() {
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setPadding(false);

        if (authenticationContext.hasRole("ADMIN")) {
            VerticalLayout registrationSection = createRegistrationSection();
            VerticalLayout listSection = createAssignmentListSection();

            content.add(registrationSection, listSection);
            content.expand(listSection);
        } else {
            VerticalLayout listSection = createAssignmentListSection();
            content.add(listSection);
            content.expand(listSection);
        }

        return content;
    }

    private VerticalLayout createRegistrationSection() {
        VerticalLayout registrationSection = new VerticalLayout();
        registrationSection.setPadding(false);
        registrationSection.setSpacing(true);
        registrationSection.setWidthFull();

        com.vaadin.flow.component.html.H3 registrationTitle =
                new com.vaadin.flow.component.html.H3("Assignment Registration");
        registrationSection.add(registrationTitle);

        registrationForm = new AssignmentRegistrationForm(
                assignmentService,
                studentService,
                this::refreshAssignmentList
        );
        registrationForm.setWidthFull();
        registrationSection.add(registrationForm);

        return registrationSection;
    }

    private VerticalLayout createAssignmentListSection() {
        VerticalLayout listSection = new VerticalLayout();
        listSection.setPadding(false);
        listSection.setSpacing(true);
        listSection.setSizeFull();

        com.vaadin.flow.component.html.H3 listTitle =
                new com.vaadin.flow.component.html.H3("Assignment List");
        listSection.add(listTitle);

        assignmentListView = new AssignmentListView(assignmentService);
        assignmentListView.setSizeFull();
        listSection.add(assignmentListView);
        listSection.expand(assignmentListView);

        return listSection;
    }

    private void refreshAssignmentList() {
        if (assignmentListView != null) {
            assignmentListView.refreshData();
        }
    }

    public void refreshStudentsInForm() {
        if (registrationForm != null) {
            registrationForm.refreshStudents();
        }
    }
}