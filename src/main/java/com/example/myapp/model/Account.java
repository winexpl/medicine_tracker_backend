package com.example.myapp.model;


import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.myapp.model.resources.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="account")
@AllArgsConstructor
@NoArgsConstructor
public class Account implements UserDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private UUID id;

    @Column(name = "a_password")
    private String password;

    @Column(name = "a_login")
    private String login;

    @Column(name = "a_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "a_role")
    private Role role;
    
    public void setRole(String role) {
        this.role = Role.valueOf(role);
    }

    public String getRole() {
        return role.toString();
    }

    
    @Override
    public Collection<Role> getAuthorities() {
        return List.of(role);
    }
    @Override
    public String getUsername() {
        return login;
    }
}