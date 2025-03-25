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

    @PostMapping
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        Member savedMember = memberRepository.save(member);
        return ResponseEntity.ok(savedMember);
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Member> searchMembers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String membershipType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tournamentStartDate) {
        
        if (name != null) {
            return memberRepository.findByNameContainingIgnoreCase(name);
        }
        if (phone != null) {
            return memberRepository.findByPhoneContainingIgnoreCase(phone);
        }
        if (membershipType != null) {
            return memberRepository.findByMembershipTypeContainingIgnoreCase(membershipType);
        }
        if (tournamentStartDate != null) {
            List<Tournament> tournaments = tournamentRepository.findByStartDate(tournamentStartDate);
            return tournaments.stream()
                    .flatMap(t -> t.getMembers().stream())
                    .distinct()
                    .collect(Collectors.toList());
        }
        return memberRepository.findAll();
    }
}
