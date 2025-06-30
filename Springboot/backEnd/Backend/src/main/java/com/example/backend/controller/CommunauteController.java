package com.example.backend.controller;

import com.example.backend.entities.Communaute;
import com.example.backend.entities.Utilisateur;
import com.example.backend.services.CommunauteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/communautes")
@RequiredArgsConstructor
public class CommunauteController {

    private final CommunauteService communauteService;

    @Operation(summary = "Créer une nouvelle communauté",
            description = "Crée une communauté avec un créateur spécifié par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Communauté créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping
    public ResponseEntity<Communaute> createCommunaute(
            @Parameter(description = "Objet Communauté à créer") @RequestBody Communaute communaute,
            @Parameter(description = "ID du créateur de la communauté") @RequestParam Long createurId) {
        Communaute nouvelleCommunaute = communauteService.createCommunaute(communaute, createurId);
        return new ResponseEntity<>(nouvelleCommunaute, HttpStatus.CREATED);
    }

    @Operation(summary = "Lister toutes les communautés",
            description = "Retourne la liste de toutes les communautés existantes.")
    @ApiResponse(responseCode = "200", description = "Liste des communautés récupérée avec succès")
    @GetMapping
    public List<Communaute> getAllCommunautes() {
        return communauteService.getAllCommunautes();
    }

    @Operation(summary = "Obtenir une communauté par son ID",
            description = "Retourne une communauté correspondant à l'ID fourni.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Communauté trouvée"),
            @ApiResponse(responseCode = "404", description = "Communauté non trouvée")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Communaute> getCommunauteById(
            @Parameter(description = "ID de la communauté") @PathVariable Long id) {
        return ResponseEntity.ok(communauteService.getCommunauteById(id));
    }

    @Operation(summary = "Mettre à jour une communauté",
            description = "Met à jour les détails d'une communauté existante identifiée par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Communauté mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Communauté non trouvée")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Communaute> updateCommunaute(
            @Parameter(description = "ID de la communauté à mettre à jour") @PathVariable Long id,
            @Parameter(description = "Détails mis à jour de la communauté") @RequestBody Communaute communauteDetails) {
        return ResponseEntity.ok(communauteService.updateCommunaute(id, communauteDetails));
    }

    @Operation(summary = "Supprimer une communauté",
            description = "Supprime une communauté identifiée par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Communauté supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Communauté non trouvée")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommunaute(
            @Parameter(description = "ID de la communauté à supprimer") @PathVariable Long id) {
        communauteService.deleteCommunaute(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Ajouter un membre à une communauté",
            description = "Ajoute un utilisateur comme membre à la communauté spécifiée.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Membre ajouté avec succès"),
            @ApiResponse(responseCode = "404", description = "Communauté ou utilisateur non trouvé")
    })
    @PostMapping("/{communauteId}/membres/{utilisateurId}")
    public ResponseEntity<Communaute> addMembre(
            @Parameter(description = "ID de la communauté") @PathVariable Long communauteId,
            @Parameter(description = "ID de l'utilisateur à ajouter") @PathVariable Long utilisateurId) {
        return ResponseEntity.ok(communauteService.addMembreToCommunaute(communauteId, utilisateurId));
    }

    @Operation(summary = "Retirer un membre d'une communauté",
            description = "Supprime un utilisateur des membres de la communauté spécifiée.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Membre retiré avec succès"),
            @ApiResponse(responseCode = "404", description = "Communauté ou utilisateur non trouvé")
    })
    @DeleteMapping("/{communauteId}/membres/{utilisateurId}")
    public ResponseEntity<Communaute> removeMembre(
            @Parameter(description = "ID de la communauté") @PathVariable Long communauteId,
            @Parameter(description = "ID de l'utilisateur à retirer") @PathVariable Long utilisateurId) {
        return ResponseEntity.ok(communauteService.removeMembreFromCommunaute(communauteId, utilisateurId));
    }

    @Operation(summary = "Lister les membres d'une communauté",
            description = "Retourne la liste des utilisateurs membres d'une communauté spécifiée.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des membres récupérée avec succès"),
            @ApiResponse(responseCode = "404", description = "Communauté non trouvée")
    })
    @GetMapping("/{communauteId}/membres")
    public ResponseEntity<Set<Utilisateur>> getMembres(
            @Parameter(description = "ID de la communauté") @PathVariable Long communauteId) {
        return ResponseEntity.ok(communauteService.getCommunauteMembres(communauteId));
    }
}
