package com.arthurticianeli.petsfriends.applications.security;

import com.arthurticianeli.petsfriends.security.JwtFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Qualifier("corsConfigurationSource")
class SecurityConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void jwtFilterBeanIsCreated() {
        JwtFilter jwtFilter = context.getBean(JwtFilter.class);
        assertThat(jwtFilter).isNotNull();
    }

    @Test
    void passwordEncoderBeanIsInstanceOfBCryptPasswordEncoder() {
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        assertThat(passwordEncoder).isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    void securityFilterChainIsConfiguredCorrectly() throws Exception {
        SecurityFilterChain securityFilterChain = context.getBean(SecurityFilterChain.class);
        assertThat(securityFilterChain).isNotNull();
    }
}