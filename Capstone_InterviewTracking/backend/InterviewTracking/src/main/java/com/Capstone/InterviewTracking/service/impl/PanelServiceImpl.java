package com.Capstone.InterviewTracking.service.impl;

import com.Capstone.InterviewTracking.dto.PanelRequest;
import com.Capstone.InterviewTracking.entity.Panel;
import com.Capstone.InterviewTracking.entity.User;
import com.Capstone.InterviewTracking.enums.RoleType;
import com.Capstone.InterviewTracking.repository.PanelRepository;
import com.Capstone.InterviewTracking.repository.UserRepository;
import com.Capstone.InterviewTracking.service.PanelService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PanelServiceImpl implements PanelService {

    private final PanelRepository panelRepository;
    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;

    public PanelServiceImpl(PanelRepository panelRepository,
                            UserRepository userRepository,
                            EmailServiceImpl emailService) {
        this.panelRepository = panelRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public void createPanel(PanelRequest request) {

        if (panelRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Panel email already exists");
        }

        
        User user = new User();
        user.setEmail(request.getEmail());
        user.setRole(RoleType.PANEL);
        user.setVerified(false);

        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(15));

        userRepository.save(user);

     
        Panel panel = new Panel();
        panel.setFullName(request.getFullName());
        panel.setEmail(request.getEmail());
        panel.setPhone(request.getPhone());
        panel.setOrganization(request.getOrganization());
        panel.setDesignation(request.getDesignation());
        panel.setUser(user);

        panelRepository.save(panel);

       
        emailService.sendVerificationMail(request.getEmail(),request.getFullName(), token);
    }
}