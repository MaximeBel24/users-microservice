package com.moimeme.users_microservice.service;

import com.moimeme.users_microservice.entities.Role;
import com.moimeme.users_microservice.entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername(String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String rolename);
    List<User> findAllUsers();
}
