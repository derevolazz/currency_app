package com.smorodinov.currency.config;

import com.smorodinov.currency.db.CurrencyRepositoryJPA;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.time.LocalDate;

@Configuration
public class DbConfig {

    @Bean
    public RestTemplate getDataSource(RestTemplateBuilder builder){
        return builder.build();
    }

    @PostConstruct
    public void init_db(RestTemplate restTemplate,
                        CurrencyRepositoryJPA repositoryJPA){

    }
}
