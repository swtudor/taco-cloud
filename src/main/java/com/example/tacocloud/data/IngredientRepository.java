package com.example.tacocloud.data;

import com.example.tacocloud.domain.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);
}
