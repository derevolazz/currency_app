package com.smorodinov.currency.db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "currency")
@NoArgsConstructor
@Getter
@Setter
public class CurrencyEntity {

    @Id
    @GeneratedValue(generator = "currency_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "currency_gen", sequenceName = "currency_sequence_id")
    @Column(name = "id")
    private Long id;

    @Column(name = "date", unique = true, nullable = false)
    private LocalDate dateTime;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "rates_id", nullable = false)
    private List<RateEntity> rates;

    public CurrencyEntity(LocalDate dateTime, List<RateEntity> rates) {
        this.dateTime = dateTime;
        this.rates = rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyEntity that = (CurrencyEntity) o;
        return dateTime.equals(that.dateTime) && rates.equals(that.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, rates);
    }


}
