package com.project.LibraryManagementSystem100.SNAPSHOT._02_entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 225, nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String author;

    private Integer quantity;
}
