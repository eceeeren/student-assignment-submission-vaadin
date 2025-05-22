package com.example.application.submission.service;

import com.example.application.submission.domain.Assignment;
import com.example.application.submission.domain.Student;
import com.example.application.submission.repository.AssignmentRepository;
import com.example.application.submission.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository, StudentRepository studentRepository) {
        this.assignmentRepository = assignmentRepository;
        this.studentRepository = studentRepository;
    }

    public List<Assignment> findAllAssignments() {
        return assignmentRepository.findAllByOrderBySubmittedAtDesc();
    }

    public Assignment submitAssignment(Long studentId, @Valid Assignment assignment) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + studentId));

        assignment.setSubmittedBy(student);

        return assignmentRepository.save(assignment);
    }
}