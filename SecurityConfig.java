package com.example.moviedb.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            HandlerMappingIntrospector introspector
    )throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(AbstractHttpConfigurer::disable);

        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        httpSecurity.authorizeHttpRequests((authorization) -> {
            authorization.requestMatchers(mvcMatcherBuilder.pattern("/actors"),
                    mvcMatcherBuilder.pattern("/actors/{id}")).hasRole("user");
            authorization.anyRequest().permitAll();
        });

        httpSecurity.formLogin((Customizer.withDefaults()));
        return httpSecurity.build();
    }
}
