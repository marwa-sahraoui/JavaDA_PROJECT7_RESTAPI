package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
/*permet la configuration d'authentification aussi à travers les requêtes permet d'identifier le user pour notre cas
  son nom ainsi que son role*/
@Configuration
public class AuthenticationConfig {

    @Autowired
    DataSource datasource;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(datasource)
                .usersByUsernameQuery("select username,password,true from Users where username = ?")
                .authoritiesByUsernameQuery("select username,role from Users where username = ?");
    }

    //permet de définir le composant qui permet le cryptage des MDP
    @Bean
    public PasswordEncoder bcrypt() {
        return new BCryptPasswordEncoder();
    }
}