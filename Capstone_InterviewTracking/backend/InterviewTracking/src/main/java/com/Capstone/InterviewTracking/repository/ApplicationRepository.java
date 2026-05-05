package com.Capstone.InterviewTracking.repository;

import com.Capstone.InterviewTracking.entity.*;
import com.Capstone.InterviewTracking.enums.ApplicationStatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByCandidateAndStatus(Candidate candidate, ApplicationStatus status);

    boolean existsByCandidateAndJob(Candidate candidate, JobDescription job);
}