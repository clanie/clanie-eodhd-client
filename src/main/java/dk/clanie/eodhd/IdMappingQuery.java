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
package dk.clanie.eodhd;

import static org.springframework.util.StringUtils.hasText;

import org.springframework.web.util.UriBuilder;

import lombok.Builder;
import lombok.Getter;

/**
 * Query parameters for ID Mapping API requests.
 * <p>
 * The API uses filter syntax: filter[field]=value
 * <p>
 * Supports multiple filter combinations:
 * <ul>
 *   <li>Symbol (with exchange) → get all identifiers (CUSIP, ISIN, FIGI, LEI, CIK)</li>
 *   <li>Identifier(s) → get exchange symbols</li>
 *   <li>Multiple identifiers can be combined for filtering</li>
 * </ul>
 * <p>
 * Example usage:
 * <pre>
 * // Get identifiers for a symbol (note: symbol must include exchange code)
 * List&lt;EodhdIdMappingData&gt; data = client.idMapping(
 *     IdMappingQuery.builder()
 *         .symbol("AAPL.US")
 *         .build()
 * );
 * 
 * // Find symbol by ISIN
 * List&lt;EodhdIdMappingData&gt; data = client.idMapping(
 *     IdMappingQuery.builder()
 *         .isin("US0378331005")
 *         .build()
 * );
 * 
 * // With pagination
 * List&lt;EodhdIdMappingData&gt; data = client.idMapping(
 *     IdMappingQuery.builder()
 *         .isin("US0378331005")
 *         .limit(100)
 *         .offset(0)
 *         .build()
 * );
 * </pre>
 */
@Getter
@Builder
public class IdMappingQuery {

	private String symbol;
	private String cusip;
	private String isin;
	private String figi;
	private String lei;
	private String cik;
	
	@Builder.Default
	private Integer limit = 100;
	
	private Integer offset;


	/**
	 * Applies the query parameters to the URI builder using filter[field] syntax.
	 */
	public void applyTo(UriBuilder uriBuilder) {
		if (hasText(symbol)) {
			uriBuilder.queryParam("filter[symbol]", symbol);
		}
		if (hasText(cusip)) {
			uriBuilder.queryParam("filter[cusip]", cusip);
		}
		if (hasText(isin)) {
			uriBuilder.queryParam("filter[isin]", isin);
		}
		if (hasText(figi)) {
			uriBuilder.queryParam("filter[figi]", figi);
		}
		if (hasText(lei)) {
			uriBuilder.queryParam("filter[lei]", lei);
		}
		if (hasText(cik)) {
			uriBuilder.queryParam("filter[cik]", cik);
		}
		if (limit != null) {
			uriBuilder.queryParam("page[limit]", limit);
		}
		if (offset != null) {
			uriBuilder.queryParam("page[offset]", offset);
		}
	}


}
