package com.example.springcompanyemployeeexample.security;

import com.example.springcompanyemployeeexample.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getUserEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getUserRole().name()));
        this.user = user;
    }

    public User getUser(){
        return user;
    }
}
