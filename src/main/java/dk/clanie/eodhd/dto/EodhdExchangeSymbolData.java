package dk.clanie.eodhd.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Value;

/**
 * Data regarding one "symbol" (ticker) on an exchange.
 */
@Value
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class EodhdExchangeSymbolData {

    String code;
    String name;
    String country;
    String exchange;
    String currency;
    String type;
    String isin;

    @JsonCreator
    public EodhdExchangeSymbolData(
        @JsonProperty("Code") String code,
        @JsonProperty("Name") String name,
        @JsonProperty("Country") String country,
        @JsonProperty("Exchange") String exchange,
        @JsonProperty("Currency") String currency,
        @JsonProperty("Type") String type,
        @JsonProperty("Isin") String isin) {
        this.code = code;
        this.name = name;
        this.country = country;
        this.exchange = exchange;
        this.currency = currency;
        this.type = type;
        this.isin = isin;
    }


}
