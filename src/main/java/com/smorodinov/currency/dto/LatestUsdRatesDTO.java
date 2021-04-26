package com.smorodinov.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LatestUsdRatesDTO {
    HashMap<String, Double> rates;
}
