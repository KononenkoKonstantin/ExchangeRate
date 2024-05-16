package org.example.exchangerate.service;

import java.time.LocalDate;
import org.example.exchangerate.dto.internal.CurrencyRateResponseDto;

public interface CurrencyRateService {

    CurrencyRateResponseDto getCurrencyRatesByDate(LocalDate date);

    void deleteAllExchangeRatesByDate(String date);
}
