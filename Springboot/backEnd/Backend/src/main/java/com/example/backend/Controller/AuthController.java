package com.example.backend.Controller;


import com.example.backend.DTO.*;
import com.example.backend.Entities.User;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Service.UserActivityService;
import com.example.backend.Service.UserService;
import com.example.backend.ServiceAPI.EmailService;
import com.example.backend.ServiceAPI.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;  // Ajout de l'injection de PasswordEncoder
    //private final CaptchaService captchaService;
    private final UserActivityService userActivityService;


    public AuthController(UserService userService, UserRepository userRepository, EmailService emailService, JwtService jwtService, PasswordEncoder passwordEncoder,  UserActivityService userActivityService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
       // this.captchaService = captchaService;
        this.userActivityService = userActivityService;
    }

    // Endpoint pour l'inscription
    // Endpoint pour l'inscription
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest request) {
        // Validation des champs obligatoires
        if (request.getNom() == null || request.getPrenom() == null || request.getEmail() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Les champs obligatoires doivent être remplis."));
        }

        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        user.setRole(request.getRole());

        try {
            User registeredUser = userService.register(user);
            return ResponseEntity.ok(registeredUser);
        } catch (ResponseStatusException e) {
            // Capture l'erreur si l'email est déjà utilisé
            return ResponseEntity.status(e.getStatusCode()).body(Collections.singletonMap("error", e.getReason()));
        } catch (Exception e) {
            // Gestion d'erreur générique
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Une erreur interne est survenue."));
        }
    }




    // Endpoint pour la connexion
   /* @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

            // Renvoyer le token dans une réponse JSON
            return ResponseEntity.ok(Collections.singletonMap("token", token)); // Utilisation d'une map pour inclure le token dans une structure JSON

        } catch (ResponseStatusException e) {
            // Gérer l'erreur en capturant le message et en renvoyant une réponse appropriée
            return ResponseEntity.status(e.getStatusCode()).body(Collections.singletonMap("error", e.getReason()));
        } catch (Exception e) {
            // Capturer les erreurs générales
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Une erreur interne est survenue."));
        }
    }*/
    //USER ACTT
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            String token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

            // Récupérer l'utilisateur
            Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                //USER ACTT
                UserActivityLogDTO logDTO = new UserActivityLogDTO();
                logDTO.setSessionId("SID_" + System.currentTimeMillis());
                logDTO.setNetworkPacketSize(500 + (int) (Math.random() * 500)); // valeur simulée
                logDTO.setProtocolType("TCP");
                logDTO.setLoginAttempts(1);
                logDTO.setSessionDuration(Math.random() * 600);
                logDTO.setEncryptionUsed("TLS");
                logDTO.setIpReputationScore(Math.random());
                logDTO.setFailedLogins(0);
                logDTO.setBrowserType(request.getHeader("User-Agent"));
                logDTO.setUnusualTimeAccess(false);
                // logDTO.setAttackDetected(null);

                // Enregistrer l'activité
                userActivityService.logActivity(user.getId(), logDTO);
            }

            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Collections.singletonMap("error", e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Une erreur interne est survenue."));
        }
    }










    // Endpoint pour vérifier l'utilisateur via un lien contenant un token
    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String token) {
        try {
            userService.verifyUser(token); // Appel au service pour gérer la logique de vérification
            return ResponseEntity.ok("Votre e-mail a été vérifié avec succès !");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail(); // On récupère l'email de l'objet envoyé

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            emailService.sendPasswordResetEmail(user); // Envoi de l'email de réinitialisation
            return ResponseEntity.ok(Collections.singletonMap("message", "Un lien de réinitialisation a été envoyé à votre adresse email."));
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Aucun utilisateur trouvé avec cet email."));
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestBody PasswordResetRequest request) {
        String newPassword = request.getNewPassword();

        try {
            String email = jwtService.extractUsername(token); // Extraction de l'email du token
            Optional<User> userOptional = userRepository.findByEmail(email);

            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Utilisateur introuvable.");
            }

            User user = userOptional.get();

            // Vérifier si le token est expiré (ajoute cette méthode dans JwtService si nécessaire)
            if (jwtService.isTokenExpired(token)) {
                return ResponseEntity.badRequest().body("Le lien de réinitialisation du mot de passe a expiré.");
            }

            user.setPassword(passwordEncoder.encode(newPassword)); // Encodage du nouveau mot de passe
            userRepository.save(user); // Sauvegarde du mot de passe réinitialisé

            return ResponseEntity.ok("Votre mot de passe a été réinitialisé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Token invalide ou erreur interne.");
        }
    }



}
