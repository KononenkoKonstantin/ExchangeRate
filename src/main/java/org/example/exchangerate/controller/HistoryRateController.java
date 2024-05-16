package org.example.exchangerate.controller;

import lombok.RequiredArgsConstructor;
import org.example.exchangerate.dto.internal.HistoryRatesResponseDto;
import org.example.exchangerate.service.HistoryRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/history-rates")
public class HistoryRateController {
    private final HistoryRateService historyRateService;

    @GetMapping("/{code}")
    ResponseEntity<HistoryRatesResponseDto> getCurrencyHistoryRates(@PathVariable String code) {
        return ResponseEntity.ok(historyRateService.getHistoryRatesByCode(code));
    }
}
