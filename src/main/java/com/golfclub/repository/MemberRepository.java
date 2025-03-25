package com.golfclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.golfclub.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByNameContainingIgnoreCase(String name);
    List<Member> findByPhoneContainingIgnoreCase(String phone);
    List<Member> findByMembershipTypeContainingIgnoreCase(String membershipType);
}
