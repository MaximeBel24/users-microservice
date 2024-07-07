package fr.doranco.users_service.repos;

import fr.doranco.users_service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    Optional<User> findByEmail(String email);
}
