package com.ssafy.homer.apartInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * 아파트 거래정보
 */
@Getter
@AllArgsConstructor
@Builder
public class ApartDealDto {

    private Long dealId;

    private String transactionAmount;

    private LocalDate transactionDate;

    private Integer floor;

}
