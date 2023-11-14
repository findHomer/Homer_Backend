package com.ssafy.homer.apartInfo.dto;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class SearchMapDto {
	
	/*
	 * 지도 위치 필수 포함!!
	 */
	@NotNull
	private Float startLat;
	@NotNull
	private Float endLat;
	@NotNull
	private Float startLng;
	@NotNull
	private Float endLng;

	private Float parkPerHouse;
	
	private String aisleType;
	
	private Integer householdCount;
	
}
