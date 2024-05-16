package org.example.exchangerate.service.impl;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.exchangerate.dto.external.CurrencyExternalDto;
import org.example.exchangerate.dto.internal.CurrencyRateResponseDto;
import org.example.exchangerate.mapper.CurrencyRateMapper;
import org.example.exchangerate.model.Currency;
import org.example.exchangerate.model.CurrencyRate;
import org.example.exchangerate.repository.CurrencyRateRepository;
import org.example.exchangerate.repository.CurrencyRepository;
import org.example.exchangerate.service.CurrencyExternalDataService;
import org.example.exchangerate.service.CurrencyRateService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@RequiredArgsConstructor
@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {
    private final CurrencyExternalDataService currencyExternalDataService;
    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyRateMapper currencyRateMapper;

    @Override
    @Transactional
    public CurrencyRateResponseDto getCurrencyRatesByDate(LocalDate date) {
        List<CurrencyRate> currencyRates = currencyRateRepository.findAllByExchangeDate(date);
        if (currencyRates.isEmpty()) {
            List<CurrencyExternalDto> currencyExternalDtos = currencyExternalDataService
                    .loadDataFromExternalApiByDate(date);
            saveCurrencyRates(currencyExternalDtos);
            currencyRates = currencyRateRepository.findAllByExchangeDate(date);
        }

        return createCurrencyRateResponse(currencyRates);
    }

    @Override
    @Transactional
    public void deleteAllExchangeRatesByDate(String date) {
        currencyRateRepository.deleteAllByExchangeDate(LocalDate.parse(date));
    }

    private CurrencyRateResponseDto createCurrencyRateResponse(List<CurrencyRate> currencyRates) {
        CurrencyRateResponseDto responseDto = new CurrencyRateResponseDto();

        responseDto.setDateOfLoading(currencyRates.stream().findFirst()
                .map(CurrencyRate::getDateOfLoading)
                .orElse(null));

        responseDto.setCurrencyRateDtos(currencyRates.stream()
                .map(currencyRateMapper::mapToCurrencyRateDto)
                .toList());

        return responseDto;
    }

    private void saveCurrencyRates(List<CurrencyExternalDto> currencyExternalDtos) {
        LocalDateTime dateOfLoading = LocalDateTime.now();

        List<CurrencyRate> currencyRates = currencyExternalDtos.stream()
                .map(dto -> {
                    Currency currency = getOrCreateCurrency(dto);
                    CurrencyRate currencyRate = currencyRateMapper.mapToCurrencyRate(dto, currency);
                    currencyRate.setCurrency(currency);
                    currencyRate.setDateOfLoading(dateOfLoading);
                    return currencyRate;
                })
                .toList();

        currencyRateRepository.saveAll(currencyRates);
    }

    private Currency getOrCreateCurrency(CurrencyExternalDto dto) {
        return currencyRepository.findByCode(dto.getCurrencyCode())
                .orElseGet(() -> {
                    Currency newCurrency = new Currency();
                    newCurrency.setCode(dto.getCurrencyCode());
                    newCurrency.setName(dto.getName());
                    return currencyRepository.save(newCurrency);
                });
    }
}
