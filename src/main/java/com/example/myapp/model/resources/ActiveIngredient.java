package com.example.myapp.model.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveIngredient {
    private String title;
    private int amount;
}