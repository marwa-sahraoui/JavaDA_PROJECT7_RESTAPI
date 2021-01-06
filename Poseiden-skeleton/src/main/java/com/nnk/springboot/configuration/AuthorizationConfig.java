package com.nnk.springboot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
public class AuthorizationConfig extends WebSecurityConfigurerAdapter {
/*permet la configuration d'autorisation ainsi de définir les accés aux services REST de l'application
 par quel utilisateur ainsi que son rôle*/

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off

        http
                .csrf().disable().exceptionHandling()
                .and()
                .headers().frameOptions().deny()
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/app/**").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/app/login") //formulaire de la page login
                .loginProcessingUrl("/do-login")
                .failureUrl("/app/error"); //si user non identifiee


        // @formatter:on


    }
}
