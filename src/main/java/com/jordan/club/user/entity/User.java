package com.jordan.club.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jordan.club.competition.entity.Competition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "competitions")
@ToString(exclude = "competitions")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @ManyToMany(mappedBy = "participants")
    @JsonIgnore
    @Builder.Default
    private Set<Competition> competitions = new HashSet<>();
}
