package com.example.springcompanyemployeeexample.controller;

import com.example.springcompanyemployeeexample.entity.User;
import com.example.springcompanyemployeeexample.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MyControllerAdvice {

    @ModelAttribute
    public User user(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            return currentUser.getUser();
        }
        return null;
    }
}
