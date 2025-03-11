package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.IngredientStatisticsDTO;
import org.example.dto.PizzaStatisticsDTO;
import org.example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final OrderRepository orderRepository;

    public List<PizzaStatisticsDTO> getPizzaStatistics(LocalDate from, LocalDate to) {
        LocalDateTime fromDateTime = from != null ? from.atStartOfDay() : LocalDate.now().minusMonths(1).atStartOfDay();
        LocalDateTime toDateTime = to != null ? to.atTime(LocalTime.MAX) : LocalDateTime.now();

        return orderRepository.getPizzaStatistics(fromDateTime, toDateTime);
    }

    public List<IngredientStatisticsDTO> getIngredientStatistics(LocalDate from, LocalDate to) {
        LocalDateTime fromDateTime = from != null ? from.atStartOfDay() : LocalDate.now().minusMonths(1).atStartOfDay();
        LocalDateTime toDateTime = to != null ? to.atTime(LocalTime.MAX) : LocalDateTime.now();

        return orderRepository.getIngredientStatistics(fromDateTime, toDateTime);
    }
}