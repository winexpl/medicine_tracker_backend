package com.example.myapp.model.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Regimen {
    До_еды("До еды"), 
    После_еды("После еды"), 
    Во_время_еды("Во время еды"), 
    Независимо_от_приема_пищи("Независимо от приема пищи");
    @Getter
    private String ruRegimen;
    @Override
    public String toString() {
        return ruRegimen;
    }
    
}
