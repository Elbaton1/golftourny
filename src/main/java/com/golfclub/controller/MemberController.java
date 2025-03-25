package com.golfclub.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.golfclub.model.Member;
import com.golfclub.model.Tournament;
import com.golfclub.repository.MemberRepository;
import com.golfclub.repository.TournamentRepository;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    // POST /members - Create a new member
    @PostMapping
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        Member savedMember = memberRepository.save(member);
        return ResponseEntity.ok(savedMember);
    }

    // GET /members - Get all members
    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // GET /members/{id} - Get a member by ID
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * GET /members/search
     * Search members by:
     *  - name: partial or full match (case-insensitive)
     *  - phone: partial or full match (case-insensitive)
     *  - membershipType: partial or full match (case-insensitive)
     *  - tournamentStartDate: exact match; returns members participating in tournaments that start on given date.
     */
    @GetMapping("/search")
    public List<Member> searchMembers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String membershipType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tournamentStartDate) {
        
        // Search by name
        if (name != null) {
            return memberRepository.findByNameContainingIgnoreCase(name);
        }
        // Search by phone
        if (phone != null) {
            return memberRepository.findByPhoneContainingIgnoreCase(phone);
        }
        // Search by membershipType
        if (membershipType != null) {
            return memberRepository.findByMembershipTypeContainingIgnoreCase(membershipType);
        }
        // Search by tournament start date
        if (tournamentStartDate != null) {
            List<Tournament> tournaments = tournamentRepository.findByStartDate(tournamentStartDate);
            // Collect all members from those tournaments
            return tournaments.stream()
                    .flatMap(t -> t.getMembers().stream())
                    .distinct()
                    .collect(Collectors.toList());
        }
        // If no search parameters, return all members
        return memberRepository.findAll();
    }
}
