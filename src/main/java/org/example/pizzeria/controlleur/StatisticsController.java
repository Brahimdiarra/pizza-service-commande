package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.PizzaStatisticsDTO;
import org.example.dto.IngredientStatisticsDTO;
import org.example.service.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/stats")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/pizzas")
    public ResponseEntity<List<PizzaStatisticsDTO>> getPizzaStatistics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(statisticsService.getPizzaStatistics(from, to));
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<IngredientStatisticsDTO>> getIngredientStatistics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(statisticsService.getIngredientStatistics(from, to));
    }
}