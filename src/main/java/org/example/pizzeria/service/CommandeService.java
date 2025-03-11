package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Commande;
import org.example.model.*;
import org.example.model.User;
import org.example.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandeService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public List<Commande> findAll() {
        return orderRepository.findAll();
    }

    public List<Commande> findByUserEmail(String email) {
        return orderRepository.findByUserEmailOrderByOrderDateDesc(email);
    }

    public Optional<Commande> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Commande createOrder(Commande order, String userEmail) {
        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        order.setUser(user);

        // Calculate total price
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CommandeElem item : order.getItems()) {
            item.setOrder(order);

            // Base price of the pizza
            BigDecimal itemPrice = item.getPizza().getBasePrice();

            // Add price of additional ingredients
            if (item.getAdditionalIngredients() != null) {
                for (var ingredient : item.getAdditionalIngredients()) {
                    itemPrice = itemPrice.add(ingredient.getAdditionalPrice());
                }
            }

            // Multiply by quantity
            item.setUnitPrice(itemPrice);
            item.setTotalPrice(itemPrice.multiply(new BigDecimal(item.getQuantity())));

            totalPrice = totalPrice.add(item.getTotalPrice());
        }

        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    @Transactional
    public Commande updateOrderStatus(Long id, Commande.OrderStatus status) {
        Commande order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        order.setStatus(status);
        return orderRepository.save(order);
    }
}