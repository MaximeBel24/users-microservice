package fr.doranco.users_service.service;

import fr.doranco.users_service.entities.Role;
import fr.doranco.users_service.entities.User;
import fr.doranco.users_service.service.register.RegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername(String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String rolename);
    List<User> findAllUsers();

    User registerUser(RegistrationRequest request);

    public void sendEmailuser(User u, String code);
    public User validateToken(String code);
}
