package org.example.repository;

import org.example.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire
}