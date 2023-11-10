package com.ssafy.homer.apartInfo.dto;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApartInfoDetailDto {
	@Id
	private String aptId;
	
	private String aptName;
	
	private String type;
	
	private String lawAddr;
	
	private String loadAddr;
	
	private String postcode;
	
	private String allowDate;
	
	private String dongCount;
	
	private Integer householdCount;
	
	private String aisleType;
	
	private Integer maxFloor;
	 
	private Integer carCount;
	
	private Float latitude;
	
	private Float longitude;
}
