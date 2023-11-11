package com.ssafy.homer.apartInfo.dto;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApartInfoDetailDto {

	private String aptId;
	
	private String aptName;

	private String aisleType;
	
	private String lawAddr;
	
	private String roadAddr;

	private String allowDate;//DATE
	
	private Integer dongCount;
	
	private Integer householdCount;

	private Integer maxFloor;
	
	private Float canPark;

	private Float latitude;
	
	private Float longitude;

	private List<ApartDealAreaDto> dealInfos;
}
