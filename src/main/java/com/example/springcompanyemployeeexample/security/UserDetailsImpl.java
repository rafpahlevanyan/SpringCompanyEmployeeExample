package com.example.springcompanyemployeeexample.security;

import com.example.springcompanyemployeeexample.entity.User;
import com.example.springcompanyemployeeexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUserEmail = userRepository.findByUserEmail(username);
        if (!byUserEmail.isPresent()){
            throw new UsernameNotFoundException("username " + username + " not founnd");
        }
        return new CurrentUser(byUserEmail.get());
    }
}
