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
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import lombok.Value;

/**
 * ID Mapping data from EODHD API.
 * <p>
 * Maps between exchange symbols and financial identifiers (CUSIP, ISIN, FIGI, LEI, CIK).
 */
@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EodhdIdMappingData {

	String symbol;
	String isin;
	String cusip;
	String figi;
	String compositeFigi;
	String shareClassFigi;
	String lei;
	String cik;


	@JsonCreator
	public EodhdIdMappingData(
			@JsonProperty("symbol") String symbol,
			@JsonProperty("isin") String isin,
			@JsonProperty("cusip") String cusip,
			@JsonProperty("figi") String figi,
			@JsonProperty("composite_figi") String compositeFigi,
			@JsonProperty("share_class_figi") String shareClassFigi,
			@JsonProperty("lei") String lei,
			@JsonProperty("cik") String cik) {
		this.symbol = symbol;
		this.isin = isin;
		this.cusip = cusip;
		this.figi = figi;
		this.compositeFigi = compositeFigi;
		this.shareClassFigi = shareClassFigi;
		this.lei = lei;
		this.cik = cik;
	}


}
