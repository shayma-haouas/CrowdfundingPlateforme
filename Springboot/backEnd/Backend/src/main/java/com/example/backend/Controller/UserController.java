package com.example.backend.Controller;


import com.example.backend.Entities.User;
import com.example.backend.Entities.UserActivityLog;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Service.UserActivityService;
import com.example.backend.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")  // Définir le préfixe de l'URL pour cette ressource
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserActivityService userActivityService;

    // Route pour récupérer tous les utilisateurs
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get")
    public List<User> getAllUsers() {
        return userService.getAllUsers();  // Appel de la méthode dans le service
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/accept/{userId}")
    public ResponseEntity<?> acceptUser(@PathVariable Long userId) {
        try {
            userService.acceptUser(userId);
            // Réponse JSON avec un message et un état de succès
            return ResponseEntity.ok(Map.of("message", "Utilisateur accepté avec succès", "success", true));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage(), "success", false));
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/block/{userId}")
    public ResponseEntity<?> blockUser(@PathVariable Long userId) {
        try {
            userService.blockUser(userId);
            // Retourner une réponse JSON directement
            return ResponseEntity.ok(Map.of("message", "Utilisateur bloqué avec succès", "success", true));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage(), "success", false));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN, PORTEUR, CONTRIBUTEUR')")
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String token) {
        User user = userService.getUserByToken(token);
        return ResponseEntity.ok(user);
    }



    @GetMapping("/activities")
    public ResponseEntity<?> getAllUserActivities() {
        try {
            List<UserActivityLog> activities = userActivityService.getAllActivities();
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Impossible de récupérer les activités."));
        }
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-user-id")
    public ResponseEntity<?> getUserIdByEmail(@RequestParam String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok(Collections.singletonMap("id", user.get().getId()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
