package org.example.exchangerate.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyExternalDto {
    @JsonProperty("r030")
    private String id;
    @JsonProperty("txt")
    private String name;
    @JsonProperty("rate")
    private String rate;
    @JsonProperty("cc")
    private String currencyCode;
    @JsonProperty("exchangedate")
    private String exchangeDate;

    public CurrencyExternalDto(String currencyCode, String rate) {
        this.currencyCode = currencyCode;
        this.rate = rate;
    }
}
