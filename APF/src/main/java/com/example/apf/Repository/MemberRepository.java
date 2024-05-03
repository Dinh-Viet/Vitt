package com.example.apf.Repository;

import com.example.apf.Entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members, String> {
    Optional<Members> findByUserId(String userId);
}
