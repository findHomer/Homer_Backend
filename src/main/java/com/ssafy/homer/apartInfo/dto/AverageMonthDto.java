package com.ssafy.homer.apartInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AverageMonthDto {
    private String month;

    private Float average;
}
