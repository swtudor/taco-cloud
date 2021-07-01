package com.example.tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity hamburglar) throws Exception {
        hamburglar
                .authorizeRequests()
                .antMatchers("/design", "/orders")
                .hasAnyAuthority("KETCHUP", "FRENCH_FRIES")
                .antMatchers("/", "/**").access("permitAll")
                .antMatchers("/console/**").access("permitAll")

                //login
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .defaultSuccessUrl("/design")
                .failureUrl("/login?error=true")
////
////                //       enableLogout[]
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .permitAll()

                .and()
                .httpBasic()

                .and()
                .csrf()
                .disable();

                hamburglar.headers().frameOptions().disable();

//                .ignoringAntMatchers("/h2-console/**")
//
//
//
//                .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder("53cr3t");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder())
                .and()
                .inMemoryAuthentication()
                .withUser("admin").password(encoder().encode("adminPass")).roles("KETCHUP");

    }

}