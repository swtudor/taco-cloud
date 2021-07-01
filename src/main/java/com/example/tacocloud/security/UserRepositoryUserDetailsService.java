package com.example.tacocloud.security;

import com.example.tacocloud.data.UserRepository;
import com.example.tacocloud.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserRepositoryUserDetailsService
        implements UserDetailsService {

    private UserRepository userRepo;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
                Optional<User> user = userRepo.findByUsername(username);
                user.orElseThrow( () -> new UsernameNotFoundException("User " + username + "not found"));
                return user.map(MyUserDetails::new).get();
        }
}