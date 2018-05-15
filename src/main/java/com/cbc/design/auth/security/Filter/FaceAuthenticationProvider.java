package com.cbc.design.auth.security.Filter;

import com.cbc.design.auth.domain.User;
import com.cbc.design.auth.repositories.UserRepository;
import com.cbc.design.auth.security.CustomUserDetails;
import com.cbc.design.common.BaiduExceptionMatch;
import com.cbc.design.common.Bean.Baidu;
import com.cbc.design.common.Bean.BaiduData;
import com.cbc.design.common.Bean.BaiduUser;
import com.cbc.design.common.FaceRecognitionUtil;
import com.cbc.design.common.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
public class FaceAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FaceAuthenticationToken authenticationToken = (FaceAuthenticationToken) authentication;
        Baidu baidu = FaceRecognitionUtil.faceRecognition((String) authenticationToken.getPrincipal());
        if(!("SUCCESS").equalsIgnoreCase(baidu.getError_msg())){
            throw new InternalAuthenticationServiceException(BaiduExceptionMatch.match(baidu.getError_msg()));
        }
        BaiduData baiduData = baidu.getResult();
        BaiduUser[] userList = baiduData.getUser_list();
        BaiduUser baiduUser = userList[0];
        Optional<User> userOptional = userRepository.findById(Long.valueOf(baiduUser.getUser_id()));
        if(!userOptional.isPresent()){
            throw new InternalAuthenticationServiceException("没有找到与你相关的信息,请先录入你的脸！");
        }
        User user = userOptional.get();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        FaceAuthenticationToken authenticationResult = new FaceAuthenticationToken(customUserDetails.getRoles(), customUserDetails);
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return FaceAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
