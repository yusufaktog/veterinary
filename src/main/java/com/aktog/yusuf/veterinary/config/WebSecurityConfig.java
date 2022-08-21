package com.aktog.yusuf.veterinary.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class WebSecurityConfig   {

    private final Environment env;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public WebSecurityConfig(Environment env) {
        this.env = env;

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        String apiVersion = '/' + env.getProperty("api.version");
        String registerEndPoint = apiVersion + "/register";
        String loginEndPoint = apiVersion + "/login";

        httpSecurity
                .authorizeRequests()
                .antMatchers(registerEndPoint)
                .permitAll()
                .anyRequest()
                .authenticated();


        httpSecurity
                .formLogin()
                .loginPage(loginEndPoint)
                .loginProcessingUrl(loginEndPoint)
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl(apiVersion + "/owner", true)
                .failureUrl(apiVersion + "/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutUrl(apiVersion + "/logout")
                .logoutSuccessUrl(apiVersion + "/login?logout")
                .and()
                .httpBasic();

        return httpSecurity.build();
    }
}
