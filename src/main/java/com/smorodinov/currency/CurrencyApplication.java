package com.smorodinov.currency;

import com.smorodinov.currency.db.CurrencyRepositoryJPA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = CurrencyRepositoryJPA.class)
public class CurrencyApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurrencyApplication.class, args);
    }
}
