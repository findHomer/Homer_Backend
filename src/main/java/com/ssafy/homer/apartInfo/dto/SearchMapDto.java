package com.ssafy.homer.apartInfo.dto;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SearchMapDto {
	
	/*
	 * 지도 위치 필수 포함!!
	 */
	@NotNull
	private Double startLat;
	@NotNull
	private Double endLat;
	@NotNull
	private Double startLng;
	@NotNull
	private Double endLng;

	private Float parkPerHouse;
	
	private String aisleType;
	
	private Integer householdCount;
	
}
