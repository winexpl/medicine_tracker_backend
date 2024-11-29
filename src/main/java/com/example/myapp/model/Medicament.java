package com.example.myapp.model;


import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.example.myapp.model.resources.ActiveIngredient;
import com.example.myapp.model.resources.DosageForm;
import com.example.myapp.model.resources.SponsorName;

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
@Table(name="medicament")
@AllArgsConstructor
@NoArgsConstructor
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_id")
    private UUID id;

    @Column(name = "m_title")
    private String title;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "m_dosageform")
    private DosageForm dosageForm;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "m_sponsorname")
    private SponsorName sponsorName;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "m_activeingredients", columnDefinition = "jsonb")
    private List<ActiveIngredient> activeIngredients;

    public void setSponsorName(String sponsorNameString) {
        sponsorName = SponsorName.valueOf(sponsorNameString.replace(' ', '_'));
    }

    public String getSponsorName() {
        return sponsorName.getSponsorName();
    }

    public void setSponsorName(SponsorName sponsorName) {
        this.sponsorName = sponsorName;
    }
}
