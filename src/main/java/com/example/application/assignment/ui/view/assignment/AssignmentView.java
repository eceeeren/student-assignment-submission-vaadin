package com.example.application.assignment.ui.view.assignment;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AssignmentView extends VerticalLayout {

    public AssignmentView() {
        setSizeFull();
        setPadding(true);

        add(createAssignmentsContent());
    }

    private VerticalLayout createAssignmentsContent() {
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setPadding(false);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);

        // Add both sections to horizontal layout
        horizontalLayout.add(createRegistrationSection(), createAssignmentListSection());

        // Add horizontal layout to main content
        content.add(horizontalLayout);

        return content;
    }

    private VerticalLayout createRegistrationSection() {
        VerticalLayout registrationSection = new VerticalLayout();
        registrationSection.setPadding(false);
        registrationSection.setSpacing(true);
        registrationSection.setWidth("50%");

        com.vaadin.flow.component.html.H3 registrationTitle =
                new com.vaadin.flow.component.html.H3("Assignment Registration");
        registrationSection.add(registrationTitle);
        registrationSection.add(new AssignmentRegistrationForm());

        return registrationSection;
    }

    private VerticalLayout createAssignmentListSection() {
        VerticalLayout listSection = new VerticalLayout();
        listSection.setPadding(false);
        listSection.setSpacing(true);
        listSection.setWidth("50%");

        com.vaadin.flow.component.html.H3 listTitle =
                new com.vaadin.flow.component.html.H3("Assignment List");
        listSection.add(listTitle);

        // Placeholder for Assignment list
        Div assignmentListPlaceholder = new Div(new Text("Assignment list will be displayed here"));
        assignmentListPlaceholder.getStyle()
                .set("border", "1px dashed #ccc")
                .set("padding", "20px")
                .set("text-align", "center")
                .set("color", "#666")
                .set("min-height", "200px");
        listSection.add(assignmentListPlaceholder);

        return listSection;
    }
}
