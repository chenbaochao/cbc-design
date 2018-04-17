package com.cbc.design.common;

import com.cbc.design.auth.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Optional;

/**
 * Created by cbc on 2018/4/4.
 */
public class SecurityUtil {

    public static Optional<User> getLoginUser(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof User){
            return Optional.of((User) principal);
        }
        return Optional.empty() ;
    }

}
