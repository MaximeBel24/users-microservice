package fr.doranco.users_service.repos;

import fr.doranco.users_service.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(String role);
}
