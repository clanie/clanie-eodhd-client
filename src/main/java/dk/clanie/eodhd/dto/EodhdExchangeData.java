/*
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

@Value
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class EodhdExchangeData {

	String name;
	String code;

	/**
	 * "Operating MIC" stands for Operating Market Identifier Code. This is an
	 * internationally recognized code, defined by ISO 10383, that uniquely
	 * identifies the specific trading venue or stock exchange where securities
	 * transactions occur.
	 */
	String operatingMIC;

	String country;
	String currency;

	String countryISO2;
	String countryISO3;


	@JsonCreator
	public EodhdExchangeData(
			@JsonProperty("Name") String name,
			@JsonProperty("Code") String code,
			@JsonProperty("OperatingMIC") String operatingMIC,
			@JsonProperty("Country") String country,
			@JsonProperty("Currency") String currency,
			@JsonProperty("CountryISO2") String countryISO2,
			@JsonProperty("CountryISO3") String countryISO3) {
		this.name = name;
		this.code = code;
		this.operatingMIC = operatingMIC;
		this.country = country;
		this.currency = currency;
		this.countryISO2 = countryISO2;
		this.countryISO3 = countryISO3;
	}


}
