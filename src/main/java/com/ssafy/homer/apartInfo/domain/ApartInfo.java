package com.ssafy.homer.apartInfo.domain;

import javax.persistence.*;
import java.util.*;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity(name="apart_info")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ApartInfo{
	/*
	 * aptId는 고유값 존재
	 */
	@Id
	private String aptId;
	
	private String aptName;
	
	private String type;
	
	private String lawAddr;
	
	private String roadAddr;
	
	private String postcode;
	
	private String allowDate;
	
	private Integer dongCount;
	
	private Integer householdCount;
	
	private String aisleType;
	
	private Integer maxFloor;
	 
	private Integer carCount;
	
	private Double lat;
	
	private Double lng;
	
	private Float parkPerHouse;

	//동코드
	@Column(columnDefinition = "char")
	private String entireCode;
	
	//아파트 거래 와 1:N
	@OneToMany(mappedBy = "apartInfo")
	private List<ApartDeal> apartDealList;
	//리뷰와 1:N
	
	//즐겨찾기와  1:N
}
