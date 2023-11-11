package com.ssafy.homer.apartInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *  면적별 아파트 거래 정보
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApartDealreaDto {

    private Float exclusiveArea;

    private ApartDealDto apartDealDto;


}
