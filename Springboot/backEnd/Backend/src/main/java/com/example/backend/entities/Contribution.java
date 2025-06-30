package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter @Setter
@Entity
@Table(name = "contributions")
public class Contribution implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campagne_id", nullable = false)
    @JsonIgnore
    private Campagne campagne;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contributeur_id", nullable = false)
    @JsonIgnore
    private Utilisateur contributeur;

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }
}