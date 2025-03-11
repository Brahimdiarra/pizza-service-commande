package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Pizza;
import org.example.repository.PizzaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PizzaService {

    private final PizzaRepository pizzaRepository;

    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    public Optional<Pizza> findById(Long id) {
        return pizzaRepository.findById(id);
    }

    @Transactional
    public Pizza save(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @Transactional
    public void deleteById(Long id) {
        pizzaRepository.deleteById(id);
    }
}