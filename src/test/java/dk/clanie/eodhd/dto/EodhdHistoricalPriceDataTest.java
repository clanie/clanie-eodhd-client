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

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import tools.jackson.databind.ObjectMapper;

class EodhdHistoricalPriceDataTest {

	@Test
	void testDeserialization() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		EodhdHistoricalPriceData[] values = objectMapper.readValue("""
				[
				    {
				        "date": "2024-02-21",
				        "open": 280.5,
				        "high": 285.0,
				        "low": 278.3,
				        "close": 282.4,
				        "adjusted_close": 282.4,
				        "volume": 12345678
				    },
				    {
				        "date": "2024-02-22",
				        "open": 282.0,
				        "high": 286.5,
				        "low": 280.0,
				        "close": 285.2,
				        "adjusted_close": 285.2,
				        "volume": 98765432
				    }
				]
				""", EodhdHistoricalPriceData[].class);

		assertThat(values).hasSize(2);

		EodhdHistoricalPriceData value1 = values[0];
		assertThat(value1.getDate()).isEqualTo(LocalDate.of(2024, 2, 21));
		assertThat(value1.getOpen()).isEqualTo(280.5);
		assertThat(value1.getHigh()).isEqualTo(285.0);
		assertThat(value1.getLow()).isEqualTo(278.3);
		assertThat(value1.getClose()).isEqualTo(282.4);
		assertThat(value1.getAdjustedClose()).isEqualTo(282.4);
		assertThat(value1.getVolume()).isEqualTo(12345678);

		EodhdHistoricalPriceData value2 = values[1];
		assertThat(value2.getDate()).isEqualTo(LocalDate.of(2024, 2, 22));
		assertThat(value2.getOpen()).isEqualTo(282.0);
		assertThat(value2.getHigh()).isEqualTo(286.5);
		assertThat(value2.getLow()).isEqualTo(280.0);
		assertThat(value2.getClose()).isEqualTo(285.2);
		assertThat(value2.getAdjustedClose()).isEqualTo(285.2);
		assertThat(value2.getVolume()).isEqualTo(98765432);
	}

}
