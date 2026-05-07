package com.Capstone.InterviewTracking.dto;

public class PanelResponse {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String organization;
    private String designation;

    public PanelResponse() {}

    public PanelResponse(Long id, String fullName, String email, String phone,
                         String organization, String designation) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.organization = organization;
        this.designation = designation;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
}
