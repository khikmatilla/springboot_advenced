package com.myproject.springboot_advenced.config;

import com.myproject.springboot_advenced.entity.AuthUser;
import com.myproject.springboot_advenced.security.UserNotActivatedException;
import com.myproject.springboot_advenced.repository.AuthUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailService")
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository usersRepository;

    public CustomUserDetailsService(AuthUserRepository authUserRepository) {
        this.usersRepository = authUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String toLowerCase = username.toLowerCase();
        return usersRepository
                .findByUserName(toLowerCase)
                .map(user -> createSpringSecurityUser(toLowerCase, user))
                .orElseThrow(()-> new UserNotActivatedException("User " + toLowerCase +" not activated"));
    }

    private User createSpringSecurityUser(String userName, AuthUser user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + userName +" not activated");
        }
        List<GrantedAuthority>grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new User(userName, user.getPassword(), grantedAuthorities);
    }
}