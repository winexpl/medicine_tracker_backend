package com.example.myapp.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.example.myapp.model.resources.CourseState;
import com.example.myapp.model.resources.Regimen;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="course")
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @Column(name = "c_id")
    private UUID id;

    @Column(name="m_id")
    private UUID medicamentId;

    @Column(name="a_id")
    private UUID accountId;

    @Column(name="c_numbermedicine")
    private int numberMedicine; // число приемов
    
    @Column(name="c_startdate")
    private Date startDate;
    
    @Column(name="c_enddate")
    private Date endDate;
    
    @Column(name="c_typecourse")
    private int typeCourse;
    
    @Column(name="c_weekday")
    private int weekday; // битовая маска
    
    @Column(name="c_period")
    private int period;
    
    @Column(name="c_dose")
    private int dose;
    
    @Enumerated(EnumType.STRING)
    @Column(name="c_regimen")
    private Regimen regimen;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name="c_scheduleday")
    private List<Time> schedule;

    @Column(name="c_state")
    @Enumerated(EnumType.STRING)
    private CourseState state;

    public void setRegimen(String regimenString) {
        regimen = Regimen.valueOf(regimenString.replace(' ', '_'));
    }

    public String getRegimen() {
        return regimen.toString();
    }

    // @Override
    // public String toString() {
    //     return "id = " + id + "\nmedicamentId = " + medicamentId + "\naccountId = " + accountId
    //         + "\nnumberMedicine = " + numberMedicine + "\nstartDate = " + startDate + "\nendDate = " + endDate
    //         + "\ntypeCourse" + typeCourse + "\nweekday = " + weekday + "\nperiod = " + period + "\ndose = " + dose
    //         + "\nregimen" + regimen + "\nschedule = " + schedule + "\nstate = " + state.toString();
    // }
}
