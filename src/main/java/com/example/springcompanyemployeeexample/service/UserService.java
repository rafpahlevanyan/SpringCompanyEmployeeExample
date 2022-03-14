package com.example.springcompanyemployeeexample.service;

import com.example.springcompanyemployeeexample.entity.User;
import com.example.springcompanyemployeeexample.entity.UserRole;
import com.example.springcompanyemployeeexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;




    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       user.setUserRole(UserRole.USER);
        userRepository.save(user);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public User getById(int id) {
        return userRepository.getById(id);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }
}
