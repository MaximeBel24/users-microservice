package com.moimeme.users_microservice;

import com.moimeme.users_microservice.security.SecParams;
import com.moimeme.users_microservice.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UsersMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersMicroserviceApplication.class, args);
	}

	@Autowired
	UserService userService;


	@PostConstruct
	void init_users() {
		// ajouter les rôles
//		userService.addRole(new Role(null,"ADMIN"));
//		userService.addRole(new Role(null,"USER"));
//
//		//ajouter les users
//		userService.saveUser(new User(null,"admin","123",true,null));
//		userService.saveUser(new User(null,"maxime","123",true,null));
//		userService.saveUser(new User(null,"lucas","123",true,null));
//
//		userService.addRoleToUser("admin", "ADMIN");
//		userService.addRoleToUser("admin", "USER");
//		userService.addRoleToUser("maxime", "USER");
//		userService.addRoleToUser("lucas", "USER");
	}

}
