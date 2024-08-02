package com.ssafy.homer.apartInfo.repository;


import java.time.LocalDate;
import java.util.Optional;
import com.ssafy.homer.apartInfo.domain.ApartDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApartDealRepository extends JpaRepository<ApartDeal,String>{
    public Optional<List<ApartDeal>> findByApartInfo_AptId(String aptId);

    @Query("SELECT ad FROM apart_deal ad WHERE ad.apartInfo.aptId = :aptId AND ad.transactionDate >= :dateLimit")
    public Optional<List<ApartDeal>> findRecentDealsByAptId(@Param("aptId") String aptId, @Param("dateLimit") LocalDate dateLimit);
}
