package com.example.myapp.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.model.TakeMedicine;
import com.example.myapp.repository.AccountRepository;
import com.example.myapp.repository.TakeMedicineRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
    
    @PutMapping
    public ResponseEntity<TakeMedicine> putTakes(@RequestBody TakeMedicine takeMedicine) {
        System.out.println(takeMedicine);
        return (takeMedicine.getId() != null && takeMedicineRepository.existsById(takeMedicine.getId()) ?
                        new ResponseEntity<>(takeMedicineRepository.save(takeMedicine), HttpStatus.OK) :
                        new ResponseEntity<>(takeMedicineRepository.save(takeMedicine), HttpStatus.CREATED));
    }

    @PostMapping
    public Iterable<TakeMedicine> postTakes(@RequestBody Iterable<TakeMedicine> takes) {
        System.out.println(takes);
        return takeMedicineRepository.saveAll(takes);
    }

    @DeleteMapping("/id={id}")
    public void deleteById(@PathVariable UUID id) {
        System.out.println(id);
        takeMedicineRepository.deleteById(id);
    }
}
