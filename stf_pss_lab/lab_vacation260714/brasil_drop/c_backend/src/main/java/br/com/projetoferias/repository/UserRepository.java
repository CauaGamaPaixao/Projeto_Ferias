package br.com.projetoferias.repository;

import br.com.projetoferias.model.UserAccount;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;
    public UserRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public boolean insert(UserAccount user) {
        try {
            jdbc.update("INSERT INTO user_accounts (email, name, password_hash) VALUES (?, ?, ?)",
                    user.email(), user.name(), user.password());
            return true;
        } catch (DuplicateKeyException exception) {
            return false;
        }
    }

    public Optional<UserAccount> findByEmail(String email) {
        return jdbc.query("SELECT * FROM user_accounts WHERE email = ?",
                (row, index) -> new UserAccount(row.getString("name"), row.getString("email"),
                        row.getString("password_hash")), email).stream().findFirst();
    }
}
