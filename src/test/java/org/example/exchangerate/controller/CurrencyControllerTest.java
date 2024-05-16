package org.example.exchangerate.controller;

import org.example.exchangerate.dto.internal.CurrencyRateResponseDto;
import org.example.exchangerate.service.CurrencyRateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {
    private static final String VALID_DATE = "2024-05-15";

    @Mock
    private CurrencyRateService currencyRateService;

    @InjectMocks
    private CurrencyController currencyController;


    @Test
    public void getCurrencyRates_SuccessfulResponse_Returns200Ok() {
        // Arrange
        CurrencyRateResponseDto mockResponseDto = new CurrencyRateResponseDto();
        when(currencyRateService.getCurrencyRatesByDate(any())).thenReturn(mockResponseDto);

        // Act
        ResponseEntity<CurrencyRateResponseDto> responseEntity = currencyController.getCurrencyRates(VALID_DATE);

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        verify(currencyRateService, times(1)).getCurrencyRatesByDate(any());
    }
}
