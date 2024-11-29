package com.example.myapp.model.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SponsorName {
    Байер("Байер"),
    HALEON_US_HOLDINGS("HALEON US HOLDINGS");
    @Getter
    private String sponsorName;
    @Override
    public String toString() {
        return sponsorName;
    }
}
