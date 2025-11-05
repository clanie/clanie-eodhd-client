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
