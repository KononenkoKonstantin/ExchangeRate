package org.example.exchangerate.dto.internal;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRateDto {
    private String name;
    private String code;
    private BigDecimal rate;
    private LocalDate exchangeDate;
}
