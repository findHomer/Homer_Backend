package com.ssafy.homer.apartInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 아파트 전체정보 id,아파트명,위도경도 을 보내주는 DTO
 * @author IZERO
 *
 */
@Getter
@AllArgsConstructor
@Builder
public class ApartInfoDto {
	
	private String aptId;
	private String aptName;
	private Double lat;
	private Double lng;
}
