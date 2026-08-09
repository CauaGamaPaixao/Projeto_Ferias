package br.com.projetoferias.service;

import br.com.projetoferias.model.UserAccount;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final Map<String, UserAccount> users = new ConcurrentHashMap<>();

    public UserService() {
        register("Usuario Demo", "demo@brasilmarket.com", "123456");
    }

    public boolean register(String name, String email, String password) {
        String normalizedEmail = normalize(email);
        if (users.containsKey(normalizedEmail)) {
            return false;
        }
        users.put(normalizedEmail, new UserAccount(name, normalizedEmail, password));
        return true;
    }

    public Optional<UserAccount> authenticate(String email, String password) {
        return Optional.ofNullable(users.get(normalize(email)))
                .filter(user -> user.password().equals(password));
    }

    private String normalize(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }
}
