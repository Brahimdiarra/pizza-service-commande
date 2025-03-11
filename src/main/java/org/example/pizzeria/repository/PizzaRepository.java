package org.example.repository;

import org.example.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire
}