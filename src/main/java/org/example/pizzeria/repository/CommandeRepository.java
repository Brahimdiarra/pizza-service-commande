package org.example.repository;

import org.example.dto.IngredientStatisticsDTO;
import org.example.dto.PizzaStatisticsDTO;
import org.example.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserEmailOrderByOrderDateDesc(String email);

    @Query("SELECT new org.example.dto.PizzaStatisticsDTO(oi.pizza.id, oi.pizza.name, SUM(oi.quantity), SUM(oi.totalPrice)) " +
            "FROM commande_elems oi JOIN oi.order o " +
            "WHERE o.orderDate BETWEEN :from AND :to " +
            "GROUP BY oi.pizza.id, oi.pizza.name " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<PizzaStatisticsDTO> getPizzaStatistics(LocalDateTime from, LocalDateTime to);

    @Query("SELECT new org.example.dto.IngredientStatisticsDTO(i.id, i.name, COUNT(oi), SUM(i.additionalPrice)) " +
            "FROM commande_elems oi JOIN oi.additionalIngredients i JOIN oi.order o " +
            "WHERE o.orderDate BETWEEN :from AND :to " +
            "GROUP BY i.id, i.name " +
            "ORDER BY COUNT(oi) DESC")
    List<IngredientStatisticsDTO> getIngredientStatistics(LocalDateTime from, LocalDateTime to);
}