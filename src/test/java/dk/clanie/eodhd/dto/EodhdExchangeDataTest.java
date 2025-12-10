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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
