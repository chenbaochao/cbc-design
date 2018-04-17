package com.cbc.design.auth.security;

import com.cbc.design.auth.domain.User;
import com.cbc.design.auth.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

/**
 * Created by cbc on 2018/3/26.
 */

@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrPhone) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsernameOrPhone(usernameOrPhone);
        return user.map(u -> new CustomUserDetails(u))
                .orElseThrow(
                () -> new UsernameNotFoundException(String.format("没有提供用户名或者手机号为%s的用户信息!",usernameOrPhone))
        );
    }
}
