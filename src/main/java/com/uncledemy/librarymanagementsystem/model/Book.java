package com.uncledemy.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publicationYear;

    @Column(nullable = false, unique = true)
    private String isbn;
    @Column(name = "available_for_borrowing", nullable = false)
    private boolean availableForBorrowing;
}
