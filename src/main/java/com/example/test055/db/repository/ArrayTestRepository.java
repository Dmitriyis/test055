package com.example.test055.db.repository;

import com.example.test055.db.entity.ArrayTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArrayTestRepository extends JpaRepository<ArrayTest, Long> {
    Optional<ArrayTest> findByName(String name);
    long deleteByName(String name);
}
