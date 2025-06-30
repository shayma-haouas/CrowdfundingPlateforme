package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@Entity
public class Communaute implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Lob
    private String description;
    private Date dateCreation;
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createur_id", nullable = false)
    @JsonIgnore
    private Utilisateur createur;

    @OneToMany(mappedBy = "communaute", cascade = CascadeType.ALL, orphanRemoval = true)

    private Set<Message> messages = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "communaute_membres",
            joinColumns = @JoinColumn(name = "communaute_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id")
    )
    @JsonIgnore
    private Set<Utilisateur> membres = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        dateCreation = new Date();
    }
}