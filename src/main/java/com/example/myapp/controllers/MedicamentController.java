package com.example.myapp.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

import com.example.myapp.model.Medicament;
import com.example.myapp.repository.AccountRepository;
import com.example.myapp.repository.MedicamentRepository;

import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/medicaments")
@NoArgsConstructor
public class MedicamentController {
    @Autowired
    private MedicamentRepository medicamentRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    public MedicamentController(MedicamentRepository medicamentRepository) { }
    
    @GetMapping
    public
    Iterable<Medicament> getMedicaments()  {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        String userId = accountRepository.findByLogin(userLogin).get().getName();
        System.out.println(userId);
        Iterable<Medicament> medicaments = medicamentRepository.findAll();
        return medicaments;
    }

    @GetMapping("/id={id}")
    Optional<Medicament> getMedicament(@PathVariable UUID id) {
        return medicamentRepository.findById(id);
    }

    @PostMapping
    public Iterable<Medicament> postMedicaments(@RequestBody Iterable<Medicament> medicaments) {
        System.out.println(medicaments);
        Iterator<Medicament> it = medicaments.iterator();
        List<Medicament> sended = new ArrayList<>();
        while(it.hasNext()) {
            Medicament m = it.next();
            try {
                medicamentRepository.save(m);
                sended.add(m);
            }
            catch (Exception e) {
                System.out.println("увы");
            }
        }
        return sended;
    }

    @PutMapping("/id={id}")
    ResponseEntity<Medicament> putMedicament(@PathVariable UUID id, @RequestBody Medicament medicament) {
        return (medicamentRepository.existsById(id) ?
                        new ResponseEntity<>(medicamentRepository.save(medicament), HttpStatus.OK) :
                        new ResponseEntity<>(medicamentRepository.save(medicament), HttpStatus.CREATED));
    }

    @DeleteMapping("/id={id}")
    void deleteMedicament(@PathVariable UUID id) {
        medicamentRepository.deleteById(id);
    }

    @GetMapping("/title={title}")
    Set<Medicament> searchByTitle(@PathVariable String title) {
        Set<Medicament> response = medicamentRepository.findByTitle(title.trim());
        response.addAll(medicamentRepository.findByAI(title.trim().toLowerCase()));
        return response;
    }

    @PutMapping
    public ResponseEntity<Medicament> putMethodName(@RequestBody Medicament medicament) {
        return (medicament.getId() != null && medicamentRepository.existsById(medicament.getId()) ?
                        new ResponseEntity<>(medicamentRepository.save(medicament), HttpStatus.OK) :
                        new ResponseEntity<>(medicamentRepository.save(medicament), HttpStatus.CREATED));
    }
}
