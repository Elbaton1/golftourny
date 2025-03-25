package com.golfclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.golfclub.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // Search by name (case-insensitive, partial match)
    List<Member> findByNameContainingIgnoreCase(String name);

    // Search by phone (case-insensitive, partial match)
    List<Member> findByPhoneContainingIgnoreCase(String phone);

    // Search by membershipType (case-insensitive, partial match)
    List<Member> findByMembershipTypeContainingIgnoreCase(String membershipType);
}
