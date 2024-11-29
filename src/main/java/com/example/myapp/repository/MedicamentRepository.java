package com.example.myapp.repository;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.myapp.model.Medicament;


@Repository
public interface MedicamentRepository extends CrudRepository<Medicament,UUID> {
    Set<Medicament> findByTitle(String title);

    @Query(value="SELECT * FROM medicament WHERE lower(m_activeingredients::TEXT) ~ :ai;",
                nativeQuery=true)
    Set<Medicament> findByAI(String ai);
}
