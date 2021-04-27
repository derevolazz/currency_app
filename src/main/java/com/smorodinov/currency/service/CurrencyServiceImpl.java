package com.smorodinov.currency.service;

import com.smorodinov.currency.db.CurrencyRepositoryJPA;
import com.smorodinov.currency.db.model.CurrencyEntity;
import com.smorodinov.currency.db.model.RateEntity;
import com.smorodinov.currency.dto.ConvertResult;
import com.smorodinov.currency.dto.LatestUsdRatesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepositoryJPA repositoryJPA;
    private final RestTemplate restTemplate;
    private final String appId;
    private final String exchangeApiUrl;

    public CurrencyServiceImpl(CurrencyRepositoryJPA repositoryJPA,
                               RestTemplate restTemplate, @Value("${openexchange.appid}") String appId,
                               @Value("${openexchange.url}") String exchangeApiUrl) {
        this.repositoryJPA = repositoryJPA;
        this.restTemplate = restTemplate;
        this.appId = appId;
        this.exchangeApiUrl = exchangeApiUrl;
    }

    @Override
    public ConvertResult getConvertedCurrency(String currencyCodeFrom, String currencyCodeTo, BigDecimal amount, LocalDate date) {

        ConvertResult convertResult = new ConvertResult();
        convertResult.setCurrencyCodeFrom(currencyCodeFrom);
        convertResult.setCurrencyCodeTo(currencyCodeTo);
        convertResult.setAmountToConvert(amount);
        convertResult.setResultOfConverting(convertCurrency(currencyCodeFrom, currencyCodeTo, amount, date));

        return convertResult;


    }

    private BigDecimal convertCurrency(String currencyCodeFrom, String currencyCodeTo, BigDecimal amount, LocalDate date) {
        CurrencyEntity usdRatesByDate = getCurrencyByDate(date);

        HashMap<String, BigDecimal> rates = new HashMap<>();
        usdRatesByDate.getRates().forEach(
                rate -> {
                    if (rate.getName().equals(currencyCodeFrom)) {
                        rates.put(currencyCodeFrom, rate.getValue());
                    }
                    if (rate.getName().equals(currencyCodeTo)) {
                        rates.put(currencyCodeTo, rate.getValue());
                    }
                }
        );
        return rates.get(currencyCodeTo).divide(rates.get(currencyCodeFrom), 10, RoundingMode.HALF_UP).multiply(amount);
    }


    @Override
    public CurrencyEntity getCurrencyByDate(LocalDate date) {
        Optional<CurrencyEntity> currencyEntityByDateTime = repositoryJPA.findCurrencyEntityByDateTime(date);
        if (currencyEntityByDateTime.isEmpty()) {
            LatestUsdRatesDTO latestUsdRatesDTO = restTemplate
                    .getForObject(exchangeApiUrl + "/historical/" + date + ".json?app_id=" + appId, LatestUsdRatesDTO.class);
            ArrayList<RateEntity> rates = new ArrayList<>();
            latestUsdRatesDTO.getRates().forEach((name, value) -> {
                rates.add(new RateEntity(name, value));
            });
            return repositoryJPA.save(new CurrencyEntity(date, rates));
        } else {
            return currencyEntityByDateTime.get();
        }
    }

}
