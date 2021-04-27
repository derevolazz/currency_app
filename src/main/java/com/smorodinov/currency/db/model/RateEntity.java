package com.smorodinov.currency.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Table(name = "rate")
@NoArgsConstructor
@Setter
@Getter
public class RateEntity {
    public RateEntity(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    @Id
    @GeneratedValue(generator = "rate_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "rate_gen", sequenceName = "rate_sequence_id")
    @Column(name="id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "value")
    private BigDecimal value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateEntity that = (RateEntity) o;
        return name.equals(that.name) && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
