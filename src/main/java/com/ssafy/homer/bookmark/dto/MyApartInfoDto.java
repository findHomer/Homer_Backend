package com.ssafy.homer.bookmark.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 북마크한
 * 아파트 정보를 보내주는 Dto
 *
 */
@Builder
@Getter
@Setter
@ToString
public class MyApartInfoDto {
	private String aptId;
	
	private String aptName;

	private String roadAddr;
	
	private Double lat;
	
	private Double lng;
	
}
