package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Order;
import org.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class CommandeController {

    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Commande>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/me")
    public ResponseEntity<List<Commande>> getMyOrders(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(orderService.findByUserEmail(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getOrderById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return orderService.findById(id)
                .map(order -> {
                    // Check if the order belongs to the current user or if the user is an admin
                    if (order.getUser().getEmail().equals(userDetails.getUsername()) ||
                            userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                        return ResponseEntity.ok(order);
                    }
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Commande> createOrder(@Valid @RequestBody Order order, @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(orderService.createOrder(order, userDetails.getUsername()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Commande> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam Order.OrderStatus status) {
        return orderService.findById(id)
                .map(order -> ResponseEntity.ok(orderService.updateOrderStatus(id, status)))
                .orElse(ResponseEntity.notFound().build());
    }
}