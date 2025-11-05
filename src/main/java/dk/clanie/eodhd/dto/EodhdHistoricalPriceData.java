package dk.clanie.eodhd.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EodhdHistoricalPriceData {

	LocalDate date;
	double open;
	double high;
	double low;
	double close;
	double adjustedClose;
	long volume;


	@JsonCreator
    public EodhdHistoricalPriceData(
        @JsonProperty("date") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate date,
        @JsonProperty("open") double open,
        @JsonProperty("high") double high,
        @JsonProperty("low") double low,
        @JsonProperty("close") double close,
        @JsonProperty("adjusted_close") double adjustedClose,
        @JsonProperty("volume") long volume) {
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.adjustedClose = adjustedClose;
		this.volume = volume;
	}


}
