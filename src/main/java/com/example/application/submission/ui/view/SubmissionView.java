package com.example.application.submission.ui.view;

import com.example.application.submission.service.AssignmentService;
import com.example.application.submission.service.StudentService;
import com.example.application.submission.ui.view.assignment.AssignmentView;
import com.example.application.submission.ui.view.student.StudentView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;

@Route(value = "submission", layout = MainLayout.class)
@PageTitle("Submission")
@Menu(title = "Submissions", icon = "vaadin:upload", order = 1)
@PermitAll
public class SubmissionView extends VerticalLayout {

    private final StudentService studentService;
    private final AssignmentService assignmentService;
    private final AuthenticationContext authenticationContext;

    private StudentView studentView;
    private AssignmentView assignmentView;

    public SubmissionView(StudentService studentService,
                          AssignmentService assignmentService,
                          AuthenticationContext authenticationContext) {
        this.studentService = studentService;
        this.assignmentService = assignmentService;
        this.authenticationContext = authenticationContext;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(createTabSheet());
    }

    private TabSheet createTabSheet() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        assignmentView = new AssignmentView(assignmentService, studentService, authenticationContext);
        studentView = new StudentView(studentService, authenticationContext, () -> assignmentView.refreshStudentsInForm());

        tabSheet.add("Students", studentView);
        tabSheet.add("Assignments", assignmentView);

        tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);
        return tabSheet;
    }
}