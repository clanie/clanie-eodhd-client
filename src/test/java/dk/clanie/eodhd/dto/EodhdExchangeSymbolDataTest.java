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

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class EodhdExchangeSymbolDataTest {

	@Test
	void testDeserialization() throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		EodhdExchangeSymbolData[] values = objectMapper.readValue("""
				[
				    {
				        "Code" : "CWIGAAKKKL",
				        "Name" : "C WorldWide Globale Aktier - Akkumulerende KL",
				        "Country" : "Denmark",
				        "Exchange" : "CO",
				        "Currency" : "DKK",
				        "Type" : "Common Stock",
				        "Isin" : "DK0060655702"
				    },
				    {
				        "Code" : "CWIGAEKL",
				        "Name" : "C WorldWide Globale Aktier Etik KL",
				        "Country" : "Denmark",
				        "Exchange" : "CO",
				        "Currency" : "DKK",
				        "Type" : "Common Stock",
				        "Isin" : null
				    }
				]
				""", EodhdExchangeSymbolData[].class);
		assertThat(values).hasSize(2);

		EodhdExchangeSymbolData value1 = values[0];
		assertThat(value1.getCode()).isEqualTo("CWIGAAKKKL");
		assertThat(value1.getName()).isEqualTo("C WorldWide Globale Aktier - Akkumulerende KL");
		assertThat(value1.getCountry()).isEqualTo("Denmark");
		assertThat(value1.getExchange()).isEqualTo("CO");
		assertThat(value1.getCurrency()).isEqualTo("DKK");
		assertThat(value1.getType()).isEqualTo("Common Stock");
		assertThat(value1.getIsin()).isEqualTo("DK0060655702");

		EodhdExchangeSymbolData value2 = values[1];
		assertThat(value2.getCode()).isEqualTo("CWIGAEKL");
		assertThat(value2.getName()).isEqualTo("C WorldWide Globale Aktier Etik KL");
		assertThat(value2.getCountry()).isEqualTo("Denmark");
		assertThat(value2.getExchange()).isEqualTo("CO");
		assertThat(value2.getCurrency()).isEqualTo("DKK");
		assertThat(value2.getType()).isEqualTo("Common Stock");
		assertThat(value2.getIsin()).isNull();
	}

}
