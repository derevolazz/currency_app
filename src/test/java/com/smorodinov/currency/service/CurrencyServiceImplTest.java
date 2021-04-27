package com.smorodinov.currency.service;

import com.smorodinov.currency.db.CurrencyRepositoryJPA;
import com.smorodinov.currency.db.model.CurrencyEntity;
import com.smorodinov.currency.db.model.RateEntity;
import com.smorodinov.currency.dto.ConvertResult;
import com.smorodinov.currency.dto.LatestUsdRatesDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CurrencyServiceImpl.class)
@ActiveProfiles("test")
class CurrencyServiceImplTest {

    @Autowired
    private CurrencyServiceImpl currencyService;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private CurrencyRepositoryJPA repositoryJPA;

    private String from = "RUB";
    private String to = "USD";
    private BigDecimal amount = BigDecimal.ONE;
    private LocalDate date = LocalDate.now();
    private LatestUsdRatesDTO dto = new LatestUsdRatesDTO(
            new HashMap<>() {{
                put(from, BigDecimal.ONE);
                put(to, BigDecimal.TEN);
            }}
    );
    private ConvertResult actualConvert = new ConvertResult(from, to, amount, BigDecimal.TEN.setScale(10, RoundingMode.HALF_UP));
    private CurrencyEntity actualCurrency = new CurrencyEntity(date, List.of(
            new RateEntity(from, BigDecimal.ONE),
            new RateEntity(to, BigDecimal.TEN)
    ));

    @Test
    @DisplayName("convert success")
    void test_1() {
        doReturn(Optional.empty()).when(repositoryJPA).findCurrencyEntityByDateTime(date);
        doReturn(dto).when(restTemplate).getForObject("test/historical/" + date + ".json?app_id=test", LatestUsdRatesDTO.class);
        doReturn(actualCurrency).when(repositoryJPA).save(any());

       ConvertResult expected = currencyService.getConvertedCurrency(from, to, amount, date);

        verify(repositoryJPA).findCurrencyEntityByDateTime(date);
        verify(restTemplate).getForObject("test/historical/" + date + ".json?app_id=test", LatestUsdRatesDTO.class);
        verify(repositoryJPA).save(any()
        );

        assertEquals(actualConvert, expected);
    }

    @Test
    @DisplayName("get currency success")
    void test_2() {
        doReturn(Optional.empty()).when(repositoryJPA).findCurrencyEntityByDateTime(date);
        doReturn(dto).when(restTemplate).getForObject("test/historical/" + date + ".json?app_id=test", LatestUsdRatesDTO.class);
        doReturn(actualCurrency).when(repositoryJPA).save(any());

        CurrencyEntity expected = currencyService.getCurrencyByDate(date);

        verify(repositoryJPA).findCurrencyEntityByDateTime(date);
        verify(restTemplate).getForObject("test/historical/" + date + ".json?app_id=test", LatestUsdRatesDTO.class);
        verify(repositoryJPA).save(any());

        assertEquals(actualCurrency, expected);
    }
}