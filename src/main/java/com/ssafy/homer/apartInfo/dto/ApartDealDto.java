package com.ssafy.homer.apartInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * 아파트 거래정보
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApartDealDto {

    private Integer dealId;

    private Integer transactionAmount;

    private Date transactionDate;

    private Integer floor;

}
