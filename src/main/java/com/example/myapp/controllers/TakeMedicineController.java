package com.example.myapp.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.repository.AccountRepository;
import com.example.myapp.repository.TakeMedicineRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.model.TakeMedicine;


@RestController
@RequestMapping("/takeMedicine")
@NoArgsConstructor
@AllArgsConstructor
public class TakeMedicineController {
    @Autowired
    private TakeMedicineRepository takeMedicineRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public Iterable<TakeMedicine> getAllTakes() {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userId = accountRepository.findByLogin(userLogin).get().getId();
        return takeMedicineRepository.findByAccountId(userId);
    }

    @GetMapping("/cId={cId}")
    public Iterable<TakeMedicine> getTakesByCourseId(@PathVariable UUID cId) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userId = accountRepository.findByLogin(userLogin).get().getId();
        return takeMedicineRepository.findByCourseIdAndAccountId(cId, userId);
    }
    
}
