package com.ssafy.homer.apartInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.homer.apartInfo.domain.ApartInfo;
import com.ssafy.homer.apartInfo.dto.ApartInfoDto;
import com.ssafy.homer.apartInfo.dto.SearchMapDto;
import com.ssafy.homer.apartInfo.dto.SearchNameDto;
@Repository
public interface ApartInfoRepository extends JpaRepository<ApartInfo,String>,ApartInfoRepositoryCustom{
	
	/**
	 * 특정 필드만 뽑아내는 Projections 적용하기위해 JPQL 쿼리문 작성
	 * @return
	 */
	@Query("select new com.ssafy.homer.apartInfo.dto.ApartInfoDto(a.aptId,a.aptName,a.latitude,a.longitude) from apart_info a")
	List<ApartInfoDto> findSimpleAll();

	/**
	 * 쿼리 dsl사용하여 필터검색 구현
	 * @param searchMapDto
	 * @return
	 */
	List<ApartInfoDto> searchMap(SearchMapDto searchMapDto);

	List<ApartInfoDto> searchName(SearchNameDto searchNameDto);
}
