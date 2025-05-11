package edu.eci.cvds.auth.security;

import edu.eci.cvds.auth.models.User;
import edu.eci.cvds.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try to load by ID
        Optional<User> userOpt = userRepository.findById(username);
        
        // If not found, try by email
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmail(username);
        }
        
        User user = userOpt.orElseThrow(() -> 
                new UsernameNotFoundException("User not found with username or email: " + username));
        
        // Check if user is active
        if (!user.isActive()) {
            throw new UsernameNotFoundException("User account is inactive: " + username);
        }
        
        return new org.springframework.security.core.userdetails.User(
                user.getId(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
