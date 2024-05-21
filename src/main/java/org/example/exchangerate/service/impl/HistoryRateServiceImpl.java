package org.example.exchangerate.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.exchangerate.dto.internal.CurrencyRateDto;
import org.example.exchangerate.dto.internal.HistoryRatesResponseDto;
import org.example.exchangerate.mapper.CurrencyRateMapper;
import org.example.exchangerate.model.CurrencyRate;
import org.example.exchangerate.repository.CurrencyRateRepository;
import org.example.exchangerate.service.HistoryRateService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HistoryRateServiceImpl implements HistoryRateService {
    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencyRateMapper currencyRateMapper;

    @Override
    public HistoryRatesResponseDto getHistoryRatesByCode(String code) {
        List<CurrencyRate> currencyRates = currencyRateRepository.findAllByCurrencyCodeIgnoreCase(code);
//        Map<String, Map<LocalDate, BigDecimal>> currencyRateMap = currencyRates.stream()
//                .map(currencyRateMapper::mapToCurrencyRateDto)
//                .collect(Collectors.groupingBy(
//                        CurrencyRateDto::getCode,
//                        Collectors.toMap(CurrencyRateDto::getExchangeDate, CurrencyRateDto::getRate)));

        Map<String, Map<LocalDate, BigDecimal>> currencyRateMap = currencyRates.stream()
                .map(currencyRateMapper::mapToCurrencyRateDto)
                .collect(Collectors.groupingBy(
                        CurrencyRateDto::getCode,
                        Collectors.collectingAndThen(
                                Collectors.toMap(
                                        CurrencyRateDto::getExchangeDate,
                                        CurrencyRateDto::getRate,
                                        (existing, replacement) -> existing,
                                        HashMap::new
                                ),
                                map -> map.entrySet().stream()
                                        .sorted(Map.Entry.comparingByKey())
                                        .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                Map.Entry::getValue,
                                                (existing, replacement) -> existing,
                                                LinkedHashMap::new
                                        ))
                        )
                ));

        HistoryRatesResponseDto responseDto = new HistoryRatesResponseDto();
        responseDto.setRatesHistoryByCode(currencyRateMap);

        return responseDto;
    }
}
