package ru.foxscribe.simplechat.service;

import lombok.AllArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.foxscribe.simplechat.model.User;
import ru.foxscribe.simplechat.repository.UserRepository;
import ru.foxscribe.simplechat.util.CustomUserDetails;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser = userRepository.findByUsername(username);

        return new CustomUserDetails(
                dbUser.getId(),
                dbUser.getUsername(),
                dbUser.getSecret(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
