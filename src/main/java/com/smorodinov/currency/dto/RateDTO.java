package com.smorodinov.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class RateDTO {
    double usdRate;
    double eurRate;
}
