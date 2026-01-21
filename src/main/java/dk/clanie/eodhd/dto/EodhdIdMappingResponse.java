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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * Response wrapper for ID Mapping API.
 * <p>
 * The API returns: {"meta": {...}, "data": [...]}
 */
@Value
public class EodhdIdMappingResponse {

	Meta meta;
	List<EodhdIdMappingData> data;


	@JsonCreator
	public EodhdIdMappingResponse(
			@JsonProperty("meta") Meta meta,
			@JsonProperty("data") List<EodhdIdMappingData> data) {
		this.meta = meta;
		this.data = data;
	}


	/**
	 * Metadata about the response (pagination info).
	 */
	@Value
	public static class Meta {
		int limit;
		int offset;
		int total;

		@JsonCreator
		public Meta(
				@JsonProperty("limit") int limit,
				@JsonProperty("offset") int offset,
				@JsonProperty("total") int total) {
			this.limit = limit;
			this.offset = offset;
			this.total = total;
		}
	}


}
