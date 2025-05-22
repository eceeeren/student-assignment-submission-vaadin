package com.example.application.assignment.ui.view;

import com.example.application.assignment.ui.view.assignment.AssignmentView;
import com.example.application.assignment.ui.view.student.StudentView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "submission", layout = MainLayout.class)
@PageTitle("Submission")
@Menu(title = "Submissions", icon = "vaadin:upload", order = 1)
@PermitAll
public class SubmissionView extends VerticalLayout {

    public SubmissionView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(createTabSheet());
    }

    private TabSheet createTabSheet() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        tabSheet.add("Students", new StudentView());

        tabSheet.add("Assignments", new AssignmentView());

        tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);
        return tabSheet;
    }
}