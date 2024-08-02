package com.ssafy.homer.apartInfo.dto;

import lombok.*;

/**
 * 동+이름(아파트)으로 검색 (필터포함)
 */
@Getter
@Builder
@AllArgsConstructor
public class SearchNameDto {

   
    private String name;

    private String dongCode;

    private Float parkPerHouse;

    private String aisleType;

    private Integer householdCount;
}
