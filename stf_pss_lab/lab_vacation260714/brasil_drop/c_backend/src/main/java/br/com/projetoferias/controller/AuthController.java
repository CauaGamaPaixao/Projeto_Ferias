package br.com.projetoferias.controller;

import br.com.projetoferias.model.UserAccount;
import br.com.projetoferias.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> me(HttpSession session) {
        UserAccount user = (UserAccount) session.getAttribute("user");
        if (user == null) return ResponseEntity.ok(null);
        return ResponseEntity.ok(Map.of("name", user.name(), "email", user.email()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpSession session) {
        Optional<UserAccount> user = userService.authenticate(body.get("email"), body.get("password"));
        if (user.isEmpty()) return ResponseEntity.status(401).body(Map.of("error", "E-mail ou senha invalidos."));
        session.setAttribute("user", user.get());
        return ResponseEntity.ok(Map.of("name", user.get().name(), "email", user.get().email()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body, HttpSession session) {
        boolean created;
        try {
            created = userService.register(body.get("name"), body.get("email"), body.get("password"));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(Map.of("error", "Dados de cadastro invalidos."));
        }
        if (!created) return ResponseEntity.status(409).body(Map.of("error", "Ja existe uma conta com este e-mail."));
        UserAccount user = userService.authenticate(body.get("email"), body.get("password")).orElseThrow();
        session.setAttribute("user", user);
        return ResponseEntity.ok(Map.of("name", user.name(), "email", user.email()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }
}
