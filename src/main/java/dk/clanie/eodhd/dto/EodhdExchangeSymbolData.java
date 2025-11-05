/**
 * Copyright (C) 2025, Claus Nielsen, clausn999@gmail.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
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
