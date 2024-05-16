package org.example.exchangerate.service.impl;

import java.time.LocalDate;
import java.util.List;
import org.example.exchangerate.mapper.CurrencyRateMapper;
import org.example.exchangerate.model.CurrencyRate;
import org.example.exchangerate.repository.CurrencyRateRepository;
import org.example.exchangerate.service.CurrencyExternalDataService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyRateServiceTest {

    @Mock
    private CurrencyExternalDataService currencyExternalDataService;
    @Mock
    private CurrencyRateRepository currencyRateRepository;
    @Mock
    private CurrencyRateMapper currencyRateMapper;
    @InjectMocks
    private CurrencyRateServiceImpl currencyRateService;

    @Test
    @DisplayName("When data exist external service should not be called")
    void when_dataExist_ShouldNotCallExternalService() {
        // Arrange
        CurrencyRate rate1 = new CurrencyRate();
        CurrencyRate rate2 = new CurrencyRate();
        LocalDate testLocalDate = LocalDate.now();
        when(currencyRateRepository.findAllByExchangeDate(testLocalDate)).thenReturn(List.of(rate1, rate2));

        // Act
        currencyRateService.getCurrencyRatesByDate(testLocalDate);

        // Assert
        verify(currencyRateRepository, times(1)).findAllByExchangeDate(testLocalDate);
        verify(currencyExternalDataService, times(0)).loadDataFromExternalApiByDate(testLocalDate);
    }

    @Test
    @DisplayName("When data don't exist external service should be called")
    void when_dataNotExist_ShouldCallExternalService() {
        // Arrange
        LocalDate testDate = LocalDate.of(2024, 5, 15);
        when(currencyRateRepository.findAllByExchangeDate(testDate)).thenReturn(List.of());

        // Act
        currencyRateService.getCurrencyRatesByDate(testDate);

        // Assert
        verify(currencyExternalDataService, times(1)).loadDataFromExternalApiByDate(testDate);
    }

    @Test
    @DisplayName("Verify deleteAllExchangeRatesByDate method deletes all exchange rates for the given date")
    void deleteAllExchangeRatesByDate_ShouldDeleteAllExchangeRatesForGivenDate() {
        // Arrange
        LocalDate testDate = LocalDate.of(2024, 5, 15);
        String testDateString = "2024-05-15";
        doNothing().when(currencyRateRepository).deleteAllByExchangeDate(testDate);

        // Act
        currencyRateService.deleteAllExchangeRatesByDate(testDateString);

        // Assert
        verify(currencyRateRepository, times(1)).deleteAllByExchangeDate(testDate);
    }
}
