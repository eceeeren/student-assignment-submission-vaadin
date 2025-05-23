package com.example.application.submission.ui.view.student;

import com.example.application.submission.domain.Student;
import com.example.application.submission.service.StudentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class StudentRegistrationForm extends Div {

    private final StudentService studentService;
    private final Binder<Student> binder;
    private final Runnable onSuccessCallback;

    private TextField name;
    private EmailField email;
    private Button submitButton;
    private Button cancelButton;

    public StudentRegistrationForm(StudentService studentService, Runnable onSuccessCallback) {
        this.studentService = studentService;
        this.onSuccessCallback = onSuccessCallback;
        this.binder = new BeanValidationBinder<>(Student.class);

        createFields();
        createButtons();
        setupBinder();
        createLayout();

        binder.setBean(new Student());
    }

    private void createFields() {
        name = new TextField("Name");
        name.setRequired(true);
        name.setErrorMessage("Name is required");

        email = new EmailField("Email");
        email.setRequired(true);
        email.setErrorMessage("Please enter a valid email address");
    }

    private void createButtons() {
        submitButton = new Button("Register Student");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener(event -> handleSubmit());

        cancelButton = new Button("Cancel");
        cancelButton.addClickListener(event -> handleCancel());
    }

    private void setupBinder() {
        binder.forField(name)
                .asRequired("Name is required")
                .bind(Student::getName, Student::setName);

        binder.forField(email)
                .asRequired("Email is required")
                .bind(Student::getEmail, Student::setEmail);

        binder.addStatusChangeListener(event -> submitButton.setEnabled(binder.isValid()));
    }

    private void createLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(name, email, submitButton, cancelButton);
        formLayout.setResponsiveSteps(
                new ResponsiveStep("0", 1),
                new ResponsiveStep("500px", 2));

        formLayout.setColspan(name, 2);
        formLayout.setColspan(email, 2);
        formLayout.setColspan(submitButton, 1);
        formLayout.setColspan(cancelButton, 1);

        add(formLayout);
    }

    private void handleSubmit() {
        try {
            Student student = binder.getBean();
            binder.validate();

            if (binder.isValid()) {
                Student savedStudent = studentService.registerStudent(student);

                if (onSuccessCallback != null) {
                    onSuccessCallback.run();
                }

                Notification.show("Student registered successfully: " + savedStudent.getName())
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                clearForm();
            }

        } catch (IllegalArgumentException e) {
            Notification.show("Registration failed: " + e.getMessage())
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        } catch (Exception e) {
            Notification.show("An unexpected error occurred. Please try again.")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void handleCancel() {
        clearForm();
        binder.validate();
    }

    private void clearForm() {
        binder.setBean(new Student());
    }
}