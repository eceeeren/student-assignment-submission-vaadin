package com.example.application.submission.ui.view.assignment;

import com.example.application.submission.domain.Assignment;
import com.example.application.submission.domain.Student;
import com.example.application.submission.service.AssignmentService;
import com.example.application.submission.service.StudentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

public class AssignmentRegistrationForm extends Div {

    private final AssignmentService assignmentService;
    private final StudentService studentService;
    private final Binder<Assignment> binder;
    private final Runnable onSuccessCallback;

    private TextField title;
    private TextArea description;
    private ComboBox<Student> submittedBy;
    private Button submitButton;
    private Button cancelButton;

    public AssignmentRegistrationForm(AssignmentService assignmentService,
                                      StudentService studentService,
                                      Runnable onSuccessCallback) {
        this.assignmentService = assignmentService;
        this.studentService = studentService;
        this.onSuccessCallback = onSuccessCallback;
        this.binder = new BeanValidationBinder<>(Assignment.class);

        createFields();
        createButtons();
        setupBinder();
        createLayout();
        loadStudents();

        binder.setBean(new Assignment());
    }

    private void createFields() {
        title = new TextField("Title");
        title.setRequired(true);
        title.setErrorMessage("Title is required");

        description = new TextArea("Description");
        description.setMinHeight("100px");
        description.setMaxHeight("200px");

        submittedBy = new ComboBox<>("Submitted By");
        submittedBy.setRequired(true);
        submittedBy.setErrorMessage("Please select a student");
        submittedBy.setItemLabelGenerator(student -> student.getName() + " (" + student.getEmail() + ")");
    }

    private void createButtons() {
        submitButton = new Button("Submit Assignment");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener(event -> handleSubmit());

        cancelButton = new Button("Cancel");
        cancelButton.addClickListener(event -> handleCancel());
    }

    private void setupBinder() {
        binder.forField(title)
                .asRequired("Title is required")
                .bind(Assignment::getTitle, Assignment::setTitle);

        binder.forField(description)
                .bind(Assignment::getDescription, Assignment::setDescription);

        binder.forField(submittedBy)
                .asRequired("Student is required")
                .bind(Assignment::getSubmittedBy, Assignment::setSubmittedBy);

        binder.addStatusChangeListener(event -> submitButton.setEnabled(binder.isValid()));
    }

    private void createLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(title, submittedBy, description, submitButton, cancelButton);
        formLayout.setResponsiveSteps(
                new ResponsiveStep("0", 1),
                new ResponsiveStep("500px", 2));

        formLayout.setColspan(title, 1);
        formLayout.setColspan(submittedBy, 1);
        formLayout.setColspan(description, 2);
        formLayout.setColspan(submitButton, 1);
        formLayout.setColspan(cancelButton, 1);

        add(formLayout);
    }

    private void loadStudents() {
        try {
            List<Student> students = studentService.findAllStudents();
            submittedBy.setItems(students);
        } catch (Exception e) {
            Notification.show("Failed to load students: " + e.getMessage())
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void handleSubmit() {
        try {
            Assignment assignment = binder.getBean();
            binder.validate();

            if (binder.isValid()) {
                Student selectedStudent = assignment.getSubmittedBy();
                Assignment savedAssignment = assignmentService.submitAssignment(
                        selectedStudent.getId(), assignment);

                if (onSuccessCallback != null) {
                    onSuccessCallback.run();
                }

                Notification.show("Assignment submitted successfully: " + savedAssignment.getTitle())
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                clearForm();
            }

        } catch (IllegalArgumentException e) {
            Notification.show("Assignment submission failed: " + e.getMessage())
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
        binder.setBean(new Assignment());
        loadStudents();
    }

    public void refreshStudents() {
        loadStudents();
    }
}