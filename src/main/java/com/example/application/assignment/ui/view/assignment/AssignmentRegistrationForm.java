package com.example.application.assignment.ui.view.assignment;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class AssignmentRegistrationForm extends Div {

    public AssignmentRegistrationForm() {
        TextField title = new TextField("Title");
        title.setRequired(true);
        title.setRequiredIndicatorVisible(true);

        TextArea description = new TextArea("Description");
        description.setMinHeight("100px");
        description.setMaxHeight("200px");

        TextField submittedBy = new TextField("Submitted By");
        submittedBy.setRequired(true);
        submittedBy.setRequiredIndicatorVisible(true);

        Button submitButton = new Button("Submit");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FormLayout formLayout = new FormLayout();
        formLayout.add(title, description, submittedBy, submitButton);
        formLayout.setResponsiveSteps(
                new ResponsiveStep("0", 1),
                new ResponsiveStep("500px", 2));

        formLayout.setColspan(title, 2);
        formLayout.setColspan(description, 2);
        formLayout.setColspan(submittedBy, 2);
        formLayout.setColspan(submitButton, 1);

        add(formLayout);
    }
}