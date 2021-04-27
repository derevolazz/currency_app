package com.smorodinov.currency.service;

import com.smorodinov.currency.db.model.CurrencyEntity;
import com.smorodinov.currency.dto.ConvertResult;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CurrencyService {

    ConvertResult getConvertedCurrency(String currencyCodeFrom, String currencyCodeTo, BigDecimal amount, LocalDate date);

    CurrencyEntity getCurrencyByDate(LocalDate date);
}
