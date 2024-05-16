package org.example.exchangerate.dto.internal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HistoryRatesResponseDto {
    private Map<String, Map<LocalDate, BigDecimal>> ratesHistoryByCode;
}
