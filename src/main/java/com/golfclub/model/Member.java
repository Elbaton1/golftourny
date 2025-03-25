package com.golfclub.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Auto-generated ID

    private String name;
    private String address;
    private String email;
    private String phone;
    
    // Membership type for search purposes (e.g., Gold, Silver)
    private String membershipType;
    
    private LocalDate startDate;  // Membership start date
    private Integer duration;     // Membership duration in months

    // Many-to-many relationship with Tournament
    @ManyToMany(mappedBy = "members")
    @JsonBackReference             // <-- prevents infinite recursion from the "child" side
    private Set<Tournament> tournaments = new HashSet<>();

    // Default constructor
    public Member() {
    }

    // Parameterized constructor
    public Member(String name, String address, String email, String phone, 
                  String membershipType, LocalDate startDate, Integer duration) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.membershipType = membershipType;
        this.startDate = startDate;
        this.duration = duration;
    }

    // Getters and setters
    public Long getId() { return id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    
    public Set<Tournament> getTournaments() { return tournaments; }
    public void setTournaments(Set<Tournament> tournaments) { this.tournaments = tournaments; }
}
