package dk.clanie.eodhd.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dk.clanie.eodhd.dto.EodhdExchangeData;

class EodhdExchangeDataTest {

	@Test
	void testJsonDeserialization() throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<EodhdExchangeData> value = objectMapper.readValue("""
				[
				  {
				    "Name": "USA Stocks",
				    "Code": "US",
				    "OperatingMIC": "XNAS, XNYS, OTCM",
				    "Country": "USA",
				    "Currency": "USD",
				    "CountryISO2": "US",
				    "CountryISO3": "USA"
				  },
				  {
				    "Name": "London Exchange",
				    "Code": "LSE",
				    "OperatingMIC": "XLON",
				    "Country": "UK",
				    "Currency": "GBP",
				    "CountryISO2": "GB",
				    "CountryISO3": "GBR"
				  },
				  {
				    "Name": "Toronto Exchange",
				    "Code": "TO",
				    "OperatingMIC": "XTSE",
				    "Country": "Canada",
				    "Currency": "CAD",
				    "CountryISO2": "CA",
				    "CountryISO3": "CAN"
				  },
				  {
				    "Name": "TSX Venture Exchange",
				    "Code": "V",
				    "OperatingMIC": "XTSX",
				    "Country": "Canada",
				    "Currency": "CAD",
				    "CountryISO2": "CA",
				    "CountryISO3": "CAN"
				  }
				]				
				""", new TypeReference<List<EodhdExchangeData>>() {});
		EodhdExchangeData v1 = value.get(0);
		assertThat(v1.getName()).isEqualTo("USA Stocks");
		assertThat(v1.getCode()).isEqualTo("US");
		assertThat(v1.getOperatingMIC()).isEqualTo("XNAS, XNYS, OTCM");
		assertThat(v1.getCountry()).isEqualTo("USA");
		assertThat(v1.getCurrency()).isEqualTo("USD");
		assertThat(v1.getCountryISO2()).isEqualTo("US");
		assertThat(v1.getCountryISO3()).isEqualTo("USA");
	}

}
