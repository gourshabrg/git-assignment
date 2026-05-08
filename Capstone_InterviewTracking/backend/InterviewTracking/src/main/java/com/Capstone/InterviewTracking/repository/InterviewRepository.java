package com.Capstone.InterviewTracking.repository;

import com.Capstone.InterviewTracking.entity.Candidate;
import com.Capstone.InterviewTracking.entity.Interview;
import com.Capstone.InterviewTracking.entity.Panel;
import com.Capstone.InterviewTracking.enums.InterviewRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByCandidate(Candidate candidate);

    List<Interview> findByPanelsContaining(Panel panel);

    Optional<Interview> findByCandidateAndRound(Candidate candidate, InterviewRound round);

    boolean existsByCandidateAndRound(Candidate candidate, InterviewRound round);

    List<Interview> findByRoundOrderByInterviewDateTimeDesc(InterviewRound round);
}
