package com.Capstone.InterviewTracking.repository;

import com.Capstone.InterviewTracking.entity.Feedback;
import com.Capstone.InterviewTracking.entity.Interview;
import com.Capstone.InterviewTracking.entity.Panel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByInterview(Interview interview);

    boolean existsByInterviewAndPanel(Interview interview, Panel panel);

    List<Feedback> findByInterviewIn(List<Interview> interviews);

    boolean existsByInterviewAndHrReviewer(Interview interview, String hrReviewer);
}
