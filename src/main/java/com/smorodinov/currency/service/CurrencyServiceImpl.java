package com.smorodinov.currency.service;

import com.smorodinov.currency.dto.ConvertResult;
import com.smorodinov.currency.dto.LatestUsdRatesDTO;
import com.smorodinov.currency.dto.RateDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;


@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final RestTemplate restTemplate;
    private final String appId;
    private final String exchangeApiUrl;

    public CurrencyServiceImpl(RestTemplateBuilder builder, @Value("${openexchange.appid}") String appId,
                               @Value("${openexchange.url}") String exchangeApiUrl) {
        restTemplate = builder.build();
        this.appId = appId;
        this.exchangeApiUrl = exchangeApiUrl;
    }

    @Override
    public ConvertResult getConvertedCurrency(String currencyCodeFrom, String currencyCodeTo, double amount, LocalDate date) {

        ConvertResult convertResult = new ConvertResult();
        convertResult.setCurrencyCodeFrom(currencyCodeFrom);
        convertResult.setCurrencyCodeTo(currencyCodeTo);
        convertResult.setAmountToConvert(amount);
        convertResult.setResultOfConverting(convertCurrency(currencyCodeFrom, currencyCodeTo, amount, date));

        return convertResult;


    }

    @Override
    public RateDTO getRatesByDate(LocalDate date) {
        HashMap<String, Double> usdRatesByDate = getUsdRatesByDate(date);

        double rubRateToUsd = usdRatesByDate.get("RUB");
        double eurRateToUsd = usdRatesByDate.get("EUR");


        RateDTO rateDTO = new RateDTO();
        rateDTO.setEurRate(rubRateToUsd / eurRateToUsd);
        rateDTO.setUsdRate(rubRateToUsd);

        return rateDTO;
    }

    private double convertCurrency(String currencyCodeFrom, String currencyCodeTo, double amount, LocalDate date) {
        HashMap<String, Double> usdRatesByDate = getUsdRatesByDate(date);

        double currencyFromRateToUsd = usdRatesByDate.get(currencyCodeFrom);
        double currencyToRateToUsd = usdRatesByDate.get(currencyCodeTo);


        return (currencyFromRateToUsd / currencyToRateToUsd) * amount;

    }


    //Из-за того, что мы используем бесплатную версию API, этот метод возвращает только ставки к доллару
    private HashMap<String, Double> getUsdRatesByDate(LocalDate date) {

        LatestUsdRatesDTO latestUsdRatesDTO = restTemplate
                .getForObject(exchangeApiUrl + "/historical/" + date + ".json?app_id=" + appId, LatestUsdRatesDTO.class);

        return latestUsdRatesDTO.getRates();

    }

}
