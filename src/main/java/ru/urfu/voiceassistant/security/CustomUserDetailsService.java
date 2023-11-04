package ru.urfu.voiceassistant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.urfu.voiceassistant.model.User;
import ru.urfu.voiceassistant.model.role.Role;
import ru.urfu.voiceassistant.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User uniqueUser = userRepository.findByEmail(email);

        if (uniqueUser != null) {
            return new org.springframework.security.core.userdetails.User(
                    uniqueUser.getEmail(),
                    uniqueUser.getPassword(),
                    mapRolesToAuthorities(uniqueUser.getRoles()));
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    private Collection <? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getLogin()))
                .collect(Collectors.toList());
    }
}
