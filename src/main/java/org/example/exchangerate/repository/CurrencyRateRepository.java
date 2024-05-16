package org.example.exchangerate.repository;

import java.time.LocalDate;
import java.util.List;
import org.example.exchangerate.model.CurrencyRate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

    @EntityGraph(attributePaths = "currency")
    List<CurrencyRate> findAllByExchangeDate(LocalDate date);

    void deleteAllByExchangeDate(LocalDate date);
}
