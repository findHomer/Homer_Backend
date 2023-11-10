package com.ssafy.homer.apartInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 아파트 전체정보 id,아파트명,위도경도 을 보내주는 DTO
 * @author IZERO
 *
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApartInfoDto {
	
	
	private String aptId;
	private String aptName;
	private Float latitude;
	private Float longitude;
}
