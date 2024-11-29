package com.example.myapp.authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.myapp.WebSecurityConfig;
import com.example.myapp.authentication.resource.LoginResult;
import com.example.myapp.model.Account;
import com.example.myapp.repository.AccountRepository;
import com.example.myapp.security.JwtHelper;

import lombok.AllArgsConstructor;


/**
 * The auth controller to handle login requests
 *
 * @author Masha
 */
@CrossOrigin(origins = {"${app.security.cors.origin}"})
@RestController
@AllArgsConstructor
public class AuthController {

    private final JwtHelper jwtHelper;
    
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AccountRepository userDetailsService;

    

    @PostMapping(path = "registration")
    public LoginResult registration(@RequestBody Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        System.out.println(account.getPassword().length());
        if(userDetailsService.findByLogin(account.getLogin()).isEmpty()) {
            userDetailsService.save(account);

            String username = account.getUsername();

            Map<String, String> claims = new HashMap<>();
            claims.put(WebSecurityConfig.LOGIN_CLAIM_NAME, username);
            String authorities = account.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
            claims.put(WebSecurityConfig.AUTHORITIES_CLAIM_NAME, authorities);
            String jwt = jwtHelper.createJwtForClaims(username, claims);
            System.out.println("token:" + jwt);
            return new LoginResult(jwt);
        } else {
            throw new ResponseStatusException(HttpStatus.FOUND, "User with that login already exists");
        }
    }
    
    @PostMapping(path = "login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public LoginResult login(@RequestParam String username, @RequestParam String password) {
        System.out.println("username: " + username + '\n' + "password: " + password);
        UserDetails userDetails;
        username=username.trim();
        password=password.trim();
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("POST login userDetails = " + userDetails);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            Map<String, String> claims = new HashMap<>();
            claims.put(WebSecurityConfig.LOGIN_CLAIM_NAME, username);
            String authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
            claims.put(WebSecurityConfig.AUTHORITIES_CLAIM_NAME, authorities);
            String jwt = jwtHelper.createJwtForClaims(username, claims);
            System.out.println("token:" + jwt);
            return new LoginResult(jwt);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }
}
