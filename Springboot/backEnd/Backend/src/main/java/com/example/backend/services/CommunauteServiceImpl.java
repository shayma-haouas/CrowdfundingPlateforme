package com.example.backend.services;

import com.example.backend.entities.Communaute;
import com.example.backend.entities.Utilisateur;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.CommunauteRepository;
import com.example.backend.repository.UtilisateurRepository;
import com.example.backend.services.CommunauteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunauteServiceImpl implements CommunauteService {

    private final CommunauteRepository communauteRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public Communaute createCommunaute(Communaute communaute, Long createurId) {
        Utilisateur createur = utilisateurRepository.findById(createurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'id: " + createurId));
        communaute.setCreateur(createur);
        return communauteRepository.save(communaute);
    }

    @Override
    @Transactional
    public Communaute getCommunauteById(Long communauteId) {
        return communauteRepository.findById(communauteId)
                .orElseThrow(() -> new ResourceNotFoundException("Communauté non trouvée avec l'id: " + communauteId));
    }

    @Override
    @Transactional
    public List<Communaute> getAllCommunautes() {
        return communauteRepository.findAll();
    }

    @Override
    public Communaute updateCommunaute(Long communauteId, Communaute communauteDetails) {
        Communaute communaute = getCommunauteById(communauteId);
        communaute.setNom(communauteDetails.getNom());
        communaute.setDescription(communauteDetails.getDescription());
        communaute.setImage(communauteDetails.getImage());
        return communauteRepository.save(communaute);
    }

    @Override
    public void deleteCommunaute(Long communauteId) {
        Communaute communaute = getCommunauteById(communauteId);
        communauteRepository.delete(communaute);
    }

    @Override
    public Communaute addMembreToCommunaute(Long communauteId, Long utilisateurId) {
        Communaute communaute = getCommunauteById(communauteId);
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'id: " + utilisateurId));
        communaute.getMembres().add(utilisateur);
        return communauteRepository.save(communaute);
    }

    @Override
    public Communaute removeMembreFromCommunaute(Long communauteId, Long utilisateurId) {
        Communaute communaute = getCommunauteById(communauteId);
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'id: " + utilisateurId));
        communaute.getMembres().remove(utilisateur);
        return communauteRepository.save(communaute);
    }

    @Override
    @Transactional
    public Set<Utilisateur> getCommunauteMembres(Long communauteId) {
        Communaute communaute = getCommunauteById(communauteId);
        return communaute.getMembres();
    }
}