package com.Capstone.InterviewTracking.entity;

import com.Capstone.InterviewTracking.enums.*;
import jakarta.persistence.*;

@Entity
@Table(name = "application")
public class Application extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private JobDescription job;

    @Enumerated(EnumType.STRING)
    private InterviewStage stage;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public Long getId() { return id; }

    public Candidate getCandidate() { return candidate; }
    public void setCandidate(Candidate candidate) { this.candidate = candidate; }

    public JobDescription getJob() { return job; }
    public void setJob(JobDescription job) { this.job = job; }

    public InterviewStage getStage() { return stage; }
    public void setStage(InterviewStage stage) { this.stage = stage; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
}