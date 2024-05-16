package org.example.exchangerate.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "currency_rates")
@SQLDelete(sql = "UPDATE currency_rates SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class CurrencyRate {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;
    @Column(name = "rate", precision = 38, scale = 8, nullable = false)
    private BigDecimal rate;
    @Column(name = "exchange_date")
    private LocalDate exchangeDate;
    @Column(name = "date_of_loading")
    private LocalDateTime dateOfLoading;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public CurrencyRate(Long id, Currency currency, BigDecimal rate, LocalDate exchangeDate) {
        this.id = id;
        this.currency = currency;
        this.rate = rate;
        this.exchangeDate = exchangeDate;
    }
}
