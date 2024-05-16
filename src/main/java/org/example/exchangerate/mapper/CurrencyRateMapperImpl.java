package org.example.exchangerate.mapper;

import java.math.BigDecimal;
import org.example.exchangerate.dto.external.CurrencyExternalDto;
import org.example.exchangerate.dto.internal.CurrencyRateDto;
import org.example.exchangerate.model.Currency;
import org.example.exchangerate.model.CurrencyRate;
import org.example.exchangerate.util.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class CurrencyRateMapperImpl implements CurrencyRateMapper {

    public CurrencyRate mapToCurrencyRate(CurrencyExternalDto currencyExternalDto, Currency currency) {
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCurrency(currency);
        currencyRate.setRate(new BigDecimal(currencyExternalDto.getRate()));
        currencyRate.setExchangeDate(DateUtils.parseDate(currencyExternalDto.getExchangeDate()));
        return currencyRate;
    }

    public CurrencyRateDto mapToCurrencyRateDto(CurrencyRate currencyRate) {
        CurrencyRateDto currencyRateDto = new CurrencyRateDto();
        currencyRateDto.setName(currencyRate.getCurrency().getName());
        currencyRateDto.setCode(currencyRate.getCurrency().getCode());
        currencyRateDto.setRate(currencyRate.getRate());
        currencyRateDto.setExchangeDate(currencyRate.getExchangeDate());
        return currencyRateDto;
    }
}
