package com.ssafy.homer.apartInfo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Date;

@Entity(name="apart_deal")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ApartDeal {

    @Id
    private Long dealId;

    private String transactionAmount;

    private String transactionType;

    private String constructionYear;

    private Float exclusiveArea;

    private Integer floor;

    private LocalDate transactionDate;

    @ManyToOne
    @JoinColumn(name = "apt_id")
    private ApartInfo apartInfo;
}
