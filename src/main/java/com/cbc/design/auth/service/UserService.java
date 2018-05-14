package com.cbc.design.auth.service;

import com.cbc.design.auth.domain.Role;
import com.cbc.design.auth.domain.User;
import com.cbc.design.auth.repositories.RoleRepository;
import com.cbc.design.auth.repositories.UserRepository;
import com.cbc.design.common.RepeatSubmit;
import com.cbc.design.common.ResultEnum;
import com.cbc.design.common.exception.RepeatSubmitException;
import com.cbc.design.common.exception.ValidationException;
import io.netty.util.internal.ConcurrentSet;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by cbc on 2018/3/28.
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private static final String EMAIL_RULE = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";

    private static final String PHONE_RULE = "^[1][3,4,5,7,8][0-9]{9}$";

    /**
     *  添加用户
     * @param user
     * @param token
     * @param register2_token
     * @param req
     */
    @Transactional
    public void addUser(User user, String token, String register2_token, HttpServletRequest req) {
        boolean isRepeatSubmit = RepeatSubmit.isRepeatSubmit(token, register2_token);
        if(isRepeatSubmit){
            throw new RepeatSubmitException(ResultEnum.REPEAT_SUBMIT);
        }else{
            req.getSession().removeAttribute("register2_token");
        }
        paramsCheck(user);

        user.init();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet(Arrays.asList(roleRepository.findByName("用户"))));
        userRepository.save(user);
    }

    /**
     *  校验注册参数
     * @param user
     */
    private void paramsCheck(User user){
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new ValidationException(ResultEnum.USERNAME_ALREADY_EXISTED);
        }
        if(userRepository.findByPhone(user.getPhone()).isPresent()){
            throw new ValidationException(ResultEnum.PHONE_ALREADY_EXISTED);
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new ValidationException(ResultEnum.EMAIL_ALREADY_EXISTED);
        }
        if(!Pattern.compile(PHONE_RULE).matcher(user.getPhone()).matches()){
            throw new ValidationException(ResultEnum.PHONE_NOT_ALLOWED);
        }
        if(!Pattern.compile(EMAIL_RULE).matcher(user.getEmail()).matches()){
            throw new ValidationException(ResultEnum.EMAIL_NOT_ALLOWED);
        }
    }
}
