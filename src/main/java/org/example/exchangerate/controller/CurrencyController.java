package org.example.exchangerate.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.example.exchangerate.dto.internal.CurrencyRateResponseDto;
import org.example.exchangerate.service.CurrencyRateService;
import org.example.exchangerate.validation.DateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/currency-rates")
public class CurrencyController {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private final CurrencyRateService currencyRateService;

    @GetMapping()
    public ResponseEntity<CurrencyRateResponseDto> getCurrencyRates(
            @RequestParam(required = false) @Valid @DateFormat(pattern = DATE_PATTERN) String date) {

        logger.debug("Received request to get currency data");
        CurrencyRateResponseDto responseDto;
        responseDto = currencyRateService.getCurrencyRatesByDate(
                Objects.requireNonNullElseGet(LocalDate.parse(date), LocalDate::now));

        logger.debug("Returned currency data: {}", responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByDate(@RequestParam @Valid @DateFormat(pattern = "yyyy-MM-dd") String date) {
        currencyRateService.deleteAllExchangeRatesByDate(date);
        return ResponseEntity.noContent().build();
    }
}
