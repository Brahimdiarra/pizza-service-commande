package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PizzaStatisticsDTO {
    private Long pizzaId;
    private String pizzaName;
    private Long totalQuantity;
    private BigDecimal totalRevenue;
}