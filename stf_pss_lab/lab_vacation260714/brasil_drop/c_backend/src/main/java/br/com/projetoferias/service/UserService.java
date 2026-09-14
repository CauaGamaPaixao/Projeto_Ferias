package br.com.projetoferias.service;

import br.com.projetoferias.model.UserAccount;
import br.com.projetoferias.repository.UserRepository;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final Pbkdf2PasswordEncoder encoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public UserService(UserRepository repository) {
        this.repository = repository;
        if (repository.findByEmail("demo@brasilmarket.com").isEmpty()) {
            register("Usuario Demo", "demo@brasilmarket.com", "123456");
        }
    }

    public boolean register(String name, String email, String password) {
        String normalizedEmail = normalize(email);
        if (name == null || name.isBlank() || name.length() > 200 || normalizedEmail.isBlank()
                || normalizedEmail.length() > 254 || !normalizedEmail.contains("@")
                || password == null || password.length() < 6 || password.length() > 200) {
            throw new IllegalArgumentException("Dados de cadastro inválidos.");
        }
        return repository.insert(new UserAccount(name.trim(), normalizedEmail, encoder.encode(password)));
    }

    public Optional<UserAccount> authenticate(String email, String password) {
        if (password == null || password.length() > 200) return Optional.empty();
        return repository.findByEmail(normalize(email)).filter(user -> encoder.matches(password, user.password()));
    }

    private String normalize(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }
}
