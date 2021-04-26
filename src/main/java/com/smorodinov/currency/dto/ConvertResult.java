package com.smorodinov.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConvertResult {
    String currencyCodeFrom;
    String currencyCodeTo;
    double amountToConvert;
    double resultOfConverting;
}
