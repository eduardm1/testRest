package com.example.testrest.coin.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CoinHistoryDTO {

    private String symbol;
    private Double priceChange;
    private Double priceChangePercent;
}
