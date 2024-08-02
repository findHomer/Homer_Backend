package com.ssafy.homer.apartInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

/**
 *  면적별 아파트 거래 정보
 */
@Getter
@AllArgsConstructor
@Builder
public class ApartDealAreaDto {

    private Float exclusiveArea;

    private ArrayList<ApartDealDto> apartDealDto;

    private ArrayList<AverageMonthDto> averageMonthDto;
}
