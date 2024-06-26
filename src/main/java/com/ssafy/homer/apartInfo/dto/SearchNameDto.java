package com.ssafy.homer.apartInfo.dto;

import com.sun.istack.NotNull;
import lombok.*;

/**
 * 동+이름(아파트)으로 검색 (필터포함)
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class SearchNameDto {

   
    private String name;

    private String dongCode;

    private Float parkPerHouse;

    private String aisleType;

    private Integer householdCount;
}
