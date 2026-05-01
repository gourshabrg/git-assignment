package com.Capstone.InterviewTracking.repository;

import com.Capstone.InterviewTracking.entity.Panel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PanelRepository extends JpaRepository<Panel, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Panel> findByEmail(String email);
}