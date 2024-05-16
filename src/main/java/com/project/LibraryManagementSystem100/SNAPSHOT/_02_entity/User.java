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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Fine fine;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Order order;

}
