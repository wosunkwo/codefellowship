package com.osunkwo.williams.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class PostController {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/post/create")
    public String createPost(Principal p, Model m){
        AppUser currentUser = appUserRepository.findByUsername(p.getName());
        m.addAttribute("currentUser", currentUser);
        m.addAttribute("sessionStatus", true);
        return "createPost";
    }

    @PostMapping("/post/create")
    public RedirectView addPostToDatabase(String body, Principal p){
        Post post = new Post(body);
        AppUser currentUser = appUserRepository.findByUsername(p.getName());
        post.creator = currentUser;
        postRepository.save(post);
        return new RedirectView("/myprofile");
    }

}
