package com.project.LibraryManagementSystem100.SNAPSHOT._02_entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity(name = "Orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    User user;

    @ManyToOne(fetch = FetchType.EAGER)
    Book book;

    @Temporal(value = TemporalType.DATE)
    Date createdAt;
}
