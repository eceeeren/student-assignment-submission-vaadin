package com.example.application.submission.ui.view.student;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class StudentView extends VerticalLayout {

    public StudentView() {
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

        // Add both sections to horizontal layout
        horizontalLayout.add(createRegistrationSection(), createStudentListSection());

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
                new com.vaadin.flow.component.html.H3("Student Registration");
        registrationSection.add(registrationTitle);
        registrationSection.add(new StudentRegistrationForm());

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

        // Placeholder for student list
        Div studentListPlaceholder = new Div(new Text("Student list will be displayed here"));
        studentListPlaceholder.getStyle()
                .set("border", "1px dashed #ccc")
                .set("padding", "20px")
                .set("text-align", "center")
                .set("color", "#666")
                .set("min-height", "200px");
        listSection.add(studentListPlaceholder);

        return listSection;
    }
}