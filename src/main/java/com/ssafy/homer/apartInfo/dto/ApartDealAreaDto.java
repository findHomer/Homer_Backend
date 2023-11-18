package com.ssafy.homer.apartInfo.dto;

import antlr.collections.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 *  면적별 아파트 거래 정보
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApartDealAreaDto {

    private Float exclusiveArea;

    private ArrayList<ApartDealDto> apartDealDto;

    private ArrayList<AverageMonthDto> averageMonthDto;
}
