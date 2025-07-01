package com.example.backend.Service;

import com.example.backend.Entities.User;
import com.example.backend.Repository.UserRepository;
import com.example.backend.ServiceAPI.EmailService;
import com.example.backend.ServiceAPI.JwtService;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
   // private final CloudinaryService cloudinaryService;



    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailService = emailService;


      //  this.cloudinaryService = cloudinaryService;
    }


    @Override
    public User register(User user) throws MessagingException, IOException, WriterException {
        // Vérification si l'email existe déjà
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un compte existe déjà avec cet email.");
        }

        // Vérification des champs obligatoires
        if (user.getNom() == null || user.getNom().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le nom est obligatoire.");
        }
        if (user.getPrenom() == null || user.getPrenom().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le prénom est obligatoire.");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'email est obligatoire.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le mot de passe est obligatoire.");
        }
        if (user.getRole() == null || user.getRole().describeConstable().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le rôle est obligatoire.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerified(false);
        user.setAccepted(false);


        User savedUser = userRepository.save(user);
        emailService.sendVerificationEmail(savedUser);

        return savedUser;
    }


    public String login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Vérification si l'utilisateur est accepté
            if (!user.isAccepted()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Votre compte est bloqué, veuillez attendre l'acceptation par l'administrateur.");
            }

            // Vérification si l'email est vérifié
            if (!user.isVerified()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Votre compte n'a pas encore été vérifié. Veuillez vérifier votre email.");
            }

            // Vérification du mot de passe
            if (passwordEncoder.matches(password, user.getPassword())) {
                return jwtService.generateToken(user); // Générer le token si tout est correct
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mot de passe incorrect.");
            }
        }

        // Si l'utilisateur n'est pas trouvé
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Identifiants incorrects.");
    }


    public void verifyUser(String token) {
        String email = jwtService.extractUsername(token);
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable.");
        }

        User user = userOptional.get();
        if (user.isVerified()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Compte déjà vérifié.");
        }

        user.setVerified(true);
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void acceptUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable.");
        }

        User user = userOptional.get();
        user.setAccepted(true); // L'utilisateur est maintenant accepté
        userRepository.save(user);
    }

    public void blockUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable.");
        }

        User user = userOptional.get();
        user.setAccepted(false); // Bloque l'utilisateur (ou met à jour le statut de l'utilisateur comme "bloqué")
        userRepository.save(user);
    }

    public User getUserByToken(String token) {
        String email = jwtService.extractUsername(token.replace("Bearer ", ""));
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
    }



    public User getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtService.extractUsername(token);
            return userRepository.findByEmail(email).orElse(null);
        }
        return null;
    }

}
