package com.smorodinov.currency.controller;

import com.smorodinov.currency.dto.ConvertResult;
import com.smorodinov.currency.dto.RateDTO;
import com.smorodinov.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/currency")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping(path = "/converter")
    public ConvertResult convertCurrency(@RequestParam(name = "from")String currencyCodeFrom,
                                         @RequestParam(name = "to") String currencyCodeTo, @RequestParam double amount,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return currencyService.getConvertedCurrency(currencyCodeFrom, currencyCodeTo, amount, date);
    }

    @GetMapping(path = "/currentRates")
    public RateDTO getRatesByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                          LocalDate date) {
        return currencyService.getRatesByDate(date);
    }


}
