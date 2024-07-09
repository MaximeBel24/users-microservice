package fr.doranco.users_service.service;

import fr.doranco.users_service.entities.Role;
import fr.doranco.users_service.entities.User;
import fr.doranco.users_service.repos.RoleRepository;
import fr.doranco.users_service.repos.UserRepository;
import fr.doranco.users_service.service.exceptions.EmailAlreadyExistsException;
import fr.doranco.users_service.service.exceptions.ExpiredTokenException;
import fr.doranco.users_service.service.exceptions.InvalidTokenException;
import fr.doranco.users_service.service.register.RegistrationRequest;
import fr.doranco.users_service.service.register.VerificationToken;
import fr.doranco.users_service.service.register.VerificationTokenRepository;
import fr.doranco.users_service.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRep;

    @Autowired
    RoleRepository roleRep;

    @Autowired
    VerificationTokenRepository verificationTokenRepo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EmailSender emailSender;

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRep.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRep.findByUsername(username);
    }

    @Override
    public Role addRole(Role role) {
        return roleRep.save(role);
    }

    @Override
    public User addRoleToUser(String username, String rolename) {
        User usr = userRep.findByUsername(username);
        Role r = roleRep.findByRole(rolename);

        usr.getRoles().add(r);
        return usr;
    }

    @Override
    public List<User> findAllUsers() {
        return userRep.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> optionalUser = userRep.findByEmail(request.getEmail());
        if(optionalUser.isPresent())
            throw new EmailAlreadyExistsException("Email déja existant !");

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());

        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setEnabled(false);

        userRep.save(newUser);

        //ajouter à newUser le role par défaut USER
        Role r = roleRep.findByRole("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(r);
        newUser.setRoles(roles);

//        userRep.save(newUser);

        //génère le code secret
        String code = this.generateCode();

        VerificationToken token = new VerificationToken(code, newUser);
        System.out.print(" Code                         :" + code);
        System.out.print(" New User                     :" + newUser);

        verificationTokenRepo.save(token);
        System.out.print(" Token JWT                     :" + token.getToken());
        sendEmailuser(newUser, code);

        return userRep.save(newUser);
    }

    @Override
    public void sendEmailuser(User u, String code) {
        String emailBody = "Bonjour " + "<h1>" + u.getUsername() + "</h1>" +
                ". Votre code de validation est " + "<h1>" + code + "</h1>";
        System.out.print(" Username                     :" + u.getUsername());
        System.out.print(" Code                         :" + code);

        emailSender.sendEmail(u.getEmail(), emailBody);
    }

    @Override
    public User validateToken(String code) {
        VerificationToken token = verificationTokenRepo.findByToken(code);

        System.out.print(" Token JWT in validate Token                     :" + token);

        if(token == null){
            throw new InvalidTokenException("InvalidToken");
        }

        User user = token.getUser();

        System.out.print("User Token                     :" + user);

        Calendar calendar = Calendar.getInstance();
        System.out.print("Calendar                     :" + calendar);

        if((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenRepo.delete(token);
            throw new ExpiredTokenException("Expired Token");
        }
        user.setEnabled(true);
        userRep.save(user);
        return user;
    }

    public String generateCode() {
        Random random =new Random();
        int code = 100000 + random.nextInt(900000);

        return Integer.toString(code);
    }
}
