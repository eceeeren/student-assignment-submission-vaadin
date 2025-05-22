package com.example.application.assignment.ui.view.student;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

public class StudentRegistrationForm extends Div {

    public StudentRegistrationForm() {
        TextField name = new TextField("Name");
        EmailField email = new EmailField("Email");

        Button submitButton = new Button("Submit");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        submitButton.addClickListener(event -> {
            // TODO: Add validation and save logic
            String nameValue = name.getValue();
            String emailValue = email.getValue();
        });

        FormLayout formLayout = new FormLayout();
        formLayout.add(name, email, submitButton);
        formLayout.setResponsiveSteps(
                new ResponsiveStep("0", 1),
                new ResponsiveStep("500px", 2));

        formLayout.setColspan(name, 2);
        formLayout.setColspan(email, 2);
        formLayout.setColspan(submitButton, 1);

        add(formLayout);
    }

}