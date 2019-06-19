package com.osunkwo.williams.codefellowship;

import org.springframework.security.core.Authentication;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.security.Security;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class AppUserController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String getHomePage(Principal p, Model m){
        boolean userStatus = isUserLoggedIn(p);
        m.addAttribute("userStatus", userStatus);
        return "home";
    }

    @GetMapping("/myprofile")
    public String getProfilePage(Principal p, Model m) {
        AppUser currentUser = appUserRepository.findByUsername(p.getName());
        m.addAttribute("currentUser", currentUser);
        m.addAttribute("sessionStatus", true);
        return "myprofile";
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable long id, Principal p, Model m){
        AppUser searchedUser = appUserRepository.findById(id).get();
        AppUser currentUser = appUserRepository.findByUsername(p.getName());
        boolean sessionStatus = isLoggedInUserTheSameAsSearchedUser(currentUser, searchedUser);

        m.addAttribute("currentUser", searchedUser);
        m.addAttribute("sessionStatus", sessionStatus);

        return "myprofile";
    }


    @GetMapping("/login")
    public String getLoginPage(Principal p, Model m)
    {
        boolean userStatus = isUserLoggedIn(p);
        m.addAttribute("userStatus", userStatus);
        return "login";
    }

    @PostMapping("/registration")
    public RedirectView createUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio) throws ParseException {
        Date dateOfBirthDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
        System.out.println("this is the first name: "+ firstName);
        AppUser newUser = new AppUser(username, bCryptPasswordEncoder.encode(password), firstName, lastName, dateOfBirthDateFormat, bio);
        appUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Principal p, Model m) {
        boolean userStatus = isUserLoggedIn(p);
        m.addAttribute("userStatus", userStatus);
        return "registration";
    }

    public Boolean isUserLoggedIn(Principal p){
        if(p != null)
            return true;
        else
            return false;
    }

    public Boolean isLoggedInUserTheSameAsSearchedUser(AppUser currentUser, AppUser targetUser) {
        if(currentUser.getUsername().equals(targetUser.getUsername()))
            return true;
        else
            return false;
    }
}
