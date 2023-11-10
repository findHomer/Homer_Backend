package com.ssafy.homer.apartInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.homer.apartInfo.domain.ApartInfo;
import com.ssafy.homer.apartInfo.dto.ApartInfoDto;
@Repository
public interface ApartInfoRepository extends JpaRepository<ApartInfo,String>{
	
	/**
	 * 특정 필드만 뽑아내는 Projections 적용하기위해 JPQL 쿼리문 작성
	 * @return
	 */
	@Query("select new com.ssafy.homer.apartInfo.dto.ApartInfoDto(a.aptId,a.aptName,a.latitude,a.longitude) from apart_info a")
	List<ApartInfoDto> findSimpleAll();
}
