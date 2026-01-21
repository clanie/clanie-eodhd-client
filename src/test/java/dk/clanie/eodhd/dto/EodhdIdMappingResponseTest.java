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

class EodhdIdMappingResponseTest {

	@Test
	void testDeserialization() throws DatabindException, JacksonException {
		ObjectMapper objectMapper = new ObjectMapper();
		EodhdIdMappingResponse response = objectMapper.readValue("""
				{
				    "meta": {
				        "limit": 100,
				        "offset": 0,
				        "total": 18
				    },
				    "data": [
				        {
				            "symbol": "0R2V.IL",
				            "isin": "US0378331005",
				            "figi": null,
				            "lei": "HWUPKR0MPOU8FGXBT394",
				            "cusip": null,
				            "cik": null
				        },
				        {
				            "symbol": "AAPL.US",
				            "isin": "US0378331005",
				            "figi": "BBG000B9XRY4",
				            "lei": "HWUPKR0MPOU8FGXBT394",
				            "cusip": "037833100",
				            "cik": "0000320193"
				        }
				    ]
				}
				""", EodhdIdMappingResponse.class);
		
		assertThat(response).isNotNull();
		assertThat(response.getMeta()).isNotNull();
		assertThat(response.getMeta().getLimit()).isEqualTo(100);
		assertThat(response.getMeta().getOffset()).isEqualTo(0);
		assertThat(response.getMeta().getTotal()).isEqualTo(18);
		
		assertThat(response.getData()).hasSize(2);
		
		EodhdIdMappingData data1 = response.getData().get(0);
		assertThat(data1.getSymbol()).isEqualTo("0R2V.IL");
		assertThat(data1.getIsin()).isEqualTo("US0378331005");
		assertThat(data1.getFigi()).isNull();
		assertThat(data1.getLei()).isEqualTo("HWUPKR0MPOU8FGXBT394");
		assertThat(data1.getCusip()).isNull();
		assertThat(data1.getCik()).isNull();
		
		EodhdIdMappingData data2 = response.getData().get(1);
		assertThat(data2.getSymbol()).isEqualTo("AAPL.US");
		assertThat(data2.getIsin()).isEqualTo("US0378331005");
		assertThat(data2.getFigi()).isEqualTo("BBG000B9XRY4");
		assertThat(data2.getLei()).isEqualTo("HWUPKR0MPOU8FGXBT394");
		assertThat(data2.getCusip()).isEqualTo("037833100");
		assertThat(data2.getCik()).isEqualTo("0000320193");
	}


}
