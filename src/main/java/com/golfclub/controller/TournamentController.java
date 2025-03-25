package com.golfclub.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private MemberRepository memberRepository;

    // POST /tournaments - Create a new tournament
    @PostMapping
    public ResponseEntity<Tournament> addTournament(@RequestBody Tournament tournament) {
        Tournament savedTournament = tournamentRepository.save(tournament);
        return ResponseEntity.ok(savedTournament);
    }

    // GET /tournaments - Get all tournaments
    @GetMapping
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    // GET /tournaments/{id} - Get a tournament by ID (with participating members)
    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournament(@PathVariable Long id) {
        Optional<Tournament> tournament = tournamentRepository.findById(id);
        return tournament.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /tournaments/{tournamentId}/addMember - Add a member to a tournament
    @PostMapping("/{tournamentId}/addMember")
    public ResponseEntity<String> addMemberToTournament(
            @PathVariable Long tournamentId,
            @RequestBody Long memberId) {
        Optional<Tournament> tournamentOpt = tournamentRepository.findById(tournamentId);
        Optional<Member> memberOpt = memberRepository.findById(memberId);

        if (tournamentOpt.isPresent() && memberOpt.isPresent()) {
            Tournament tournament = tournamentOpt.get();
            Member member = memberOpt.get();
            tournament.getMembers().add(member);
            tournamentRepository.save(tournament);
            return ResponseEntity.ok("Member added to Tournament successfully");
        }
        return ResponseEntity.notFound().build();
    }

    // GET /tournaments/search - Search tournaments by start date or location.
    @GetMapping("/search")
    public List<Tournament> searchTournaments(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) String location) {
        if (startDate != null) {
            return tournamentRepository.findByStartDate(startDate);
        }
        if (location != null) {
            return tournamentRepository.findByLocationContainingIgnoreCase(location);
        }
        return tournamentRepository.findAll();
    }
}
