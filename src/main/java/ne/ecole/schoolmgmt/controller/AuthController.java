package ne.ecole.schoolmgmt.controller;

import ne.ecole.schoolmgmt.dto.*;
import ne.ecole.schoolmgmt.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentification", description = "API d'authentification et gestion des utilisateurs")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Connexion utilisateur", description = "Authentifie un utilisateur et retourne un token JWT")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "Inscription utilisateur", description = "Crée un nouveau compte utilisateur")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody CreateUserRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    @Operation(summary = "Profil utilisateur", description = "Récupère les informations du profil de l'utilisateur connecté")
    public ResponseEntity<UserDto> getProfile(Authentication authentication) {
        UserDto user = authService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    @Operation(summary = "Mise à jour du profil", description = "Met à jour les informations du profil utilisateur")
    public ResponseEntity<UserDto> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateUserRequest request) {
        UserDto user = authService.updateProfile(authentication.getName(), request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/change-password")
    @Operation(summary = "Changement de mot de passe", description = "Change le mot de passe de l'utilisateur connecté")
    public ResponseEntity<Void> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(authentication.getName(), request);
        return ResponseEntity.ok().build();
    }
}

