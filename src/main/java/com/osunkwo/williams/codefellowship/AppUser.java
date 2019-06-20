package com.osunkwo.williams.codefellowship;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.*;

@Entity
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    public long getId() {
        return id;
    }

    @Column(unique = true)
    String username;

    String password;
    String firstName;
    String lastName;
    Date dateOfBirth;
    String bio;
    String imageUrl;

    @OneToMany(mappedBy = "creator")
    List<Post> posts;

    //this creates an associative relationship between a user being followed, and the user doing the following
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "following_followers",
            joinColumns = { @JoinColumn(name = "followee_id") },
            inverseJoinColumns = { @JoinColumn(name = "follower_id") }
    )
    Set<AppUser> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers")
    Set<AppUser> following = new HashSet<>();

    public AppUser(){}

    public String getImageUrl() {
        return imageUrl;
    }

    public Set<AppUser> getFollowers() {
        return followers;
    }

    public Set<AppUser> getFollowing() {
        return following;
    }

    public AppUser(String username, String password, String firstName, String lastName, Date dateOfBirth, String bio, String imageUrl){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBio() {
        return bio;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
