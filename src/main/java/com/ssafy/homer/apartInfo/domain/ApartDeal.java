package com.ssafy.homer.apartInfo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="apart_deal")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ApartDeal {

    @Id
    private Integer dealId;

    private String aptId;

    private Integer transactionAmount;

    private String transactionType;

    private Integer transactionYear;

    private Float exclusiveArea;

    private Integer floor;

    private Date transactionDate;


}
