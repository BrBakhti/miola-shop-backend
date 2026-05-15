package org.cours.springdatarest.auth;

import org.cours.springdatarest.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (username.equals("admin") && password.equals("admin123")) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(
                    new AuthResponse(token, username, "Connexion reussie !", "ADMIN")
            );
        } else if (username.equals("user") && password.equals("user123")) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(
                    new AuthResponse(token, username, "Connexion reussie !", "USER")
            );
        }

        return ResponseEntity.status(401)
                .body(new AuthResponse(null, null, "Identifiants incorrects !", null));
    }
}