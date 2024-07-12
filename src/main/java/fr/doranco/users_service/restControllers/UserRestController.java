package fr.doranco.users_service.restControllers;

import fr.doranco.users_service.entities.User;
import fr.doranco.users_service.service.UserService;
import fr.doranco.users_service.service.register.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserRestController {

    @Autowired
    UserService userService;

    @RequestMapping(path = "all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/register")
    public User register(@RequestBody RegistrationRequest request) {
        return userService.registerUser(request);
    }

    @GetMapping("/verifyEmail/{token}")
    public User verifyEmail(@PathVariable("token") String token){

        System.out.print(" Token JWT in validate Token 1                     :" + token);

        return userService.validateToken(token);
    }
}
