package com.example.myapp.model.resources;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return toString();
    }
}
