package com.dev.reCode.service;

import com.dev.reCode.models.MyUserDetails;
import com.dev.reCode.models.User;
import com.dev.reCode.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MyUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            System.out.println(user);
        }
        return user.map(MyUserDetails::new).orElseGet(() -> user.map(MyUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(email)));
    }
}
