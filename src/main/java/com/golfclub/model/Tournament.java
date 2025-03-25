package com.golfclub.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private Double entryFee;
    private Double cashPrize;
    
    @ManyToMany
    @JoinTable(
        name = "tournament_member",
        joinColumns = @JoinColumn(name = "tournament_id"),
        inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    @JsonManagedReference           
    private Set<Member> members = new HashSet<>();
    public Tournament() {
    }

    
    public Tournament(LocalDate startDate, LocalDate endDate, String location, 
                      Double entryFee, Double cashPrize) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.entryFee = entryFee;
        this.cashPrize = cashPrize;
    }


    public Long getId() { return id; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Double getEntryFee() { return entryFee; }
    public void setEntryFee(Double entryFee) { this.entryFee = entryFee; }
    public Double getCashPrize() { return cashPrize; }
    public void setCashPrize(Double cashPrize) { this.cashPrize = cashPrize; }
    public Set<Member> getMembers() { return members; }
    public void setMembers(Set<Member> members) { this.members = members; }
}
