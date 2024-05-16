package org.example.exchangerate.mapper;

import org.example.exchangerate.dto.external.CurrencyExternalDto;
import org.example.exchangerate.dto.internal.CurrencyRateDto;
import org.example.exchangerate.model.Currency;
import org.example.exchangerate.model.CurrencyRate;

public interface CurrencyRateMapper {
    CurrencyRate mapToCurrencyRate(CurrencyExternalDto currencyExternalDto, Currency currency);

    CurrencyRateDto mapToCurrencyRateDto(CurrencyRate currencyRate);
}
