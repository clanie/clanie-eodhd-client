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

import java.net.URI;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.netty.http.client.HttpClient;

import io.micrometer.core.instrument.Metrics;

import dk.clanie.eodhd.dto.EodhdExchangeData;
import dk.clanie.eodhd.dto.EodhdExchangeSymbolData;
import dk.clanie.eodhd.dto.EodhdHistoricalPriceData;
import dk.clanie.eodhd.dto.EodhdIdMappingData;
import dk.clanie.eodhd.dto.EodhdIdMappingResponse;
import dk.clanie.web.WebClientFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class EodhdClient {

	private final WebClientFactory webClientFactory;

	@Value("${eodhd.url}")
	private String baseUrl;

	@Value("${eodhd.apiToken}")
	private String apiToken;

	@Value("${eodhd.wiretap:false}")
	private boolean wiretap;

	@Value("${eodhd.responseTimeout:PT2M}")
	private Duration responseTimeout;

	private WebClient wc;


	@PostConstruct
	public void init() {
		wc = webClientFactory.newWebClient(baseUrl, builder -> {
			HttpClient httpClient = HttpClient.create()
					.followRedirect(false)
					.wiretap(wiretap)
					.responseTimeout(responseTimeout);
			builder.clientConnector(new ReactorClientHttpConnector(httpClient));
			builder.filter(authorizationFilter());
			builder.filter(callCountingFilter());
		}, wiretap);
	}


	private static ExchangeFilterFunction callCountingFilter() {
		return ExchangeFilterFunction.ofRequestProcessor(cr -> {
			Metrics.globalRegistry.counter("eodhd.api.calls").increment();
			return Mono.just(cr);
		});
	}


	private ExchangeFilterFunction authorizationFilter() {
		return ExchangeFilterFunction.ofRequestProcessor(cr -> {
			URI originalUri = cr.url();
			URI newUri = UriComponentsBuilder.fromUri(originalUri)
					.queryParam("api_token", apiToken)
					.queryParam("fmt", "json").build(true) // true indicates the URI is already encoded
					.toUri();
			return Mono.just(ClientRequest.from(cr).url(newUri).build());
		});
	}


	public List<EodhdExchangeData> getExchanges() {
		return wc.get().uri("/exchanges-list/")
				.retrieve()
				.bodyToFlux(EodhdExchangeData.class)
				.collectList()
				.block();
	}


	/**
	 * Gets exchange symbols (aka tickers) for a given exchange.
	 * <p/>
	 * @param exchangeCode the code of the exchange
	 * @param delisted by default, this API provides only tickers that were active at least a
	 * 		month ago. To get the list of inactive (delisted) tickers please use the
	 * 		parameter  {@code delisted=true}. Notice that active listings are then NOT included.
	 * <p/>
	 * Arguments currently not supported:
	 * <dl>
	 * <dt>type</dt>
	 * <dd>filters the output by ticker type. Possible types: common_stock,
	 * preferred_stock, stock (both common and preferred combined), etf, fund. E.
	 * g.: adding &type=etf to your request will return all ETFs of the given
	 * exchange</dd>
	 * </dl>
	 */
	public List<EodhdExchangeSymbolData> getExchangeSymbols(String exchangeCode, boolean delisted) {
		return wc.get().uri(uriBuilder -> uriBuilder.path("/exchange-symbol-list/{exchangeCode}")
				.queryParam("delisted", delisted ? "1" : "0")
				.build(exchangeCode))
				.retrieve().bodyToFlux(EodhdExchangeSymbolData.class).collectList().block();
	}


	public List<EodhdExchangeSymbolData> getExchangeSymbols(String exchangeCode) {
		return getExchangeSymbols(exchangeCode, false);
	}


	/**
	 * Gets historical price data for a given symbol.
	 * <p/>
	 * Arguments accepted by the endpoint:
	 * <dl>
	 *   <dt>period</dt>
	 *   <dd>use ‘d’ for daily, ‘w’ for weekly, ‘m’ for monthly prices. By default, daily prices will be shown.
	 *  	Data aggregated weekly and monthly also shows weekly and monthly volume.</dd>
	 *   <dt>order</dt>
	 *   <dd>use ‘a’ for ascending dates (from old to new), ‘d’ for descending dates (from new to old).
	 *   	By default, dates are shown in ascending order.</dd>
	 *   <dt>from and to</dt>
	 *   <dd>the format is ‘YYYY-MM-DD’. If you need data from Jan 5, 2017, to Feb 10, 2017, you should use from=2017-01-05 and to=2017-02-10.</dd>
	 * </dl>
	 */
	public List<EodhdHistoricalPriceData> getHistoricalPriceData(String symbolCode, String exchangeCode, LocalDate from, LocalDate to) {
		return wc.get()
				.uri(uriBuilder -> uriBuilder.path("/eod/{symbol}.{exchangeCode}")
						.queryParam("from", from)
						.queryParam("to", to)
						.build(symbolCode, normalizedExchangeCode(exchangeCode)))
				.retrieve()
				.bodyToFlux(EodhdHistoricalPriceData.class)
				.collectList()
				.block();
	}


	/**
	 * ID Mapping API - maps between exchange symbols and financial identifiers.
	 * <p>
	 * Supports bidirectional mapping:
	 * <ul>
	 *   <li>Symbol (format: SYMBOL.EXCHANGE) → identifiers (CUSIP, ISIN, FIGI, LEI, CIK)</li>
	 *   <li>Identifier(s) → Symbol</li>
	 * </ul>
	 * <p>
	 * Multiple filters can be combined for more specific results.
	 * The API uses pagination with default limit of 100 results.
	 * <p>
	 * Example usage:
	 * <pre>
	 * // Get identifiers for Apple stock (note: symbol must include exchange)
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
	 * // Combine multiple filters with custom pagination
	 * List&lt;EodhdIdMappingData&gt; data = client.idMapping(
	 *     IdMappingQuery.builder()
	 *         .isin("US0378331005")
	 *         .limit(50)
	 *         .offset(0)
	 *         .build()
	 * );
	 * </pre>
	 * 
	 * @param query the ID mapping query with filter criteria
	 * @return list of matching ID mapping data (may be empty if no matches found)
	 */
	public List<EodhdIdMappingData> idMapping(IdMappingQuery query) {
		EodhdIdMappingResponse response = wc.get()
				.uri(uriBuilder -> {
					uriBuilder.path("/id-mapping");
					query.applyTo(uriBuilder);
					return uriBuilder.build();
				})
				.retrieve()
				.bodyToMono(EodhdIdMappingResponse.class)
				.block();
		return response != null ? response.getData() : List.of();
	}


	/**
	 * Convenience method to get all identifiers for a given symbol.
	 * <p>
	 * The symbol parameter can include the exchange code in the format: SYMBOL.EXCHANGE
	 * (e.g., "AAPL.US", "MSFT.US", "BP.LSE")
	 * 
	 * @param symbolWithExchange the symbol with optional exchange code (e.g., "AAPL.US")
	 * @return list of matching ID mapping data
	 */
	public List<EodhdIdMappingData> getIdentifiersForSymbol(String symbolWithExchange) {
		return idMapping(IdMappingQuery.builder()
				.symbol(symbolWithExchange)
				.build());
	}


	/**
	 * Convenience method to find exchange symbols by ISIN.
	 * 
	 * @param isin the ISIN identifier
	 * @return list of matching ID mapping data
	 */
	public List<EodhdIdMappingData> findSymbolByIsin(String isin) {
		return idMapping(IdMappingQuery.builder()
				.isin(isin)
				.build());
	}


	private String normalizedExchangeCode(String exchangeCode) {
		return switch (exchangeCode) {
		case  "AMEX", "ARCA", "BATS", "NASDAQ", "NYSE", "NYSE MKT","OTC", "PINK", "XASE", "XNAS", "XNYS"  -> "US";
		case "XLON" -> "LSE";
		case "XCSE" -> "CPH";
		default -> exchangeCode;
		};
	}


}
