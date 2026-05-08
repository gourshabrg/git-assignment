package com.Capstone.InterviewTracking.repository;

import com.Capstone.InterviewTracking.entity.Candidate;
import com.Capstone.InterviewTracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByEmail(String email);
    Optional<Candidate> findByUser(User user);

    List<Candidate> findByJobDescriptionId(Long jobId);
   
     boolean existsByEmail(String email);
}