package com.Capstone.InterviewTracking.repository;

import com.Capstone.InterviewTracking.entity.Application;
import com.Capstone.InterviewTracking.entity.Candidate;
import com.Capstone.InterviewTracking.entity.JobDescription;
import com.Capstone.InterviewTracking.enums.ApplicationStatus;
import com.Capstone.InterviewTracking.enums.InterviewStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByCandidateAndStatus(Candidate candidate, ApplicationStatus status);

    boolean existsByCandidateAndJob(Candidate candidate, JobDescription job);

    Optional<Application> findByCandidate(Candidate candidate);

    List<Application> findByCandidateOrderByCreatedAtDesc(Candidate candidate);

    List<Application> findAllByOrderByCreatedAtDesc();

    List<Application> findByStage(InterviewStage stage);

    List<Application> findByStatus(ApplicationStatus status);

    List<Application> findByJob(JobDescription job);

    List<Application> findByStageAndStatus(InterviewStage stage, ApplicationStatus status);
}
