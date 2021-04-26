package com.smorodinov.currency.service;

import com.smorodinov.currency.dto.ConvertResult;
import com.smorodinov.currency.dto.RateDTO;

import java.time.LocalDate;

public interface CurrencyService {

    ConvertResult getConvertedCurrency(String currencyCodeFrom, String currencyCodeTo, double amount, LocalDate date);

    RateDTO getRatesByDate(LocalDate date);
}
