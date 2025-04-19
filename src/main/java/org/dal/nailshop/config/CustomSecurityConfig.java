package org.dal.nailshop.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class CustomSecurityConfig {

//    private final JWTCheckFilter jwtCheckFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        log.info("------------------Security Config-----------------------");

        http.cors(cors -> {
            cors.configurationSource(corsConfigurationSource());
        });

        http.formLogin(config -> {
            config.disable();
        });

//        http.exceptionHandling(config -> {
//            config.accessDeniedHandler(new CustomAccessDeniedHandler());
//        });

        http.csrf(config -> {
            config.disable();
        });

        http.sessionManagement(config -> {
            config.sessionCreationPolicy(SessionCreationPolicy.NEVER);
        });

        // 아이디 패스워드 확인 전에
//        http.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CORS 설정 필요 - 최신 버전
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(List.of("*")); // 어디서든 허락
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }


}
