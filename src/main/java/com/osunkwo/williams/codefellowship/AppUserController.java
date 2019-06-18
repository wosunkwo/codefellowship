package com.osunkwo.williams.codefellowship;

import org.springframework.security.core.Authentication;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Security;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class AppUserController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public RedirectView loginUser(String username, String password){
     //   AppUser newUser = new AppUser(username, bCryptPasswordEncoder.encode(password), firstName, lastName, dateOfBirth, bio);
        AppUser ourUser = appUserRepository.findByUsername(username);
        if (ourUser != null){
            String passedPassword = bCryptPasswordEncoder.encode(password);
            if(passedPassword.equals(ourUser.getPassword())){
                return new RedirectView("/myprofile");
            }else{
                return new RedirectView("/login");
            }
        }else{
            return new RedirectView("/login");
        }
    }

    @PostMapping("/registration")
    public RedirectView createUser(String username, String password, String firstName, String lastName, Date dateOfBirth, String bio){
        AppUser newUser = new AppUser(username, bCryptPasswordEncoder.encode(password), firstName, lastName, dateOfBirth, bio);
        appUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/users");
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

}