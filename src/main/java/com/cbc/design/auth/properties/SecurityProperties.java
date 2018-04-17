package com.cbc.design.auth.properties;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.SecurityPrerequisite;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by cbc on 2018/3/27.
 */
@Data
@ConfigurationProperties(prefix = "security.properties")
public class SecurityProperties implements SecurityPrerequisite {

    private int rememberMeSeconds = 60*60*24*7;

    @Builder.Default
    private Boolean isAutoCreateRememberTable = false;
}
