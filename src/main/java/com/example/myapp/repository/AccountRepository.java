package com.example.myapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.example.myapp.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, UUID>, UserDetailsService{

    Optional<Account> findByLogin(String login);
    @Override
    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByLogin(username).get();
    }
}
