package com.cbc.design.auth.security;

import com.cbc.design.auth.properties.SecurityProperties;
import com.cbc.design.auth.repositories.UserRepository;
import com.cbc.design.auth.security.Filter.FaceAuthenticationFilter;
import com.cbc.design.auth.security.Filter.FaceAuthenticationProvider;
import com.cbc.design.common.Bean.Face;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.PrintWriter;

/**
 * Created by cbc on 2018/3/26.
 */
@Configuration
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {

    private final UserRepository userRepository;

    private final DataSource dataSource;

    private final SecurityProperties securityProperties;


    @Bean
    public UserDetailsService myUserDetailsService() {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public AuthenticationProvider faceAuthenticationProvider(){
        return new FaceAuthenticationProvider(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcTokenRepositoryImpl rememberMeTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        tokenRepository.setCreateTableOnStartup(securityProperties.getAutoCreateRememberTable());
        return tokenRepository;
    }

/*    @Bean
    public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint(){
        LoginUrlAuthenticationEntryPoint faceEntryPoint = new LoginUrlAuthenticationEntryPoint("/face/login");
        return faceEntryPoint;
    }*/


   /* @Configuration
    @Order(1)
    public class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        public App1ConfigurationAdapter() {
            super();
        }


        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/assets*", "/css*", "/image*", "/js*");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
           *//* http
                    .antMatcher("/admin*")
                    .authorizeRequests()
                    .anyRequest().hasRole("ADMIN")
                    // log in
                    .and()
                    .rememberMe()
                    .tokenRepository(rememberMeTokenRepository())
                    .tokenValiditySeconds(securityProperties.getRememberMeSeconds())
                    .and()
                    .formLogin().loginPage("/admin/login").permitAll()
                    .loginProcessingUrl("/userLogin")
                    //    .failureUrl("/loginAdmin?error=loginError")
                    .defaultSuccessUrl("/adminPage")
                    // logout
                    //  .and()
               *//**//*     .logout()
                    .logoutUrl("/admin_logout")
                    .logoutSuccessUrl("/protectedLinks")
                    .deleteCookies("JSESSIONID")
                    .and().exceptionHandling().accessDeniedPage("/403")*//**//*
                    .and().csrf().disable();*//*

        }
    }*/

    @Configuration
    @Order(2)
    public class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        public App2ConfigurationAdapter() {
            super();
        }


        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/assets*", "/css*", "/image*", "/js*", "/**/favicon.ico","/layui/**");
        }

   /*     @Bean
        @Override
        protected AuthenticationManager authenticationManager() throws Exception {
            return super.authenticationManager();
        }*/

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(myUserDetailsService()).passwordEncoder(passwordEncoder());
            auth.authenticationProvider(faceAuthenticationProvider());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/assets/**", "/css/**", "/image/**", "/js/**", "/webjars/**").permitAll()
                    .antMatchers("/error*").permitAll()
                    .antMatchers("/register*").permitAll()
                    .antMatchers("/user*").hasRole("USER")
                    .antMatchers("/api/register/**").permitAll()
                    .antMatchers("/","/home").permitAll()
                    .antMatchers("/face/login").permitAll()
                    .anyRequest().authenticated()
                    // log in
                    .and().anonymous()
                    .and().rememberMe()
                    .tokenRepository(rememberMeTokenRepository())
                    .tokenValiditySeconds(securityProperties.getRememberMeSeconds())
                    .and().formLogin().loginPage("/userLogin").permitAll()
                    .failureHandler((HttpServletRequest req, HttpServletResponse resp, AuthenticationException e)-> {
                            resp.setContentType("application/json;charset=utf-8");
                            PrintWriter out = resp.getWriter();
                            StringBuilder sb = new StringBuilder();
                            sb.append("{\"code\":\"401\",\"msg\":\"");
                            if(e instanceof UsernameNotFoundException){
                                sb.append(e.getMessage());
                            }else if(e instanceof  BadCredentialsException){
                                sb.append("您输入的密码有误，请重新输入！");
                            } else{
                                sb.append("登陆失败！");
                            }
                            sb.append("\"}");
                            out.print(sb.toString());
                            out.flush();
                            out.close();
                    })
                    .defaultSuccessUrl("/home")
                    // logout
                    .and().logout().logoutSuccessUrl("/userLogin").deleteCookies("JSESSIONID")
                    .and().sessionManagement().invalidSessionUrl("/userLogin")
                    .maximumSessions(1)  //最大session并发数量1
                    .maxSessionsPreventsLogin(false)
                    .and().and().csrf().disable();
            FaceAuthenticationFilter faceFilter = new FaceAuthenticationFilter();
            faceFilter.setAuthenticationManager(authenticationManager());
            faceFilter.setAuthenticationFailureHandler((HttpServletRequest req, HttpServletResponse resp, AuthenticationException e)-> {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                StringBuilder sb = new StringBuilder();
                sb.append("{\"code\":\"401\",\"msg\":\"");
                sb.append(e.getMessage());
                sb.append("\"}");
                out.print(sb.toString());
                out.flush();
                out.close();
            });
           // http.exceptionHandling().accessDeniedPage("/face/login").authenticationEntryPoint(loginUrlAuthenticationEntryPoint());
            http.addFilterBefore(faceFilter,UsernamePasswordAuthenticationFilter.class);

        }
    }


    //使用spring-data-security
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
