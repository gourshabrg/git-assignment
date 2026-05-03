package com.Capstone.InterviewTracking.repository;

import com.Capstone.InterviewTracking.entity.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobDescriptionRepository extends JpaRepository<JobDescription, Long> {

    List<JobDescription> findByActiveTrueOrderByCreatedAtDesc();
    List<JobDescription> findAllByOrderByCreatedAtDesc();
}
