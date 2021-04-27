package com.smorodinov.currency.db;

import com.smorodinov.currency.db.model.CurrencyEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CurrencyRepositoryJPA extends JpaRepository<CurrencyEntity, Long> {

   Optional<CurrencyEntity> findCurrencyEntityByDateTime(LocalDate dataTime);
}
