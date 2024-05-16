package org.example.exchangerate.service;

import java.time.LocalDate;
import java.util.List;
import org.example.exchangerate.dto.external.CurrencyExternalDto;

public interface CurrencyExternalDataService {
    List<CurrencyExternalDto> loadDataFromExternalApiByDate(LocalDate date);
}
