package com.uncledemy.librarymanagementsystem.repository;

import com.uncledemy.librarymanagementsystem.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatronRepository extends JpaRepository<Patron, Long> {
    Optional<Patron> findByEmail(String email);
}
