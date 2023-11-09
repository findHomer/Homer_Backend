package com.ssafy.homer.apartInfo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity(name="apart_info")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Apartment {
	/*
	 * aptId는 고유값 존재
	 */
	@Id
	private String aptId;
	
	private String aptName;
	
	private String type;
	
	private String lawAddr;
	
	private String loadAddr;
	
	private String postcode;
	
	private String allowDate;
	
	private String dongCount;
	
	private String householdCount;
	
	private String aisleType;
	
	private Integer maxFloor;
	 
	private Integer carCount;
	
	private Float latitude;
	
	private Float longitude;
	
	//아파트 거래 와 1:N
	
	//리뷰와 1:N
	
	//즐겨찾기와  1:N
}
