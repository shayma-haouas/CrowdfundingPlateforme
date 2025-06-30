package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Getter @Setter
@Entity
@Table(name = "utilisateurs")
public class Utilisateur implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String email;
    private String mdp;
    private Date dateInscription;
    @Enumerated(EnumType.STRING)
    private ROLE role;

    @OneToMany(mappedBy = "createur")
    @JsonIgnore
    private Set<Communaute> communautesCrees;

    @OneToMany(mappedBy = "auteur")
    @JsonIgnore
    private Set<Message> messages;

    @OneToMany(mappedBy = "porteur")
    @JsonIgnore
    private Set<Campagne> campagnesPortees;

    @OneToMany(mappedBy = "contributeur")
    @JsonIgnore
    private Set<Contribution> contributions;
}