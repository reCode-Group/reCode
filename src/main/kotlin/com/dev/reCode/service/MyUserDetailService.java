package com.dev.reCode.service;

import com.dev.reCode.models.MyUserDetails;
import com.dev.reCode.models.User;
import com.dev.reCode.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetailService implements UserDetailsService {
     private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(MyUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
