package org.example.exchangerate.service;

import org.example.exchangerate.dto.internal.HistoryRatesResponseDto;

public interface HistoryRateService {
    HistoryRatesResponseDto getHistoryRatesByCode(String code);
}
