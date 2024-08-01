package com.ssafy.homer.apartInfo.repository;


import java.util.Optional;
import com.ssafy.homer.apartInfo.domain.ApartDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApartDealRepository extends JpaRepository<ApartDeal,String>{
    public Optional<List<ApartDeal>> findByApartInfo_AptId(String aptId);
}
