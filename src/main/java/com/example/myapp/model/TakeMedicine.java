package com.example.myapp.model;

import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="takemedicine")
@NoArgsConstructor
@AllArgsConstructor
public class TakeMedicine {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name="tm_id")
    private UUID id;
    
    @Column(name="c_id")
    private UUID courseId;

    @Column(name="tm_datetime")
    private Date datetime;

    @Column(name="tm_state")
    private boolean state;

}
