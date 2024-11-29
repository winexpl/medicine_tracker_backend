package com.example.myapp;

import org.springframework.context.annotation.Bean;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;


import jakarta.servlet.FilterChain;

@Component
public class WebSecurityConfig  {

	public static final String AUTHORITIES_CLAIM_NAME = "role";
    public static final String LOGIN_CLAIM_NAME = "login";
	
	private final PasswordEncoder passwordEncoder;

	public WebSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@SuppressWarnings("deprecation")
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(configurer ->
                        configurer
                                .requestMatchers(
                                        "/error",
                                        "/login",
                                        "/registration"
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                );
        // JWT Validation Configuration
		System.out.println("WebSecurityConfig::configure");
        http.oauth2ResourceServer(server -> server.jwt().jwtAuthenticationConverter(authenticationConverter()));
		return http.build();
    }


    protected JwtAuthenticationConverter authenticationConverter() {
        System.out.println("WebSecurityConfig::authenticationConverter");
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");
        authoritiesConverter.setAuthoritiesClaimName(AUTHORITIES_CLAIM_NAME);

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }
}
