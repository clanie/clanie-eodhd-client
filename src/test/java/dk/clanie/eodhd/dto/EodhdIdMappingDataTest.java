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

import tools.jackson.core.JacksonException;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.ObjectMapper;

class EodhdIdMappingDataTest {

	@Test
	void testDeserialization() throws DatabindException, JacksonException {
		ObjectMapper objectMapper = new ObjectMapper();
		EodhdIdMappingData[] values = objectMapper.readValue("""
				[
				    {
				        "symbol": "AAPL.US",
				        "isin": "US0378331005",
				        "cusip": "037833100",
				        "figi": "BBG000B9XRY4",
				        "composite_figi": "BBG000B9XRY4",
				        "share_class_figi": "BBG001S5N8V8",
				        "lei": "HWUPKR0MPOU8FGXBT394",
				        "cik": "0000320193"
				    },
				    {
				        "symbol": "MSFT.US",
				        "isin": "US5949181045",
				        "cusip": "594918104",
				        "figi": "BBG000BPH459",
				        "composite_figi": null,
				        "share_class_figi": null,
				        "lei": "INR2EJN1ERAN0W5ZP974",
				        "cik": null
				    }
				]
				""", EodhdIdMappingData[].class);
		assertThat(values).hasSize(2);

		EodhdIdMappingData value1 = values[0];
		assertThat(value1.getSymbol()).isEqualTo("AAPL.US");
		assertThat(value1.getIsin()).isEqualTo("US0378331005");
		assertThat(value1.getCusip()).isEqualTo("037833100");
		assertThat(value1.getFigi()).isEqualTo("BBG000B9XRY4");
		assertThat(value1.getCompositeFigi()).isEqualTo("BBG000B9XRY4");
		assertThat(value1.getShareClassFigi()).isEqualTo("BBG001S5N8V8");
		assertThat(value1.getLei()).isEqualTo("HWUPKR0MPOU8FGXBT394");
		assertThat(value1.getCik()).isEqualTo("0000320193");

		EodhdIdMappingData value2 = values[1];
		assertThat(value2.getSymbol()).isEqualTo("MSFT.US");
		assertThat(value2.getIsin()).isEqualTo("US5949181045");
		assertThat(value2.getCusip()).isEqualTo("594918104");
		assertThat(value2.getFigi()).isEqualTo("BBG000BPH459");
		assertThat(value2.getCompositeFigi()).isNull();
		assertThat(value2.getShareClassFigi()).isNull();
		assertThat(value2.getLei()).isEqualTo("INR2EJN1ERAN0W5ZP974");
		assertThat(value2.getCik()).isNull();
	}


}
