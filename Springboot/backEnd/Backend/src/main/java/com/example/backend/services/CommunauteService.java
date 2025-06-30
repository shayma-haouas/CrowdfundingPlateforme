package com.example.backend.services;

import com.example.backend.entities.Communaute;
import com.example.backend.entities.Utilisateur;

import java.util.List;
import java.util.Set;

public interface CommunauteService {

    Communaute createCommunaute(Communaute communaute, Long createurId);

    Communaute getCommunauteById(Long communauteId);

    List<Communaute> getAllCommunautes();

    Communaute updateCommunaute(Long communauteId, Communaute communauteDetails);

    void deleteCommunaute(Long communauteId);

    Communaute addMembreToCommunaute(Long communauteId, Long utilisateurId);

    Communaute removeMembreFromCommunaute(Long communauteId, Long utilisateurId);

    Set<Utilisateur> getCommunauteMembres(Long communauteId);

}