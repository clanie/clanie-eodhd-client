# EODHD Client

EodhdClient is a Java client for accessing financial data from [eodhd.com](https://eodhd.com/), including:
- Historical end-of-day prices for stock exchanges worldwide
- Exchange listings and symbol information
- ID Mapping between exchange symbols and financial identifiers (CUSIP, ISIN, FIGI, LEI, CIK)

To use it, you need an EODHD account and an API token.

The client is meant to be used in Spring Boot applications, and will be auto-configured, if you provide the required configuration properties:

```
eodhd:
  apiToken: <your-api-token>
  url: https://eodhd.com/api
  wiretap: false
```

## Supported APIs

### Exchange Listings
Get a list of all supported exchanges or symbols for a specific exchange.

### Historical Price Data
Fetch end-of-day historical prices for any symbol on any supported exchange.

### ID Mapping API
Map between exchange symbols and financial identifiers bidirectionally:
- Symbol (format: SYMBOL.EXCHANGE) → identifiers (CUSIP, ISIN, FIGI, LEI, CIK)
- Identifier(s) → Symbol

The API uses filter syntax with pagination support. Multiple filters can be combined for more precise results.

**Example usage:**

```java
// Get all identifiers for Apple stock (symbol must include exchange code)
List<EodhdIdMappingData> data = client.idMapping(
    IdMappingQuery.builder()
        .symbol("AAPL.US")
        .build()
);

// Or use convenience method
List<EodhdIdMappingData> data = client.getIdentifiersForSymbol("AAPL.US");

// Find symbol by ISIN
List<EodhdIdMappingData> data = client.findSymbolByIsin("US0378331005");

// Combine multiple filters with custom pagination
List<EodhdIdMappingData> data = client.idMapping(
    IdMappingQuery.builder()
        .isin("US0378331005")
        .limit(50)
        .offset(0)
        .build()
);
```
