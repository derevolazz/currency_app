package com.smorodinov.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConvertResult {
    String currencyCodeFrom;
    String currencyCodeTo;
    BigDecimal amountToConvert;
    BigDecimal resultOfConverting;
}
