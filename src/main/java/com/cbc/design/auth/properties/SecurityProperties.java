package com.cbc.design.auth.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by cbc on 2018/3/27.
 */
@Data
@Component
@ConfigurationProperties(prefix = "security.properties")
public class SecurityProperties{

    private int rememberMeSeconds= 60 * 60 * 24 * 7;

    private Boolean autoCreateRememberTable = false;


}
