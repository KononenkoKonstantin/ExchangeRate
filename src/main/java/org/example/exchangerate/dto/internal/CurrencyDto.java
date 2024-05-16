package org.example.exchangerate.dto.internal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyDto {
    private String code;
    private String name;
}
