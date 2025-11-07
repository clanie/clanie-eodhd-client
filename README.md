# EODHD Client

EodhdClient is a Java client for fetching historical end-of-day prices for stock exchanges around the world from [eodhd.com](https://eodhd.com/).

To use it, you need an EODHD account and an API token.

The client is meant to be used in Spring Boot applications, and will be auto-configured, if you provide the required configuration properties:

```
eodhd:
  apiToken: <your-api-token>
  url: https://eodhd.com/api
  wiretap: false
```
