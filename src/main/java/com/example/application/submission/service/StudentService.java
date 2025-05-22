package com.example.application.submission.service;

import com.example.application.submission.domain.Student;
import com.example.application.submission.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Student registerStudent(@Valid Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + student.getEmail());
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(@Valid Student student) {
        if (!studentRepository.existsById(student.getId())) {
            throw new IllegalArgumentException("Student not found with id: " + student.getId());
        }
        return studentRepository.save(student);
    }
}