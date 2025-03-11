package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientStatisticsDTO {
    private Long ingredientId;
    private String ingredientName;
    private Long totalQuantity;
    private BigDecimal totalRevenue;
}