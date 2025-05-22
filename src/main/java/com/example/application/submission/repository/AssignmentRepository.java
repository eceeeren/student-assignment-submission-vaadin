package com.example.application.submission.repository;

import com.example.application.submission.domain.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findAllByOrderBySubmittedAtDesc();
}